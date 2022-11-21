package org.sid.gestiondestock.validator;

import org.sid.gestiondestock.dto.LigneCommandeFournisseurDto;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeFournisseurValidator {

    public static List<String> validate(LigneCommandeFournisseurDto ligneCommandeFournisseurDto){
        List<String> errors = new ArrayList<>();

        if(ligneCommandeFournisseurDto == null){
            errors.add("Veuillez renseigner la quantite de l'article");
            errors.add("Veuillez renseigner le prix unitaire de l'article");
            errors.add("Veuillez selectionner un article");
            errors.add("Veuillez selectionner la commande du fournisseur");

            return errors;
        }
        if(ligneCommandeFournisseurDto.getQuantite() == null){
            errors.add("Veuillez renseigner la quantite de l'article");
        }
        if(ligneCommandeFournisseurDto.getPrixUnitaire() == null){
            errors.add("Veuillez renseigner le prix unitaire de l'article");
        }
        if(ligneCommandeFournisseurDto.getArticle() == null){
            errors.add("Veuillez selectionner un article");
        }
        if(ligneCommandeFournisseurDto.getCommandeFournisseurs() == null){
            errors.add("Veuillez selectionner la commande du fournisseur");
        }

        return errors;
    }
}
