package org.sid.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.FournisseurDto;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.services.FlickrService;
import org.sid.gestiondestock.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("fournisseurStrategy")
@Slf4j
public class SaveFournisseurPhoto implements Strategy<FournisseurDto> {

    private FlickrService flickrService;
    private FournisseurService fournisseurService;

    @Autowired
    public SaveFournisseurPhoto(FlickrService flickrService, FournisseurService fournisseurService) {
        this.flickrService = flickrService;
        this.fournisseurService = fournisseurService;
    }

    @Override
    public FournisseurDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {

        FournisseurDto fournisseurDto = fournisseurService.findById(id);

        String urlPhoto = flickrService.savePhoto(photo, titre);

        if(StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo du fournisseur", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        fournisseurDto.setPhoto(urlPhoto);

        return fournisseurService.save(fournisseurDto);
    }
}
