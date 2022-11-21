package org.sid.gestiondestock.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sid.gestiondestock.dto.EntrepriseDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/entreprises")
public interface EntrepriseApi {

    @PostMapping(value = APP_ROOT + "/entreprises/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une entreprise", notes = "Cette methode permet d'enregistrer ou de modifier une entreprise", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Entreprise cree / modifie"),
            @ApiResponse(code = 400, message = "L'Entreprise n'est pas valide")
    })
    EntrepriseDto save(@RequestBody EntrepriseDto dto);

    @GetMapping(value = APP_ROOT + "/entreprises/{idEntreprise}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une entreprise par ID", notes = "Cette methode permet de chercher une entreprise par son ID", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Entreprise a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune Entreprise n'esxiste pas dans la BDD avec l'ID fourni")
    })
    EntrepriseDto findById(@PathVariable("idEntreprise") Integer id);

    @GetMapping(value = APP_ROOT + "/entreprises/{numTelEntreprise}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une entreprise par NUMERO TELEPHONE", notes = "Cette methode permet de chercher une entreprise par son NUMERO TELEPHONE", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Entreprise a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucun entreprise n'esxiste pas dans la BDD avec NUMERO TELEPHONE fourni")
    })
    EntrepriseDto findByNumTel(@PathVariable("numTelEntreprise") String numTel);

    @GetMapping(value = APP_ROOT + "/entreprises/{emailEntreprise}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une entreprise par EMAIL", notes = "Cette methode permet de chercher une entreprise par son EMAIL", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Entreprise a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune entreprise n'esxiste pas dans la BDD avec l'EMAIL fourni")
    })
    EntrepriseDto findByEmail(@PathVariable("emailEntreprise") String email);

    @GetMapping(value = APP_ROOT + "/entreprises/{nomEntreprise}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une entreprise par NOM", notes = "Cette methode permet de chercher une entreprise par son NOM", response = EntrepriseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Entreprise a ete trouve dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune entreprise n'esxiste pas dans la BDD avec le NOM fourni")
    })
    EntrepriseDto findByNom(@PathVariable("nomEntreprise") String nom);

    @GetMapping(value = APP_ROOT + "/entreprises/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des entreprises", notes = "Cette methode permet de chercher et renvoyer la liste des entreprises qui existe dans la BDD", responseContainer = "List<EntrepriseDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des entreprises / une liste vide")
    })
    List<EntrepriseDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/entreprises/delete/{idEntreprise}")
    @ApiOperation(value = "Supprimer une entreprise", notes = "Cette methode permet de supprimer une entreprise par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'Entreprise a ete supprime")
    })
    void delete(@PathVariable("idEntreprise") Integer id);
}
