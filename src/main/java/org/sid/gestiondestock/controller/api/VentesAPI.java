package org.sid.gestiondestock.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sid.gestiondestock.dto.VentesDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/ventes")
public interface VentesAPI {

    @PostMapping(value = APP_ROOT + "/ventes/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une vente", notes = "Cette methode permet d'enregistrer ou de modifier une vente", response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La vente creee / modifiee"),
            @ApiResponse(code = 400, message = "La vente n'est pas valide")
    })
    VentesDto save(@RequestBody VentesDto dto);

    @GetMapping(value = APP_ROOT + "/ventes/{idVente}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une vente par ID", notes = "Cette methode permet de chercher une vente par son ID", response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La vente a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune vente n'esxiste pas dans la BDD avec l'ID fourni")
    })
    VentesDto findById(@PathVariable("idVente") Integer id);

    @GetMapping(value = APP_ROOT + "/ventes/{codeVente}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une vente par CODE", notes = "Cette methode permet de chercher une vente par son CODE", response = VentesDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La vente a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune vente n'esxiste pas dans la BDD avec le CODE fourni")
    })
    VentesDto findByCode(@PathVariable("codeVente") String code);

    @GetMapping(value = APP_ROOT + "/ventes/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des ventes", notes = "Cette methode permet de chercher et renvoyer la liste des ventes qui existe dans la BDD", responseContainer = "List<VentesDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des ventes / une liste vide")
    })
    List<VentesDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/ventes/delete/{idVente}")
    @ApiOperation(value = "Supprimer une vente", notes = "Cette methode permet de supprimer une vente par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La vente a ete supprimee")
    })
    void delete(@PathVariable("idVente") Integer id);
}
