package org.sid.gestiondestock.dto;

import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.MvtStk;
import org.sid.gestiondestock.model.SourceMvtStk;
import org.sid.gestiondestock.model.TypeMvtStk;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class MvtStkDto {

    private Integer id;
    private Instant dateMvt;
    private BigDecimal quantite;
    private ArticleDto article;
    private TypeMvtStk typeMvt;
    private Integer idEntreprise;
    private SourceMvtStk sourceMvt;

    public static MvtStkDto fromEntity(MvtStk mvtStk){
        if(mvtStk == null){
            return  null;
            // TODO thrown an Exception
        }

        return MvtStkDto.builder()
                .id(mvtStk.getId())
                .dateMvt(mvtStk.getDateMvt())
                .quantite(mvtStk.getQuantite())
                .article(ArticleDto.fromEntity(mvtStk.getArticle()))
                .typeMvt(mvtStk.getTypeMvt())
                .idEntreprise(mvtStk.getIdEntreprise())
                .sourceMvt(mvtStk.getSourceMvt())
                .build();
    }

    public static MvtStk toEntity(MvtStkDto mvtStkDto){
        if(mvtStkDto == null){
            return null;
        }

        MvtStk mvtStk = new MvtStk();
        mvtStk.setId(mvtStkDto.getId());
        mvtStk.setDateMvt(mvtStkDto.getDateMvt());
        mvtStk.setQuantite(mvtStkDto.getQuantite());
        mvtStk.setArticle(ArticleDto.toEntity(mvtStkDto.getArticle()));
        mvtStk.setTypeMvt(mvtStkDto.getTypeMvt());
        mvtStk.setIdEntreprise(mvtStkDto.getIdEntreprise());
        mvtStk.setSourceMvt(mvtStkDto.getSourceMvt());

        return mvtStk;
    }
}
