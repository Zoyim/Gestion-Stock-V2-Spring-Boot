package org.sid.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.LigneVente;

import java.math.BigDecimal;

@Data
@Builder
public class LigneVenteDto {

    private Integer id;
    private VentesDto ventes;
    private ArticleDto article;
    private BigDecimal quantite;
    private BigDecimal prixUnitaire;
    private Integer idEntreprise;

    public static LigneVenteDto fromEntity(LigneVente ligneVente){
        if(ligneVente == null){
            return  null;
            // TODO thrown an Exception
        }

        return LigneVenteDto.builder()
                .id(ligneVente.getId())
                .ventes(VentesDto.fromEntity(ligneVente.getVentes()))
                .article(ArticleDto.fromEntity(ligneVente.getArticle()))
                .quantite(ligneVente.getQuantite())
                .prixUnitaire(ligneVente.getPrixUnitaire())
                .idEntreprise(ligneVente.getIdEntreprise())
                .build();
    }

    public static LigneVente toEntity(LigneVenteDto ligneVenteDto){
        if(ligneVenteDto == null){
            return null;
        }

        LigneVente ligneVente = new LigneVente();
        ligneVente.setId(ligneVenteDto.getId());
        ligneVente.setVentes(VentesDto.toEntity(ligneVenteDto.getVentes()));
        ligneVente.setArticle(ArticleDto.toEntity(ligneVenteDto.getArticle()));
        ligneVente.setQuantite(ligneVenteDto.getQuantite());
        ligneVente.setPrixUnitaire(ligneVenteDto.getPrixUnitaire());
        ligneVente.setIdEntreprise(ligneVenteDto.getIdEntreprise());

        return ligneVente;
    }
}
