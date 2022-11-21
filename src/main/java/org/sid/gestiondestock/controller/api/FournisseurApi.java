package org.sid.gestiondestock.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sid.gestiondestock.dto.FournisseurDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/fournisseurs")
public interface FournisseurApi {

    @PostMapping(value = APP_ROOT + "/fournisseurs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un fournisseur", notes = "Cette methode permet d'enregistrer ou de modifier un fournisseur", response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur cree / modifie"),
            @ApiResponse(code = 400, message = "Le fournisseur n'est pas valide")
    })
    FournisseurDto save(@RequestBody FournisseurDto dto);

    @GetMapping(value = APP_ROOT + "/fournisseurs/{idFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un fournisseur par ID", notes = "Cette methode permet de chercher un fournisseur par son ID", response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun fournisseur n'esxiste pas dans la BDD avec l'ID fourni")
    })
    FournisseurDto findById(@PathVariable("idFournisseur") Integer id);

    @GetMapping(value = APP_ROOT + "/fournisseurs/{numTelFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un fournisseur par NUMERO TELEPHONE", notes = "Cette methode permet de chercher un fournisseur par son NUMERO TELEPHONE", response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun fournisseur n'esxiste pas dans la BDD avec NUMERO TELEPHONE fourni")
    })
    FournisseurDto findByNumTel(@PathVariable("numTelFournisseur") String numTel);

    @GetMapping(value = APP_ROOT + "/fournisseurs/{emailFournisseur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un fournisseur par EMAIL", notes = "Cette methode permet de chercher un fournisseur par son EMAIL", response = FournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun fournisseur n'esxiste pas dans la BDD avec l'EMAIL fourni")
    })
    FournisseurDto findByEmail(@PathVariable("emailFournisseur") String email);

    @GetMapping(value = APP_ROOT + "/fournisseurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des fournisseurs", notes = "Cette methode permet de chercher et renvoyer la liste des fournisseurs qui existe dans la BDD", responseContainer = "List<FournisseurDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des fournisseurs / une liste vide")
    })
    List<FournisseurDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/fournisseurs/delete/{idFournisseur}")
    @ApiOperation(value = "Supprimer un fournisseur", notes = "Cette methode permet de supprimer un fournisseur par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le fournisseur a ete supprime")
    })
    void delete(@PathVariable("idFournisseur") Integer id);
}
