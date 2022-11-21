package org.sid.gestiondestock.validator;

import org.sid.gestiondestock.dto.AdresseDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AdresseValidator {

    public static List<String> validate(AdresseDto adresseDto){
        List<String> errors = new ArrayList<>();

        if(adresseDto == null){
            errors.add("Le champ 'Adresse1' est obliqatoire");
            errors.add("Le champ 'Ville' est obliqatoire");
            errors.add("Le champ 'Pays' est obliqatoire");
            errors.add("Le champ 'Code Postale' est obliqatoire");
            return errors;
        }

        if(!StringUtils.hasLength(adresseDto.getAdresse1())){
            errors.add("Le champ 'Adresse1' est obliqatoire");
        }
        if(!StringUtils.hasLength(adresseDto.getVille())){
            errors.add("Le champ 'Ville' est obliqatoire");
        }

        if(!StringUtils.hasLength(adresseDto.getPays())){
            errors.add("Le champ 'Pays' est obliqatoire");
        }

        if(!StringUtils.hasLength(adresseDto.getCodePostale())){
            errors.add("Le champ 'Code Postale' est obliqatoire");
        }

        return errors;
    }

}
