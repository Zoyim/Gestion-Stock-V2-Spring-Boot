package org.sid.gestiondestock.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sid.gestiondestock.dto.CategoryDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/categories")
public interface CategoryApi {

    @PostMapping(value = APP_ROOT + "/categories/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une categorie", notes = "Cette methode permet d'enregistrer ou de modifier une categorie", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La categorie cree / modifie"),
            @ApiResponse(code = 400, message = "La categorie n'est pas valide")
    })
    CategoryDto save(@RequestBody CategoryDto dto);

    @GetMapping(value = APP_ROOT + "/categories/{idCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie par ID", notes = "Cette methode permet de chercher une categorie par son ID", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La categorie a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune categorie n'esxiste pas dans la BDD avec l'ID fourni")
    })
    CategoryDto findById(@PathVariable("idCategory") Integer id);

    @GetMapping(value = APP_ROOT + "/categories/{codeCategory}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une categorie par CODE", notes = "Cette methode permet de chercher une categorie par son CODE", response = CategoryDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La categorie a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune categorie n'esxiste pas dans la BDD avec le CODE fourni")
    })
    CategoryDto findByCodeCategory(@PathVariable("codeCategory") String code);

    @GetMapping(value = APP_ROOT + "/categories/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des categories", notes = "Cette methode permet de chercher et renvoyer la liste des categories qui existe dans la BDD", responseContainer = "List<CategoryDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des categories / une liste vide")
    })
    List<CategoryDto> findAll();

    @DeleteMapping(value = APP_ROOT + "/categories/delete/{idCategory}")
    @ApiOperation(value = "Supprimer une categorie", notes = "Cette methode permet de supprimer une categorie par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La categorie a ete supprime")
    })
    void delete(@PathVariable("idCategory") Integer id);
}
