package org.sid.gestiondestock.services;

import org.sid.gestiondestock.dto.ArticleDto;
import org.sid.gestiondestock.dto.LigneCommandeClientDto;
import org.sid.gestiondestock.dto.LigneCommandeFournisseurDto;
import org.sid.gestiondestock.dto.LigneVenteDto;

import java.util.List;

public interface ArticleService {

    ArticleDto save(ArticleDto dto);
    ArticleDto findById(Integer id);
    ArticleDto findByCodeArticle(String codeArticle);
    List<ArticleDto> findAll();

    List<LigneVenteDto> findHistoriqueVentes(Integer idArticle);
    List<LigneCommandeClientDto> findHistoriqueCommandeClient(Integer idArticle);
    List<LigneCommandeFournisseurDto> findHistoriqueCommandeFournisseur(Integer idArticle);
    List<ArticleDto> findAllArticleByIdCategory(Integer idCategory);

    void delete(Integer id);
}
