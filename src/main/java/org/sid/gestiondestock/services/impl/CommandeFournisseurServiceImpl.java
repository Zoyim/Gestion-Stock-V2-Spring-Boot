package org.sid.gestiondestock.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.*;
import org.sid.gestiondestock.exception.EntityNotFoundException;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidEntityException;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.model.*;
import org.sid.gestiondestock.repository.ArticleRepository;
import org.sid.gestiondestock.repository.CommandeFournisseurRepository;
import org.sid.gestiondestock.repository.FournisseurRepository;
import org.sid.gestiondestock.repository.LigneCommandeFournisseurRepository;
import org.sid.gestiondestock.services.CommandeFournisseurService;
import org.sid.gestiondestock.services.MvtStkService;
import org.sid.gestiondestock.validator.ArticleValidator;
import org.sid.gestiondestock.validator.CommandeFournisseurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private CommandeFournisseurRepository commandeFournisseurRepository;
    private LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository;
    private FournisseurRepository fournisseurRepository;
    private ArticleRepository articleRepository;
    private MvtStkService mvtStkService;

    @Autowired
    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository,
                                          LigneCommandeFournisseurRepository ligneCommandeFournisseurRepository,
                                          FournisseurRepository fournisseurRepository,
                                          ArticleRepository articleRepository, MvtStkService mvtStkService) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
        this.ligneCommandeFournisseurRepository = ligneCommandeFournisseurRepository;
        this.fournisseurRepository = fournisseurRepository;
        this.articleRepository = articleRepository;
        this.mvtStkService = mvtStkService;
    }

    @Override
    public CommandeFournisseurDto save(CommandeFournisseurDto dto) {
        List<String> errors = CommandeFournisseurValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Commande Fournisseur n'est pas valide");
            throw new InvalidEntityException("La commande fournisseur n'est pas valide", ErrorCodes.COMMANDE_FOURNISSEUR_NOT_VALID, errors);
        }

        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(dto.getFournisseur().getId());
        if(fournisseur.isEmpty()){
            log.warn("Fournisseur ID {} was not found in the DB", dto.getFournisseur().getId());
            throw new EntityNotFoundException("Aucun fournisseur avec l'ID " + dto.getFournisseur().getId() + " n'a ete trouve dans la BD", ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        List<String> articleErrors = new ArrayList<>();

        if(dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(ligCmdClt ->{
                if(ligCmdClt.getArticle() != null){
                    Optional<Article> article = articleRepository.findById(ligCmdClt.getArticle().getId());
                    if(article.isEmpty()){
                        articleErrors.add("L'article avec l'ID " + ligCmdClt.getArticle().getId() + " n'existe pas");
                    }
                }else{
                    articleErrors.add("Impossible d'enregistrer une commande avec un article NULL");
                }

            } );
        }

        if(!articleErrors.isEmpty()){
            log.warn("");
            throw new InvalidEntityException("Article n'existe pas dans la BD", ErrorCodes.ARTICLE_NOT_FOUND, articleErrors);
        }

        CommandeFournisseur saveCmdClt = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(dto));

        if(dto.getLigneCommandeFournisseurs() != null){
            dto.getLigneCommandeFournisseurs().forEach(ligCmdFrn ->{
                LigneCommandeFournisseur ligneCommandeFournisseur = LigneCommandeFournisseurDto.toEntity(ligCmdFrn);
                ligneCommandeFournisseur.setCommandeFournisseurs(saveCmdClt);
                ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);
            });
        }
        return CommandeFournisseurDto.fromEntity(saveCmdClt);
    }

    @Override
    public CommandeFournisseurDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande) {
        checkIdCommande(idCommande);

        if(!StringUtils.hasLength(String.valueOf(etatCommande))){
            log.error("L'etat de la commande fournisseur ID is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un etat null", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);
        commandeFournisseur.setEtatCommande(etatCommande);
        CommandeFournisseur savedCmtFrn = commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur));

        if(commandeFournisseur.isCommandeLivree()){
            updateMvtStk(idCommande);
        }

        return CommandeFournisseurDto.fromEntity(savedCmtFrn);
    }

    @Override
    public CommandeFournisseurDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite) {

        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        if(quantite == null || quantite.compareTo(BigDecimal.ZERO) == 0){
            log.error("L'ID de la ligne commande is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une quantite null ou 0", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

        LigneCommandeFournisseur ligneCommandeFournisseur = ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseur.setQuantite(quantite);

        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseur);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto updateFournisseur(Integer idCommande, Integer idFournisseur) {

        checkIdCommande(idCommande);
        checkIdFournisseur(idFournisseur);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<Fournisseur> fournisseurOptional = fournisseurRepository.findById(idFournisseur);

        if(fournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun fournisseur n'a ete trouve avec l'ID " + idFournisseur, ErrorCodes.FOURNISSEUR_NOT_FOUND);
        }

        commandeFournisseur.setFournisseur(FournisseurDto.fromEntity(fournisseurOptional.get()));

        return CommandeFournisseurDto.fromEntity(
                commandeFournisseurRepository.save(CommandeFournisseurDto.toEntity(commandeFournisseur))
        );
    }

    @Override
    public CommandeFournisseurDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);
        checkIdArticle(idArticle, "nouvel");

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = findLigneCommandeFournisseur(idLigneCommande);

        Optional<Article> articleOptional = articleRepository.findById(idArticle);

        if(articleOptional.isEmpty()){
            throw new EntityNotFoundException("Aucun article n'a ete trouve avec l'ID " + idArticle, ErrorCodes.ARTICLE_NOT_FOUND);
        }

        List<String> errors = ArticleValidator.validate(ArticleDto.fromEntity(articleOptional.get()));

        if(!errors.isEmpty()){
            throw new InvalidEntityException("Article Invalid", ErrorCodes.ARTICLE_NOT_VALID, errors);
        }

        LigneCommandeFournisseur ligneCommandeFournisseurToSaved = ligneCommandeFournisseurOptional.get();
        ligneCommandeFournisseurToSaved.setArticle(articleOptional.get());
        ligneCommandeFournisseurRepository.save(ligneCommandeFournisseurToSaved);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto deleteArticle(Integer idCommande, Integer idLigneCommande) {
        checkIdCommande(idCommande);
        checkIdLigneCommande(idLigneCommande);

        CommandeFournisseurDto commandeFournisseur = checkEtatCommande(idCommande);

        // Just to check the LigneCommandeFournisseur and inform the fournisseur in case it is absent
        findLigneCommandeFournisseur(idLigneCommande);
        ligneCommandeFournisseurRepository.deleteById(idLigneCommande);

        return commandeFournisseur;
    }

    @Override
    public CommandeFournisseurDto findById(Integer id) {
        if(id == null){
            log.error("Commande Fournisseur ID is null");
            return null;
        }
        return commandeFournisseurRepository.findById(id)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande fournisseur n'a ete trouve avec l'ID " +id, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
    }

    @Override
    public CommandeFournisseurDto findByCode(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Commande Fournisseur CODE is null");
            return null;
        }
        return commandeFournisseurRepository.findCommandeFournisseurByCode(code)
                .map(CommandeFournisseurDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune commande fournisseur n'a ete trouve avec le CODE " +code, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND));
    }

    @Override
    public List<CommandeFournisseurDto> findAll() {
        return commandeFournisseurRepository.findAll().stream()
                .map(CommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommmandeFournisseurId(Integer idCommande) {
        return ligneCommandeFournisseurRepository.findAllByCommandeFournisseursId(idCommande).stream()
                .map(LigneCommandeFournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Commande Client ID is null");
            return;
        }

        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseursId(id);
        if(!ligneCommandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer une commande fournisseur deja utilisee", ErrorCodes.COMMANDE_FOURNISSEUR_ALREADY_IN_USE);
        }

        commandeFournisseurRepository.deleteById(id);
    }

    private void checkIdCommande(Integer idCommande){
        if(idCommande == null){
            log.error("Commande Fournisseur ID is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec ID null", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdFournisseur(Integer idFournisseur){
        if(idFournisseur == null){
            log.error("L'ID du fournisseur is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un ID fournisseur null", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }
    }

    private void checkIdLigneCommande(Integer idLigneCommande){
        if(idLigneCommande == null){
            log.error("L'ID de la ligne commande is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec une ligne de commande null", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private void checkIdArticle(Integer idArticle, String msg){
        if(idArticle == null){
            log.error("L'ID de " + msg +" l'article is null");
            throw new InvalidOperationException("Impossible de modifier l'etat de la commande avec un " + msg + " ID article null", ErrorCodes.COMMANDE_CLIENT_NON_MODIFIABLE);
        }
    }

    private CommandeFournisseurDto checkEtatCommande(Integer idCommande) {
        CommandeFournisseurDto commandeFournisseur = findById(idCommande);

        if(commandeFournisseur.isCommandeLivree()){
            throw new InvalidOperationException("Impossible de modifier la commande lorsqu'elle est livrée", ErrorCodes.COMMANDE_FOURNISSEUR_NON_MODIFIABLE);
        }

        return commandeFournisseur;
    }
    private Optional<LigneCommandeFournisseur> findLigneCommandeFournisseur(Integer idLigneCommande) {
        Optional<LigneCommandeFournisseur> ligneCommandeFournisseurOptional = ligneCommandeFournisseurRepository.findById(idLigneCommande);

        if(ligneCommandeFournisseurOptional.isEmpty()){
            throw new EntityNotFoundException("Aucune ligne commande fournisseur n'a ete trouve avec l'ID " +idLigneCommande, ErrorCodes.COMMANDE_FOURNISSEUR_NOT_FOUND);
        }

        return ligneCommandeFournisseurOptional;
    }
    private void updateMvtStk(Integer idCommande){
        List<LigneCommandeFournisseur> ligneCommandeFournisseurs = ligneCommandeFournisseurRepository.findAllByCommandeFournisseursId(idCommande);
        ligneCommandeFournisseurs.forEach(lig ->{
            MvtStkDto mvtStkDto = MvtStkDto.builder()
                    .article(ArticleDto.fromEntity(lig.getArticle()))
                    .dateMvt(Instant.now())
                    .typeMvt(TypeMvtStk.ENTREE)
                    .sourceMvt(SourceMvtStk.COMMANDE_FOURNISSEUR)
                    .quantite(lig.getQuantite())
                    .idEntreprise(lig.getIdEntreprise())
                    .build();

            mvtStkService.entreeStock(mvtStkDto);
        });
    }
}
