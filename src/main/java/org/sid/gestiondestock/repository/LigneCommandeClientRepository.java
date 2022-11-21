package org.sid.gestiondestock.repository;

import org.sid.gestiondestock.model.LigneCommandeClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LigneCommandeClientRepository extends JpaRepository<LigneCommandeClient, Integer> {

    List<LigneCommandeClient> findAllByCommandeClientId(Integer idCommande);

    List<LigneCommandeClient> findAllByArticleId(Integer idArticle);
}
