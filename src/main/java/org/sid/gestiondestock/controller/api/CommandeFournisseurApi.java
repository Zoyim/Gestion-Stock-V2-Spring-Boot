package org.sid.gestiondestock.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sid.gestiondestock.dto.CommandeFournisseurDto;
import org.sid.gestiondestock.dto.LigneCommandeFournisseurDto;
import org.sid.gestiondestock.model.EtatCommande;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/commandefournisseurs")
public interface CommandeFournisseurApi {

    @PostMapping(value = APP_ROOT + "/commandefournisseurs/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une commande fournisseur", notes = "Cette methode permet d'enregistrer ou de modifier une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur cree / modifie"),
            @ApiResponse(code = 400, message = "La commande fournisseur n'est pas valide")
    })
    CommandeFournisseurDto save(CommandeFournisseurDto dto);

    @PatchMapping(value = APP_ROOT + "/commandefournisseurs/update/etat/{idCommande}/{etatCommande}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une commande fournisseur", notes = "Cette methode permet de modifier une commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur modifie"),
            @ApiResponse(code = 400, message = "La commande fournisseur n'est pas valide")
    })
    CommandeFournisseurDto updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value = APP_ROOT + "/commandefournisseurs/update/quantite/{idCommande}/{idLigneCommande}/{quantite}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une quantite commande fournisseur", notes = "Cette methode permet de modifier une quantite commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La quantite commande client modifie"),
            @ApiResponse(code = 400, message = "La commande client n'est pas valide")
    })
    CommandeFournisseurDto updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(value = APP_ROOT + "/commandefournisseurs/update/client/{idCommande}/{idFournisseur}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un client de la commande fournisseur", notes = "Cette methode permet de modifier un fournisseur de la commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client de la commande fournisseur modifie"),
            @ApiResponse(code = 400, message = "La commande fournisseur n'est pas valide")
    })
    CommandeFournisseurDto updateFournisseur(@PathVariable("idCommande") Integer idCommande, @PathVariable("idFournisseur") Integer idFournisseur);

    @PatchMapping(value = APP_ROOT + "/commandefournisseurs/update/article/{idCommande}/{idLigneCommande}/{idArticle}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un article de la ligne commande fournisseur", notes = "Cette methode permet de modifier un article de la ligne commande fournisseur", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article de la ligne commande fournisseur modifie"),
            @ApiResponse(code = 400, message = "La commande fournisseur n'est pas valide")
    })
    CommandeFournisseurDto updateArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = APP_ROOT + "/commandefournisseurs/{idCmdFrns}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande fournisseur par ID", notes = "Cette methode permet de chercher une commande fournisseur par son ID", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune commande fournisseur n'esxiste pas dans la BDD avec l'ID fourni")
    })
    CommandeFournisseurDto findById(@PathVariable("idCmdFrns") Integer id);

    @GetMapping(value = APP_ROOT + "/commandefournisseurs/{codeCmdFrns}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande fournisseur par CODE", notes = "Cette methode permet de chercher une commande fournisseur par son CODE", response = CommandeFournisseurDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune commande fournisseur n'esxiste pas dans la BDD avec le CODE fourni")
    })
    CommandeFournisseurDto findByCode(@PathVariable("codeCmdFrns") String code);

    @GetMapping(value = APP_ROOT + "/commandefournisseurs/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoie la liste des commandes fournisseurs", notes = "Cette methode permet de chercher et renvoyer la liste des commandes fournisseurs qui existe dans la BDD", responseContainer = "List<CommandeFournisseurDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des commandes fournisseurs / une liste vide")
    })
    List<CommandeFournisseurDto> findAll();

    @GetMapping(value = APP_ROOT + "/commandefournisseurs/lignesCommandes/{idCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des lignes des commandes fournisseurs", notes = "Cette methode permet de chercher et renvoyer la liste des lignes des commandes fournisseurs qui existe dans la BDD", responseContainer = "List<CommandeFournisseurDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des lignes des commandes clients / une liste vide")
    })
    List<LigneCommandeFournisseurDto> findAllLignesCommandesFournisseurByCommmandeFournisseurId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(value = APP_ROOT + "/commandefournisseurs/delete/{idCmdFrns}")
    @ApiOperation(value = "Supprimer une commande fournisseur", notes = "Cette methode permet de supprimer une commande fournisseur par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande fournisseur a ete supprime")
    })
    void delete(@PathVariable("idCmdFrns") Integer id);

    @DeleteMapping(value = APP_ROOT + "/commandefournisseurs/delete/article/{idCommande}/{idLigneCommande}")
    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par ID Commande et ID ligne commande")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a ete supprime")
    })
    CommandeFournisseurDto deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);
}
