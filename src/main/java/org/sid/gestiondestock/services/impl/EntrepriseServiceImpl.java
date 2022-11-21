package org.sid.gestiondestock.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.EntrepriseDto;
import org.sid.gestiondestock.dto.RolesDto;
import org.sid.gestiondestock.dto.UtilisateurDto;
import org.sid.gestiondestock.exception.EntityNotFoundException;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidEntityException;
import org.sid.gestiondestock.repository.EntrepriseRepository;
import org.sid.gestiondestock.repository.RolesRepository;
import org.sid.gestiondestock.services.EntrepriseService;
import org.sid.gestiondestock.services.UtilisateurService;
import org.sid.gestiondestock.validator.EntrepriseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EntrepriseServiceImpl implements EntrepriseService {

    private EntrepriseRepository entrepriseRepository;
    private UtilisateurService utilisateurService;
    private RolesRepository rolesRepository;
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository, UtilisateurService utilisateurService, RolesRepository rolesRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.entrepriseRepository = entrepriseRepository;
        this.utilisateurService = utilisateurService;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public EntrepriseDto save(EntrepriseDto dto) {
        List<String> errors = EntrepriseValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Entreprise is not valid {}", dto);
            throw new InvalidEntityException("L'Entreprise n'est pas valide", ErrorCodes.ENTREPRISE_NOT_VALID, errors);
        }

        EntrepriseDto savedEntreprise = EntrepriseDto.fromEntity(entrepriseRepository.save(EntrepriseDto.toEntity(dto)));

        UtilisateurDto utilisateur = fromEntreprise(savedEntreprise);

        UtilisateurDto savedUser = utilisateurService.save(utilisateur);

        RolesDto rolesDto = RolesDto.builder()
                .roleName("ADMIN")
                .utilisateur(savedUser)
                .build();

        rolesRepository.save(RolesDto.toEntity(rolesDto));

        return savedEntreprise;
    }

    private UtilisateurDto fromEntreprise(EntrepriseDto dto) {
        return UtilisateurDto.builder()
                .adresse(dto.getAdresse())
                .nom(dto.getNom())
                .prenom(dto.getCodeFiscal())
                .email(dto.getEmail())
                .motDePasse(bCryptPasswordEncoder.encode(generateRandomPassword()))
                .entreprise(dto)
                .dateDeNaissance(Instant.now())
                .photo(dto.getPhoto())
                .build();
    }

    private String generateRandomPassword() {
        return "som3R@nd0mP@$$word";
    }

    @Override
    public EntrepriseDto findById(Integer id) {
        if(id == null){
            log.error("Enteprise ID is null");
            return null;
        }
        return entrepriseRepository.findById(id)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucune Entreprise avec l'ID = " + id + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    @Override
    public EntrepriseDto findByNumTel(String numTel) {
        if(!StringUtils.hasLength(numTel)){
            log.error("Entreprise Numero Telephone is null");
            return null;
        }
        return entrepriseRepository.findEntrepriseByNumTel(numTel)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucune Entreprise avec le Numero Telelphone = " + numTel + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    @Override
    public EntrepriseDto findByEmail(String email) {
        if(!StringUtils.hasLength(email)){
            log.error("Entreprise avec email is null");
            return null;
        }
        return entrepriseRepository.findEntrepriseByEmail(email)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucune Entreprise avec l'email = " + email + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    @Override
    public EntrepriseDto findByNom(String nom) {
        if(!StringUtils.hasLength(nom)){
            log.error("Entreprise avec nom is null");
            return null;
        }
        return entrepriseRepository.findEntrepriseByNom(nom)
                .map(EntrepriseDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucune Entreprise avec le nom = " + nom + " n'a pas ete trouve dans la BDD",
                        ErrorCodes.ENTREPRISE_NOT_FOUND
                ));
    }

    @Override
    public List<EntrepriseDto> findAll() {
        return entrepriseRepository.findAll().stream()
                .map(EntrepriseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Entreprise ID is null");
            return;
        }
        entrepriseRepository.deleteById(id);
    }
}
