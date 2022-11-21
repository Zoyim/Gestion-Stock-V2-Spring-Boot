package org.sid.gestiondestock.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.sid.gestiondestock.dto.CategoryDto;
import org.sid.gestiondestock.exception.EntityNotFoundException;
import org.sid.gestiondestock.exception.ErrorCodes;
import org.sid.gestiondestock.exception.InvalidEntityException;
import org.sid.gestiondestock.exception.InvalidOperationException;
import org.sid.gestiondestock.model.Article;
import org.sid.gestiondestock.repository.ArticleRepository;
import org.sid.gestiondestock.repository.CategoryRepository;
import org.sid.gestiondestock.services.CategoryService;
import org.sid.gestiondestock.validator.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ArticleRepository articleRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        List<String> errors = CategoryValidator.validate(dto);
        if(!errors.isEmpty()){
            log.error("Category is not valid {}", dto);
            throw new InvalidEntityException("La categorie n'est pas valide", ErrorCodes.CATEGORY_NOT_VALID, errors);
        }
        return CategoryDto.fromEntity(
                categoryRepository.save(
                        CategoryDto.toEntity(dto)
                )
        );
    }

    @Override
    public CategoryDto findById(Integer id) {
        if(id == null){
            log.error("Category ID is null");
            return null;
        }
        return categoryRepository.findById(id)
                .map(CategoryDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucune categorie avec l'ID = " + id + " n'a ete trouve dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND
                ));
    }

    @Override
    public CategoryDto findByCodeCategory(String code) {
        if(!StringUtils.hasLength(code)){
            log.error("Category CODE is null");
            return null;
        }
        return categoryRepository.findCategoryByCode(code)
                .map(CategoryDto::fromEntity)
                .orElseThrow(()-> new EntityNotFoundException(
                        "Aucune categorie avec le CODE = " + code + " n'a ete trouve dans la BDD",
                        ErrorCodes.CATEGORY_NOT_FOUND
                ));

    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        if(id == null){
            log.error("Categorie ID is null");
            return;
        }

        List<Article> articles = articleRepository.findAllByCategoryId(id);
        if(!articles.isEmpty()){
            throw new InvalidOperationException("Impossible de supprimer cette categorie qui est deja utilise dans des articles", ErrorCodes.CATEGORY_ALREADY_IN_USE);
        }

        categoryRepository.deleteById(id);
    }
}
