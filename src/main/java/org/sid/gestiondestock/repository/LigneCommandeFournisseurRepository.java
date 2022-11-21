package org.sid.gestiondestock.repository;

import org.sid.gestiondestock.model.LigneCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LigneCommandeFournisseurRepository extends JpaRepository<LigneCommandeFournisseur, Integer> {

    List<LigneCommandeFournisseur> findAllByCommandeFournisseursId(Integer idCommande);

    List<LigneCommandeFournisseur> findAllByArticleId(Integer idArticle);

}
