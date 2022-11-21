package org.sid.gestiondestock.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.ArticleDto;
import org.sid.gestiondestock.dto.LigneCommandeClientDto;
import org.sid.gestiondestock.dto.LigneCommandeFournisseurDto;
import org.sid.gestiondestock.dto.LigneVenteDto;
import org.sid.gestiondestock.exception.EntityNotFoundException;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidEntityException;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.model.LigneCommandeClient;
import org.sid.gestiondestock.model.LigneCommandeFournisseur;
import org.sid.gestiondestock.model.LigneVente;
import org.sid.gestiondestock.repository.*;
import org.sid.gestiondestock.services.ArticleService;
import org.sid.gestiondestock.validator.ArticleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {


    private ArticleRepository articleRepository;
    private LigneVenteRepository venteRepository;
    private LigneCommandeClientRepository commandeClientRepository;
    private LigneCommandeFournisseurRepository commandeFournisseurRepository;



    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, LigneVenteRepository venteRepository, LigneCommandeClientRepository commandeClientRepository, LigneCommandeFournisseurRepository commandeFournisseurRepository) {
        this.articleRepository = articleRepository;
        this.venteRepository = venteRepository;
        this.commandeClientRepository = commandeClientRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }


    @Override
    public ArticleDto save(ArticleDto dto) {
        List<String> errors = ArticleValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Article is not valid {}", dto);
            throw new InvalidEntityException("L'article n'est pas valide", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }
        return ArticleDto.fromEntity(
                articleRepository.save(
                        ArticleDto.toEntity(dto)
                )
        );
    }

    @Override
    public ArticleDto findById(Integer id) {
        if(id == null){
            log.error("Article ID is null");
            return null;
        }

        return articleRepository.findById(id)
                .map(ArticleDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun article avec l'ID = " + id + " n'a ete trouve dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND
                ));
    }

    @Override
    public ArticleDto findByCodeArticle(String codeArticle) {
        if(!StringUtils.hasLength(codeArticle)){
            log.error("Article CODE is null");
            return null;
        }

        return articleRepository.findArticleByCodeArticle(codeArticle)
                .map(ArticleDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun article avec le CODE = " + codeArticle + " n'a ete trouve dans la BDD",
                        ErrorCodes.ARTICLE_NOT_FOUND
                ));
    }

    @Override
    public List<ArticleDto> findAll() {
        return articleRepository.findAll().stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Article ID is null");
            return;
        }

        List<LigneCommandeClient> ligneCommandeClients = commandeClientRepository.findAllByArticleId(id);
        if(!ligneCommandeClients.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes clients", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = commandeFournisseurRepository.findAllByArticleId(id);
        if(!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des commandes fournisseurs", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        List<LigneVente> ligneVentes = venteRepository.findAllByArticleId(id);
        if(!ligneVentes.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer un article deja utilise dans des ventes", ErrorCodes.ARTICLE_ALREADY_IN_USE);
        }

        articleRepository.deleteById(id);
    }

    @Override
    public List<LigneVenteDto> findHistoriqueVentes(Integer idArticle) {
        return venteRepository.findAllByArticleId(idArticle).stream()
                .map(LigneVenteDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle) {
        return commandeClientRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeClientDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle) {
        return commandeFournisseurRepository.findAllByArticleId(idArticle).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllArticleByIdCategory(Integer idCategory) {
        return articleRepository.findAllByCategoryId(idCategory).stream()
                .map(ArticleDto::fromEntity)
                .collect(Collectors.toList());
    }
}
