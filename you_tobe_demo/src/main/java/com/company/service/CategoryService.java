package com.company.service;

import com.company.dto.category.CategoryCreateDTO;
import com.company.dto.category.CategoryDTO;
import com.company.entity.CategoryEntity;
import com.company.enums.CategoryStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryCreateDTO dto) {

        CategoryEntity categoryEntity = new CategoryEntity();
        save(dto,categoryEntity);

        return getCategoryDTO(categoryEntity);
    }
    public CategoryDTO getCategoryDTO(CategoryEntity categoryEntity){

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCreatedDate(categoryEntity.getCreatedDate());
        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setName(categoryEntity.getName());
        categoryDTO.setVisible(categoryEntity.getVisible());
        categoryDTO.setStatus(categoryEntity.getStatus());

        return categoryDTO;
    }

    public CategoryDTO update(Integer id, CategoryCreateDTO dto) {

        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()){
            throw new ItemNotFoundException("This region id not fount");
        }

        CategoryEntity categoryEntity = optional.get();
        save(dto,categoryEntity);

        return getCategoryDTO(categoryEntity);
    }

    private void save(CategoryCreateDTO dto, CategoryEntity categoryEntity){

        categoryEntity.setName(dto.getName());

        categoryRepository.save(categoryEntity);
    }

    public CategoryDTO changeVisible(Integer id) {

        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()){
            throw new BadRequestException("Region not fount");
        }

        CategoryEntity categoryEntity = optional.get();
        categoryEntity.setVisible(!categoryEntity.getVisible());

        categoryRepository.save(categoryEntity);

        return getCategoryDTO(categoryEntity);
    }

    public List<CategoryDTO> getAll() {

        Iterable<CategoryEntity> iterable = categoryRepository.findAll();

        List<CategoryDTO> allRegion = new ArrayList<>();
        iterable.forEach(categoryEntity -> {

            CategoryDTO categoryDTO = getCategoryDTO(categoryEntity);
            allRegion.add(categoryDTO);
        });

        return allRegion;
    }
    public CategoryEntity get(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Category not found");
        });
    }

    public CategoryEntity getByKey(String categoryKey) {

        return categoryRepository
                .findByNameAndStatusAndVisible(categoryKey, CategoryStatus.ACTIVE, Boolean.TRUE)
                .orElseThrow(() -> {
            throw new ItemNotFoundException("Category not found");
        });

    }

//    public CategoryDTO getCategoryDTO(CategoryEntity entity){
//
//        CategoryDTO categoryDTO = new CategoryDTO();
//
//        categoryDTO.setCreatedDate(entity.getCreatedDate());
//        categoryDTO.setStatus(entity.getStatus());
//        categoryDTO.setKey(entity.getKey());
//        categoryDTO.setNameEn(entity.getNameEn());
//        categoryDTO.setNameRu(entity.getNameRu());
//        categoryDTO.setNameUz(entity.getNameUz());
//
//        return categoryDTO;
//    }
}
