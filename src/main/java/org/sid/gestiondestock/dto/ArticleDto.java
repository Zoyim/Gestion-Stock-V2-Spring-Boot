package org.sid.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ArticleDto {

    private Integer id;
    private String codeArticle;
    private String designation;
    private BigDecimal prixUnitaireHt;
    private BigDecimal tauxTva;
    private BigDecimal prixUnitaireTtc;
    private String photo;
    private CategoryDto category;
    private Integer idEntreprise;
    @JsonIgnore
    private List<LigneVente> ligneVentes;
    @JsonIgnore
    private List<LigneCommandeClient> ligneCommandeClients;
    @JsonIgnore
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs;
    @JsonIgnore
    private List<MvtStk> mvtStks;

    public static ArticleDto fromEntity(Article article){
        if(article == null){
            return  null;
            // TODO thrown an Exception
        }

        return ArticleDto.builder()
                .id(article.getId())
                .codeArticle(article.getCodeArticle())
                .designation(article.getDesignation())
                .prixUnitaireHt(article.getPrixUnitaireHt())
                .tauxTva(article.getTauxTva())
                .prixUnitaireTtc(article.getPrixUnitaireTtc())
                .photo(article.getPhoto())
                .idEntreprise(article.getIdEntreprise())
                .category(CategoryDto.fromEntity(article.getCategory()))
                .build();
    }

    public static Article toEntity(ArticleDto articleDto){
        if(articleDto == null){
            return null;
        }

        Article article = new Article();
        article.setId(articleDto.getId());
        article.setCodeArticle(articleDto.getCodeArticle());
        article.setDesignation(articleDto.getDesignation());
        article.setPrixUnitaireHt(articleDto.getPrixUnitaireHt());
        article.setTauxTva(articleDto.getTauxTva());
        article.setPrixUnitaireTtc(articleDto.getPrixUnitaireTtc());
        article.setPhoto(articleDto.getPhoto());
        article.setIdEntreprise(articleDto.getIdEntreprise());
        article.setCategory(CategoryDto.toEntity(articleDto.getCategory()));
        return article;
    }
}
