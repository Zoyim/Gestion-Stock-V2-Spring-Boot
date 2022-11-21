package org.sid.gestiondestock.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.FournisseurDto;
import org.sid.gestiondestock.exception.EntityNotFoundException;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidEntityException;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.model.CommandeFournisseur;
import org.sid.gestiondestock.repository.CommandeFournisseurRepository;
import org.sid.gestiondestock.repository.FournisseurRepository;
import org.sid.gestiondestock.services.FournisseurService;
import org.sid.gestiondestock.validator.FournisseurValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FournisseurServiceImpl implements FournisseurService {

    private FournisseurRepository fournisseurRepository;
    private CommandeFournisseurRepository commandeFournisseurRepository;

    @Autowired
    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository, CommandeFournisseurRepository commandeFournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }

    @Override
    public FournisseurDto save(FournisseurDto dto) {
        List<String> errors = FournisseurValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Fournisseur is not valid {}", dto);
            throw new InvalidEntityException("Le fournisseur n'est pas valide", ErrorCodes.FOURNISSEUR_NOT_VALID, errors);
        }
        return FournisseurDto.fromEntity(
                fournisseurRepository.save(
                        FournisseurDto.toEntity(dto)
                )
        );
    }

    @Override
    public FournisseurDto findById(Integer id) {
        if(id == null){
            log.error("Fournisseur ID is null");
            return null;
        }
        return fournisseurRepository.findById(id)
                .map(FournisseurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun fournisseur avec l'ID = " + id + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public FournisseurDto findByNumTel(String numTel) {
        if(!StringUtils.hasLength(numTel)){
            log.error("Fournisseur Numero Telephone is null");
            return null;
        }
        return fournisseurRepository.findFournisseurByNumTel(numTel)
                .map(FournisseurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun fournisseur avec le Numero Telelphone = " + numTel + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public FournisseurDto findByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("Fournisseur Email is null");
            return null;
        }
        return fournisseurRepository.findFournisseurByEmail(email)
                .map(FournisseurDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucun fournisseur avec l'email = " + email + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.FOURNISSEUR_NOT_FOUND
                ));
    }

    @Override
    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(FournisseurDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Fournisseur ID is null");
            return;
        }

        List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAllByFournisseurId(id);
        if(!commandeFournisseurs.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer ce fournisseur qui a deja des commandes fournisseurs", ErrorCodes.FOURNISSEUR_ALREADY_IN_USE);
        }

        fournisseurRepository.deleteById(id);
    }
}
