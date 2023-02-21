package com.backfcdev.advancedspringrestapis.controller;

import com.backfcdev.advancedspringrestapis.dto.CategoryDTO;
import com.backfcdev.advancedspringrestapis.dto.ProductDTO;
import com.backfcdev.advancedspringrestapis.model.Category;
import com.backfcdev.advancedspringrestapis.model.Product;
import com.backfcdev.advancedspringrestapis.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final ICategoryService categoryService;
    private final ModelMapper mapper;

    @GetMapping
    ResponseEntity<List<CategoryDTO>> findAll(){
        List<CategoryDTO> categories = categoryService.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    ResponseEntity<CategoryDTO> save(@RequestBody CategoryDTO categoryDTO){
        Category category = convertToEntity(categoryDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(convertToDto(categoryService.save(category)));
    }

    @GetMapping("/{id}")
    ResponseEntity<CategoryDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(convertToDto(categoryService.findById(id)));
    }

    // Hateoas - Nivel 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<CategoryDTO> findByIdHateoas(@PathVariable Integer id){
        EntityModel<CategoryDTO> resource = EntityModel.of(this.convertToDto(categoryService.findById(id)));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("category-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<CategoryDTO> update(@PathVariable Integer id, @RequestBody CategoryDTO categoryDTO){
        categoryDTO.setId(id);
        Category category = categoryService.update(id, convertToEntity(categoryDTO));
        return ResponseEntity.ok(convertToDto(category));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id){
        categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public CategoryDTO convertToDto(Category dto){
        return mapper.map(dto, CategoryDTO.class);
    }
    public Category convertToEntity(CategoryDTO entity){
        return mapper.map(entity, Category.class);
    }
}
