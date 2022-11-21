package org.sid.gestiondestock.services.strategy;

import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.ClientDto;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.services.ClientService;
import org.sid.gestiondestock.services.FlickrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("photoStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {

    private FlickrService flickrService;
    private ClientService clientService;

    @Autowired
    public SaveClientPhoto(FlickrService flickrService, ClientService clientService) {
        this.flickrService = flickrService;
        this.clientService = clientService;
    }

    @Override
    public ClientDto savePhoto(Integer id, InputStream photo, String titre) throws FlickrException {

        ClientDto clientDto = clientService.findById(id);
        String urlPhoto = flickrService.savePhoto(photo, titre);

        if(StringUtils.hasLength(urlPhoto)){
            throw new InvalidOperationException("Erreur lors de l'enregistrement de la photo du client", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }

        clientDto.setPhoto(urlPhoto);
        return clientService.save(clientDto);
    }
}
