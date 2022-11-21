package org.sid.gestiondestock.services;

import org.sid.gestiondestock.dto.CommandeClientDto;
import org.sid.gestiondestock.dto.LigneCommandeClientDto;
import org.sid.gestiondestock.model.EtatCommande;

import java.math.BigDecimal;
import java.util.List;

public interface CommandeClientService {

    CommandeClientDto save(CommandeClientDto dto);
    CommandeClientDto updateEtatCommande(Integer idCommande, EtatCommande etatCommande);
    CommandeClientDto updateQuantiteCommande(Integer idCommande, Integer idLigneCommande, BigDecimal quantite);
    CommandeClientDto updateClient(Integer idCommande, Integer idClient);
    CommandeClientDto updateArticle(Integer idCommande, Integer idLigneCommande, Integer idArticle);
    // Delete article ==> delete LigneCommandeClient
    CommandeClientDto deleteArticle(Integer idCommande, Integer idLigneCommande);
    CommandeClientDto findById(Integer id);
    CommandeClientDto findByCode(String code);
    List<CommandeClientDto> findAll();
    List<LigneCommandeClientDto> findAllLignesCommandesClientByCommmandeClientId(Integer idCommande);
    void delete(Integer id);
}
