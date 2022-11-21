package org.sid.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.Fournisseur;

import java.util.List;

@Data
@Builder
public class FournisseurDto {

    private Integer id;
    private String nom;
    private String prenom;
    private AdresseDto adresse;
    private String photo;
    private String email;
    private String numTel;
    private Integer idEntreprise;
    @JsonIgnore
    private List<CommandeFournisseurDto> commandeFournisseurs;

    public static FournisseurDto fromEntity(Fournisseur fournisseur){
        if(fournisseur == null){
            return  null;
            // TODO thrown an Exception
        }

        return FournisseurDto.builder()
                .id(fournisseur.getId())
                .nom(fournisseur.getNom())
                .prenom(fournisseur.getPrenom())
                .adresse(AdresseDto.fromEntity(fournisseur.getAdresse()))
                .photo(fournisseur.getPhoto())
                .email(fournisseur.getEmail())
                .numTel(fournisseur.getNumTel())
                .idEntreprise(fournisseur.getIdEntreprise())
                .build();
    }

    public static Fournisseur toEntity(FournisseurDto fournisseurDto){
        if(fournisseurDto == null){
            return null;
        }

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(fournisseurDto.getId());
        fournisseur.setNom(fournisseurDto.getNom());
        fournisseur.setPrenom(fournisseurDto.getPrenom());
        fournisseur.setAdresse(AdresseDto.toEntity(fournisseurDto.getAdresse()));
        fournisseur.setPhoto(fournisseurDto.getPhoto());
        fournisseur.setEmail(fournisseurDto.getEmail());
        fournisseur.setNumTel(fournisseurDto.getNumTel());
        fournisseur.setIdEntreprise(fournisseurDto.getIdEntreprise());
        return fournisseur;
    }
}
