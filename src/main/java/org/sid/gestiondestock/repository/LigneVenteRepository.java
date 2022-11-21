package org.sid.gestiondestock.repository;

import org.sid.gestiondestock.model.LigneVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneVenteRepository extends JpaRepository<LigneVente, Integer> {

    List<LigneVente> findAllByArticleId(Integer idArticle);

    List<LigneVente> findAllByVentesId(Integer idClient);
}
