package org.sid.gestiondestock.controller;

import com.flickr4java.flickr.FlickrException;
import org.sid.gestiondestock.controller.api.PhotoApi;
import org.sid.gestiondestock.services.strategy.StrategyPhotoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class PhotoController implements PhotoApi {

    private StrategyPhotoContext strategyPhotoContext;

    @Autowired
    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    @Override
    public Object savePhoto(String context, Integer id, MultipartFile photo, String titre) throws IOException, FlickrException {
        return strategyPhotoContext.savePhoto(context, id, photo.getInputStream(), titre);
    }
}
