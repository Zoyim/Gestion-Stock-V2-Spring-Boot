package org.sid.gestiondestock.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sid.gestiondestock.dto.ClientDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/clients")
public interface ClientApi {

    @PostMapping(value = APP_ROOT + "/clients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un client", notes = "Cette methode permet d'enregistrer ou de modifier un client", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client cree / modifie"),
            @ApiResponse(code = 400, message = "Le client n'est pas valide")
    })
    ClientDto save(@RequestBody ClientDto dto);

    @GetMapping(value = APP_ROOT + "/clients/{idClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un client par ID", notes = "Cette methode permet de chercher un client par son ID", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun client n'esxiste pas dans la BDD avec l'ID fourni")
    })
    ClientDto findById(@PathVariable("idClient") Integer id);

    @GetMapping(value = APP_ROOT + "/clients/{numTelClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un client par NUMERO TELEPHONE", notes = "Cette methode permet de chercher un client par son NUMERO TELEPHONE", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun client n'esxiste pas dans la BDD avec NUMERO TELEPHONE fourni")
    })
    ClientDto findByNumTel(@PathVariable("numTelClient") String numTel);

    @GetMapping(value = APP_ROOT + "/clients/{emailClient}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un client par EMAIL", notes = "Cette methode permet de chercher un client par son EMAIL", response = ClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun client n'esxiste pas dans la BDD avec l'EMAIL fourni")
    })
    ClientDto findByEmail(@PathVariable("emailClient") String email);

    @GetMapping(value = APP_ROOT + "/clients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des clients", notes = "Cette methode permet de chercher et renvoyer la liste des clients qui existe dans la BDD", responseContainer = "List<ClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des clients / une liste vide")
    })
    List<ClientDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/clients/delete/{idClient}")
    @ApiOperation(value = "Supprimer un client", notes = "Cette methode permet de supprimer un client par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client a ete supprime")
    })
    void delete(@PathVariable("idClient") Integer id);
}
