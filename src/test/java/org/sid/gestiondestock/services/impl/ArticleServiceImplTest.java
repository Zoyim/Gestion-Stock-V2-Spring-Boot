package org.sid.gestiondestock.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sid.gestiondestock.dto.ArticleDto;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidEntityException;
import org.sid.gestiondestock.services.ArticleService;
import org.sid.gestiondestock.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceImplTest {

    @Autowired
    private ArticleService service;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void shouldSaveArticleWithSuccess(){
        ArticleDto expectedArticle = ArticleDto.builder()
                .codeArticle("Article 1")
                .designation("Designation Article")
                .prixUnitaireHt(BigDecimal.valueOf(10))
                .tauxTva(BigDecimal.valueOf(20))
                .prixUnitaireTtc(BigDecimal.valueOf(18))
                .idEntreprise(1)
                .category(categoryService.findById(5))
                .build();

        ArticleDto savedArticle = service.save(expectedArticle);

        assertNotNull(savedArticle);
        assertNotNull(savedArticle.getId());
        assertEquals(expectedArticle.getCodeArticle(),savedArticle.getCodeArticle());
        assertEquals(expectedArticle.getDesignation(),savedArticle.getDesignation());
        assertEquals(expectedArticle.getPrixUnitaireHt(),savedArticle.getPrixUnitaireHt());
        assertEquals(expectedArticle.getTauxTva(),savedArticle.getTauxTva());
        assertEquals(expectedArticle.getPrixUnitaireTtc(),savedArticle.getPrixUnitaireTtc());
        assertEquals(expectedArticle.getIdEntreprise(),savedArticle.getIdEntreprise());
        assertEquals(expectedArticle.getCategory(),savedArticle.getCategory());

    }

    @Test
    public void shouldUpdateArticleWithSuccess(){
        ArticleDto expectedArticle = ArticleDto.builder()
                .codeArticle("Article 1")
                .designation("Designation Article")
                .prixUnitaireHt(BigDecimal.valueOf(10))
                .tauxTva(BigDecimal.valueOf(20))
                .prixUnitaireTtc(BigDecimal.valueOf(18))
                .idEntreprise(1)
                .category(categoryService.findById(5))
                .build();

        ArticleDto savedArticle = service.save(expectedArticle);

        ArticleDto articleToUpdate = savedArticle;
        articleToUpdate.setCodeArticle("Article Update");
        articleToUpdate.setTauxTva(BigDecimal.valueOf(5));

        savedArticle = service.save(articleToUpdate);

        assertNotNull(articleToUpdate);
        assertNotNull(articleToUpdate.getId());
        assertEquals(articleToUpdate.getCodeArticle(),savedArticle.getCodeArticle());
        assertEquals(articleToUpdate.getDesignation(),savedArticle.getDesignation());
        assertEquals(articleToUpdate.getPrixUnitaireHt(),savedArticle.getPrixUnitaireHt());
        assertEquals(articleToUpdate.getTauxTva(),savedArticle.getTauxTva());
        assertEquals(articleToUpdate.getPrixUnitaireTtc(),savedArticle.getPrixUnitaireTtc());
        assertEquals(articleToUpdate.getIdEntreprise(),savedArticle.getIdEntreprise());
        assertEquals(articleToUpdate.getCategory(),savedArticle.getCategory());
    }

    @Test
    public void shouldThrowInvalidEntityException(){
        ArticleDto expectedArticle = ArticleDto.builder().build();

        InvalidEntityException expectedException = assertThrows(InvalidEntityException.class, () -> service.save(expectedArticle));

        assertEquals(ErrorCodes.ARTICLE_NOT_VALID, expectedException.getErrorCode());
        assertEquals(6, expectedException.getErrors().size());
        assertEquals("Veuillez renseigner le code de l'article", expectedException.getErrors().get(0));
        assertEquals("Veuillez renseigner la designation de l'article", expectedException.getErrors().get(1));
        assertEquals("Veuillez renseigner le prix unitaire HT de l'article", expectedException.getErrors().get(2));
        assertEquals("Veuillez renseigner le taux TVA de l'article", expectedException.getErrors().get(3));
        assertEquals("Veuillez renseigner le prix unitaire TTC de l'article", expectedException.getErrors().get(4));
        assertEquals("Veuillez selectionner une categorie", expectedException.getErrors().get(5));
    }

}
