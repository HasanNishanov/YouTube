package com.company.controller;

import com.company.dto.category.CategoryCreateDTO;
import com.company.dto.category.CategoryDTO;
import com.company.service.CategoryService;
import com.company.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api("Category Controller")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Category Create", notes = "Method for category create")
    @PostMapping("/adm")
    public ResponseEntity<?> create(@RequestBody CategoryCreateDTO dto) {

        log.info("Request for category created dto: {}", dto);
        CategoryDTO categoryDTO = categoryService.create(dto);

        return ResponseEntity.ok(categoryDTO);
    }

    @ApiOperation(value = "Category UPdate", notes = "Method for category update")
    @PutMapping("/adm/{id}")
    public ResponseEntity<?> update(@RequestBody CategoryCreateDTO dto,
                                    @PathVariable("id") Integer id) {

        CategoryDTO update = categoryService.update(id, dto);
        log.info("Request for category update UpdateDto: {}, categoryId: {}", dto, id);
        return ResponseEntity.ok(update);

    }

    @ApiOperation(value = "Category Change Status", notes = "Method for category change status")
    @DeleteMapping("/adm/{id}")
    public ResponseEntity<?> changeVisible(@PathVariable("id") Integer id) {

        CategoryDTO categoryDTO = categoryService.changeVisible(id);
        log.info("Request for category deleted id: {}", id);
        return ResponseEntity.ok(categoryDTO);
    }

    @ApiOperation(value = "Category All list", notes = "Method for category list ")
    @GetMapping("/adm/list")
    public ResponseEntity<?> getAllCategory() {

        List<CategoryDTO> getAllCategory = categoryService.getAll();
        log.info("Request for category list ");
        return ResponseEntity.ok(getAllCategory);
    }

}
