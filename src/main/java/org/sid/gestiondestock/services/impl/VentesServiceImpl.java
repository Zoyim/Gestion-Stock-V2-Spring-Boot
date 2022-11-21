package org.sid.gestiondestock.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.ArticleDto;
import org.sid.gestiondestock.dto.LigneVenteDto;
import org.sid.gestiondestock.dto.MvtStkDto;
import org.sid.gestiondestock.dto.VentesDto;
import org.sid.gestiondestock.exception.EntityNotFoundException;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidEntityException;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.model.*;
import org.sid.gestiondestock.repository.ArticleRepository;
import org.sid.gestiondestock.repository.LigneVenteRepository;
import org.sid.gestiondestock.repository.VentesRepository;
import org.sid.gestiondestock.services.MvtStkService;
import org.sid.gestiondestock.services.VentesService;
import org.sid.gestiondestock.validator.VentesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VentesServiceImpl implements VentesService {

    private ArticleRepository articleRepository;
    private VentesRepository ventesRepository;
    private LigneVenteRepository ligneVenteRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public VentesServiceImpl(ArticleRepository articleRepository,
                             VentesRepository ventesRepository,
                             LigneVenteRepository ligneVenteRepository, MvtStkService mvtStkService) {
        this.articleRepository = articleRepository;
        this.ventesRepository = ventesRepository;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public VentesDto save(VentesDto dto) {
        List<String> errors = VentesValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Ventes n'est pas valide");
            throw new InvalidEntityException("L'objet vente n'est pas valide", ErrorCodes.VENTE_NOT_VALID, errors);
        }

        List<String> articleErrors = new ArrayList<>();

        dto.getLigneVentes().forEach(ligneVenteDto -> {
            Optional<Article> article = articleRepository.findById(ligneVenteDto.getArticle().getId());
            if(article.isEmpty()){
                articleErrors.add("Aucun article avec l'ID " + ligneVenteDto.getArticle().getId() + " n'a ete trouve dans la BD");
            }
        });

        if(!articleErrors.isEmpty()){
            log.warn("One or more articles were not found in the DB, {}", errors);
            throw new InvalidEntityException("Un ou plusieurs articles n'ont pas ete trouve dans la BD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        Ventes saveVente = ventesRepository.save(VentesDto.toEntity(dto));

        dto.getLigneVentes().forEach(ligneVenteDto ->{
            LigneVente ligneVente = LigneVenteDto.toEntity(ligneVenteDto);
            ligneVente.setVentes(saveVente);
            ligneVenteRepository.save(ligneVente);
            updateMvtStk(ligneVente);
        });

        return VentesDto.fromEntity(saveVente);
    }

    @Override
    public VentesDto findById(Integer id) {
        if(id == null){
            log.error("Vente ID is null");
            return null;
        }
        return ventesRepository.findById(id)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente n'a ete trouve avec l'ID " +id, ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public VentesDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Vente CODE is null");
            return null;
        }
        return ventesRepository.findVentesByCode(code)
                .map(VentesDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune vente n'a ete trouve avec le CODE " +code, ErrorCodes.VENTE_NOT_FOUND));
    }

    @Override
    public List<VentesDto> findAll() {
        return ventesRepository.findAll().stream()
                .map(VentesDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Commande Client ID is null");
            return;
        }

        List<LigneVente> ligneVentes = ligneVenteRepository.findAllByVentesId(id);
        if(!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une vente deja utilisee", ErrorCodes.VENTE_ALREADY_IN_USE);
        }

        ventesRepository.deleteById(id);
    }

    private void updateMvtStk(LigneVente lig){

            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.SORTIE)
                    .sourceMvt(SourceMvtStk.VENTE)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();

            mvtStkService.sortieStock(mvtStkDto);

    }
}
