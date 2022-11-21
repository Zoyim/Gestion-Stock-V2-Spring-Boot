package org.sid.gestiondestock.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sid.gestiondestock.dto.UtilisateurDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/utilisateurs")
public interface UtilisateurApi {

    @PostMapping(value = APP_ROOT + "/utilisateurs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer un utilisateur", notes = "Cette methode permet d'enregistrer ou de modifier un utilisateur", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur cree / modifie"),
            @ApiResponse(code = 400, message = "L'utilisateur n'est pas valide")
    })
    UtilisateurDto save(@RequestBody UtilisateurDto dto);

    @GetMapping(value = APP_ROOT + "/utilisateurs/{idUtilisateur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur par ID", notes = "Cette methode permet de chercher un utilisateur par son ID", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun utilisateur n'esxiste pas dans la BDD avec l'ID fourni")
    })
    UtilisateurDto findById(@PathVariable("idUtilisateur") Integer id);

    @GetMapping(value = APP_ROOT + "/utilisateurs/{numTelUtilisateur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur par NUMERO TELEPHONE", notes = "Cette methode permet de chercher un utilisateur par son NUMERO TELEPHONE", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun utilisateur n'esxiste pas dans la BDD avec NUMERO TELEPHONE fourni")
    })
    UtilisateurDto findByNumTel(@PathVariable("numTelUtilisateur") String numTel);

    @GetMapping(value = APP_ROOT + "/utilisateurs/{emailUtilisateur}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher un utilisateur par EMAIL", notes = "Cette methode permet de chercher un utilisateur par son EMAIL", response = UtilisateurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun utilisateur n'esxiste pas dans la BDD avec l'EMAIL fourni")
    })
    UtilisateurDto findByEmail(@PathVariable("emailUtilisateur") String email);

    @GetMapping(value = APP_ROOT + "/utilisateurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des utilisateurs", notes = "Cette methode permet de chercher et renvoyer la liste des utilisateurs qui existe dans la BDD", responseContainer = "List<UtilisateurDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des utilisateurs / une liste vide")
    })
    List<UtilisateurDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/utilisateurs/delete/{idUtilisateur}")
    @ApiOperation(value = "Supprimer un utilisateur", notes = "Cette methode permet de supprimer un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'utilisateur a ete supprime")
    })
    void delete(@PathVariable("idUtilisateur") Integer id);
}
