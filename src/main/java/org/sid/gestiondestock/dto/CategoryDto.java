package org.sid.gestiondestock.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.sid.gestiondestock.model.Category;

import java.util.List;

@Data
@Builder
public class CategoryDto {

    private Integer id;
    private String code;
    private String designation;
    private Integer idEntreprise;
    @JsonIgnore
    private List<ArticleDto> articles;

    public static CategoryDto fromEntity(Category category){
        if(category == null){
            return  null;
            // TODO thrown an Exception
        }
    // Category -> CategoryDto
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .designation(category.getDesignation())
                .idEntreprise(category.getIdEntreprise())
                .build();
    }

    public static Category toEntity(CategoryDto categoryDto){
        if(categoryDto == null){
            return null;
        }

        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setCode(categoryDto.getCode());
        category.setDesignation(categoryDto.getDesignation());
        category.setIdEntreprise(categoryDto.getIdEntreprise());

        return category;
    }
}
