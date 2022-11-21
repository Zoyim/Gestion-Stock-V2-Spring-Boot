package org.sid.gestiondestock.validator;

import org.sid.gestiondestock.dto.LigneCommandeClientDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class LigneCommandeClientValidator {

    public static List<String> validate(LigneCommandeClientDto ligneCommandeClientDto){
        List<String> errors = new ArrayList<>();

        if(ligneCommandeClientDto == null){
            errors.add("Veuillez renseigner la quantite de l'article");
            errors.add("Veuillez renseigner le prix unitaire de l'article");
            errors.add("Veuillez selectionner un article");
            errors.add("Veuillez selectionner la commande du client");

            return errors;
        }
        if(ligneCommandeClientDto.getQuantite() == null){
            errors.add("Veuillez renseigner la quantite de l'article");
        }
        if(ligneCommandeClientDto.getPrixUnitaire() == null){
            errors.add("Veuillez renseigner le prix unitaire de l'article");
        }
        if(ligneCommandeClientDto.getArticle() == null){
            errors.add("Veuillez selectionner un article");
        }
        if(ligneCommandeClientDto.getCommandeClient() == null){
            errors.add("Veuillez selectionner la commande du client");
        }

        return errors;
    }
}
