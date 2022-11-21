package org.sid.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.EntrepriseDto;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.services.EntrepriseService;
import org.sid.gestiondestock.services.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("entrepriseStrategy")
@Slf4j
public class SaveEntreprisePhoto implements Strategy<EntrepriseDto> {

    private FlickrService flickrService;
    private EntrepriseService entrepriseService;

    @Autowired
    public SaveEntreprisePhoto(FlickrService flickrService, EntrepriseService entrepriseService) {
        this.flickrService = flickrService;
        this.entrepriseService = entrepriseService;
    }

    @Override
    public EntrepriseDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {
        EntrepriseDto entrepriseDto = entrepriseService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if(StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo de l'entreprise", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        entrepriseDto.setPhoto(urlPhoto);
        return entrepriseService.save(entrepriseDto);
    }
}
