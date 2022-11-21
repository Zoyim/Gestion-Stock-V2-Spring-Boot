package org.sid.gestiondestock.controller.api;

import com.flickr4java.flickr.FlickrException;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/photos")
public interface PhotoApi {

    @PostMapping(value = APP_ROOT + "/photos/{id}/{titre}/{context}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Object savePhoto(@PathVariable("context") String context, @PathVariable("id") Integer id, @RequestPart("file") MultipartFile photo, @PathVariable("titre") String titre) throws IOException, FlickrException;
}
