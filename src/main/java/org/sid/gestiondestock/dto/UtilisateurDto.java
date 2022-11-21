package org.sid.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.Utilisateur;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class UtilisateurDto {

    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String numTel;
    private Instant dateDeNaissance;
    private String motDePasse;
    private AdresseDto adresse;
    private String photo;
    private EntrepriseDto entreprise;
    private List<RolesDto> roles;

    public static UtilisateurDto fromEntity(Utilisateur utilisateur){
        if(utilisateur == null){
            return  null;
            // TODO thrown an Exception
        }

        return UtilisateurDto.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .numTel(utilisateur.getNumTel())
                .dateDeNaissance(utilisateur.getDateDeNaissance())
                .motDePasse(utilisateur.getMotDePasse())
                .adresse(AdresseDto.fromEntity(utilisateur.getAdresse()))
                .photo(utilisateur.getPhoto())
                .entreprise(EntrepriseDto.fromEntity(utilisateur.getEntreprise()))
                .roles(
                        utilisateur.getRoles() != null ?
                                utilisateur.getRoles().stream()
                                .map(RolesDto::fromEntity)
                                .collect(Collectors.toList()) : null
                )
                .build();
    }

    public static Utilisateur toEntity(UtilisateurDto utilisateurDto){
        if(utilisateurDto == null){
            return null;
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(utilisateurDto.getId());
        utilisateur.setNom(utilisateurDto.getNom());
        utilisateur.setPrenom(utilisateurDto.getPrenom());
        utilisateur.setEmail(utilisateurDto.getEmail());
        utilisateur.setNumTel(utilisateurDto.getNumTel());
        utilisateur.setDateDeNaissance(utilisateurDto.getDateDeNaissance());
        utilisateur.setMotDePasse(utilisateurDto.getMotDePasse());
        utilisateur.setAdresse(AdresseDto.toEntity(utilisateurDto.getAdresse()));
        utilisateur.setPhoto(utilisateurDto.getPhoto());
        utilisateur.setEntreprise(EntrepriseDto.toEntity(utilisateurDto.getEntreprise()));
        return utilisateur;
    }
}
