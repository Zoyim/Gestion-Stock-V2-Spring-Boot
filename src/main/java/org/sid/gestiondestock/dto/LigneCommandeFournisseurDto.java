package org.sid.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.LigneCommandeFournisseur;

import java.math.BigDecimal;

@Data
@Builder
public class LigneCommandeFournisseurDto {

    private Integer id;
    private ArticleDto article;
    private CommandeFournisseurDto commandeFournisseurs;
    private BigDecimal quantite;
    private BigDecimal prixUnitaire;
    private Integer idEntreprise;

    public static LigneCommandeFournisseurDto fromEntity(LigneCommandeFournisseur ligneCommandeFournisseur){
        if(ligneCommandeFournisseur == null){
            return  null;
            // TODO thrown an Exception
        }

        return LigneCommandeFournisseurDto.builder()
                .id(ligneCommandeFournisseur.getId())
                .article(ArticleDto.fromEntity(ligneCommandeFournisseur.getArticle()))
                .commandeFournisseurs(CommandeFournisseurDto.fromEntity(ligneCommandeFournisseur.getCommandeFournisseurs()))
                .quantite(ligneCommandeFournisseur.getQuantite())
                .prixUnitaire(ligneCommandeFournisseur.getPrixUnitaire())
                .idEntreprise(ligneCommandeFournisseur.getIdEntreprise())
                .build();
    }

    public static LigneCommandeFournisseur toEntity(LigneCommandeFournisseurDto ligneCommandeFournisseurDto){
        if(ligneCommandeFournisseurDto == null){
            return null;
        }

        LigneCommandeFournisseur ligneCommandeFournisseur = new LigneCommandeFournisseur();

        ligneCommandeFournisseur.setId(ligneCommandeFournisseurDto.getId());
        ligneCommandeFournisseur.setArticle(ArticleDto.toEntity(ligneCommandeFournisseurDto.getArticle()));
        ligneCommandeFournisseur.setCommandeFournisseurs(CommandeFournisseurDto.toEntity(ligneCommandeFournisseurDto.getCommandeFournisseurs()));
        ligneCommandeFournisseur.setQuantite(ligneCommandeFournisseurDto.getQuantite());
        ligneCommandeFournisseur.setPrixUnitaire(ligneCommandeFournisseurDto.getPrixUnitaire());
        ligneCommandeFournisseur.setIdEntreprise(ligneCommandeFournisseurDto.getIdEntreprise());
        return ligneCommandeFournisseur;
    }
}
