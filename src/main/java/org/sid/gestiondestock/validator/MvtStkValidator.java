package org.sid.gestiondestock.validator;

import org.sid.gestiondestock.dto.MvtStkDto;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MvtStkValidator {

    public static List<String> validate(MvtStkDto mvtStkDto){
        List<String> errors = new ArrayList<>();


        if(mvtStkDto == null){
            errors.add("Veuillez renseigner la date du mouvement de stock");
            errors.add("Veuillez renseigner la quantite du mouvement de stock");
            errors.add("Veuillez selectionner l'article");
            errors.add("Veuillez renseigner le type de mouvement");
            return errors;
        }

        if(mvtStkDto.getDateMvt() == null){
            errors.add("Veuillez renseigner la date du mouvement de stock");
        }

        if(mvtStkDto.getQuantite() == null || mvtStkDto.getQuantite().compareTo(BigDecimal.ZERO) == 0){
            errors.add("Veuillez renseigner la quantite du mouvement de stock");
        }

        if(mvtStkDto.getArticle() == null || mvtStkDto.getArticle().getId() == null){
            errors.add("Veuillez selectionner l'article");
        }

        if(!StringUtils.hasLength(mvtStkDto.getTypeMvt().name())){
            errors.add("Veuillez renseigner le type de mouvement");
        }

        return errors;
    }
}
