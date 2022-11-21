package org.sid.gestiondestock.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.sid.gestiondestock.dto.CommandeClientDto;
import org.sid.gestiondestock.dto.LigneCommandeClientDto;
import org.sid.gestiondestock.model.EtatCommande;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.sid.gestiondestock.utils.Constants.APP_ROOT;

@Api(APP_ROOT + "/commandeclients")
public interface CommandeClientApi {

    @PostMapping(value = APP_ROOT + "/commandeclients/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Enregistrer une commande client", notes = "Cette methode permet d'enregistrer ou de modifier une commande client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande client cree / modifie"),
            @ApiResponse(code = 400, message = "La commande client n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> save(@RequestBody CommandeClientDto dto);

    @PatchMapping(value = APP_ROOT + "/commandeclients/update/etat/{idCommande}/{etatCommande}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une commande client", notes = "Cette methode permet de modifier une commande client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande client modifie"),
            @ApiResponse(code = 400, message = "La commande client n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> updateEtatCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("etatCommande") EtatCommande etatCommande);

    @PatchMapping(value = APP_ROOT + "/commandeclients/update/quantite/{idCommande}/{idLigneCommande}/{quantite}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier une quantite commande client", notes = "Cette methode permet de modifier une quantite commande client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La quantite commande client modifie"),
            @ApiResponse(code = 400, message = "La commande client n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> updateQuantiteCommande(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("quantite") BigDecimal quantite);

    @PatchMapping(value = APP_ROOT + "/commandeclients/update/client/{idCommande}/{idClient}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un client de la commande client", notes = "Cette methode permet de modifier un client de la commande client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Le client de la commande client modifie"),
            @ApiResponse(code = 400, message = "La commande client n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> updateClient(@PathVariable("idCommande") Integer idCommande, @PathVariable("idClient") Integer idClient);

    @PatchMapping(value = APP_ROOT + "/commandeclients/update/article/{idCommande}/{idLigneCommande}/{idArticle}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modifier un article de la ligne commande client", notes = "Cette methode permet de modifier un article de la ligne commande client", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article de la ligne commande client modifie"),
            @ApiResponse(code = 400, message = "La commande client n'est pas valide")
    })
    ResponseEntity<CommandeClientDto> updateArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande, @PathVariable("idArticle") Integer idArticle);

    @GetMapping(value = APP_ROOT + "/commandeclients/{idCmdClt}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande client par ID", notes = "Cette methode permet de chercher une commande client par son ID", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande client a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune commande client n'esxiste pas dans la BDD avec l'ID fourni")
    })
    ResponseEntity<CommandeClientDto> findById(@PathVariable("idCmdClt") Integer id);

    @GetMapping(value = APP_ROOT + "/commandeclients/{codeCmdClt}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Rechercher une commande client par CODE", notes = "Cette methode permet de chercher une commande client par son CODE", response = CommandeClientDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande client a ete trouver dans la BDD"),
            @ApiResponse(code = 404, message = "Aucune commande client n'esxiste pas dans la BDD avec le CODE fourni")
    })
    ResponseEntity<CommandeClientDto> findByCode(@PathVariable("codeCmdClt") String code);

    @GetMapping(value = APP_ROOT + "/commandeclients/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des commandes clients", notes = "Cette methode permet de chercher et renvoyer la liste des commandes clients qui existe dans la BDD", responseContainer = "List<CommandeClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des commandes clients / une liste vide")
    })
    ResponseEntity<List<CommandeClientDto>>findAll();

    @GetMapping(value = APP_ROOT + "/commandeclients/lignesCommandes/{idCommande}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Renvoi la liste des lignes des commandes clients", notes = "Cette methode permet de chercher et renvoyer la liste des lignes des commandes clients qui existe dans la BDD", responseContainer = "List<LigneCommandeClientDto>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La liste des lignes des commandes clients / une liste vide")
    })
    List<LigneCommandeClientDto> findAllLignesCommandesClientByCommmandeClientId(@PathVariable("idCommande") Integer idCommande);

    @DeleteMapping(value = APP_ROOT + "/commandeclients/delete/{idCmdClt}")
    @ApiOperation(value = "Supprimer une commande client", notes = "Cette methode permet de supprimer une commande client par son ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "La commande client a ete supprime")
    })
    ResponseEntity delete(@PathVariable("idCmdClt") Integer id);

    @DeleteMapping(value = APP_ROOT + "/commandeclients/delete/article/{idCommande}/{idLigneCommande}")
    @ApiOperation(value = "Supprimer un article", notes = "Cette methode permet de supprimer un article par ID Commande et ID ligne commande")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "L'article a ete supprime")
    })
    ResponseEntity<CommandeClientDto> deleteArticle(@PathVariable("idCommande") Integer idCommande, @PathVariable("idLigneCommande") Integer idLigneCommande);
}
