package org.sid.gestiondestock.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.ChangerMotDePasseUtilisateurDto;
import org.sid.gestiondestock.dto.UtilisateurDto;
import org.sid.gestiondestock.exception.EntityNotFoundException;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidEntityException;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.model.Utilisateur;
import org.sid.gestiondestock.repository.UtilisateurRepository;
import org.sid.gestiondestock.services.UtilisateurService;
import org.sid.gestiondestock.validator.UtilisateurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UtilisateurDto save(UtilisateurDto dto) {
        List<String> errors = UtilisateurValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Utilisateur is not valid {}", dto);
            throw new InvalidEntityException("L'utilisateur n'est pas valide", ErrorCodes.UTILISATEUR_NOT_VALID, errors);
        }
        return UtilisateurDto.fromEntity(
                utilisateurRepository.save(
                        UtilisateurDto.toEntity(dto)
                )
        );
    }

    @Override
    public UtilisateurDto findById(Integer id) {
        if(id == null){
            log.error("Utilisateur ID is null");
            return null;
        }
        return utilisateurRepository.findById(id)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun utilisateur avec l'ID = " + id + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    @Override
    public UtilisateurDto findByNumTel(String numTel) {
        if(!StringUtils.hasLength(numTel)){
            log.error("Utilisateur Numero Telephone is null");
            return null;
        }
        return utilisateurRepository.findUtilisateurByNumTel(numTel)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun utilisateur avec le Numero Telelphone = " + numTel + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    @Override
    public UtilisateurDto findByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("Utilisateur Email is null");
            return null;
        }
        return utilisateurRepository.findUtilisateurByEmail(email)
                .map(UtilisateurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun utilisateur avec l'email = " + email + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.UTILISATEUR_NOT_FOUND
                ));
    }

    @Override
    public List<UtilisateurDto> findAll() {
        return utilisateurRepository.findAll().stream()
                .map(UtilisateurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Utilisateur ID is null");
            return;
        }
        utilisateurRepository.deleteById(id);
    }

    @Override
    public UtilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {

        validate(dto);
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(dto.getId());
        if(utilisateurOptional.isEmpty()){
            log.warn("Aucun utilisateur n'a ete trouve avec l'ID " + dto.getId());
            throw new EntityNotFoundException("Aucun utilisateur n'a ete trouve avec l'ID " + dto.getId(), ErrorCodes.UTILISATEUR_NOT_FOUND);
        }

        Utilisateur utilisateur = utilisateurOptional.get();

        utilisateur.setMotDePasse(dto.getMotDePasse());

        return UtilisateurDto.fromEntity(utilisateurRepository.save(utilisateur));
    }

    private void validate(ChangerMotDePasseUtilisateurDto dto){
        if(dto == null){
            log.warn("Impossible de modifier le mot de passe avec un objet null");
            throw new InvalidOperationException("Aucune information n'a ete fournie pour pouvoir changer le mot de passe", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if(dto.getId() == null){
            log.warn("Impossible de modifier le mot de passe avec un ID null");
            throw new InvalidOperationException("ID utilisateur null:: impossible de modifier le mot de passe", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if(!StringUtils.hasLength(dto.getMotDePasse()) || !StringUtils.hasLength(dto.getConfirmMotDePasse())){
            log.warn("Impossible de modifier le mot de passe avec un mot de passe null");
            throw new InvalidOperationException("Mot de passe utilisateur null:: impossible de modifier le mot de passe", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        if(!dto.getMotDePasse().equals(dto.getConfirmMotDePasse())){
            log.warn("Impossible de modifier le mot de passe avec deux mots de passe différent");
            throw new InvalidOperationException("Mot de passe utilisateur non conforme:: impossible de modifier le mot de passe", ErrorCodes.UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
    }
}
