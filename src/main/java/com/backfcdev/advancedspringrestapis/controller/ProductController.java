package com.backfcdev.advancedspringrestapis.controller;

import com.backfcdev.advancedspringrestapis.dto.ClientDTO;
import com.backfcdev.advancedspringrestapis.dto.ProductDTO;
import com.backfcdev.advancedspringrestapis.model.Product;
import com.backfcdev.advancedspringrestapis.service.IProductService;
import com.backfcdev.advancedspringrestapis.utils.PaginationLinksUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final IProductService productService;
    private final ModelMapper mapper;
    private final PaginationLinksUtils paginationLinksUtils;

    /**
     * Search for products, either by name or price, or both
     * Example Endpoints:
     * localhost:8777/api/products?name=ora
     * localhost:8777/api/products?price=80
     * localhost:8777/api/products?name=o&price=78

     * Example Pagination:
     * localhost:8777/api/products?name=o&page=1
     */
    @GetMapping
    ResponseEntity<Page<ProductDTO>> findAllProductsByDistinctArgs(@RequestParam Optional<String> name, @RequestParam Optional<Double> price,
                                @PageableDefault(page = 0, size = 4) Pageable pageable, HttpServletRequest request){
        Page<Product> productsPage = productService.findByArgs(name, price, pageable);

        Page<ProductDTO> products = productsPage.map(this::convertToDto);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
        return ResponseEntity.ok().header("link",
                        paginationLinksUtils.createLinkHeader(products, uriBuilder))
                .body(products);

    }

    @PostMapping
    ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO){
        Product product = convertToEntity(productDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(convertToDto(productService.save(product)));
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductDTO> findById(@PathVariable Integer id){
        return ResponseEntity.ok(convertToDto(productService.findById(id)));
    }

    // Hateoas - Nivel 3 Richardson
    @GetMapping("/hateoas/{id}")
    EntityModel<ProductDTO> findByIdHateoas(@PathVariable Integer id){
        EntityModel<ProductDTO> resource = EntityModel.of(this.convertToDto(productService.findById(id)));
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findById(id));
        resource.add(link.withRel("product-info"));
        return resource;
    }

    @PutMapping("/{id}")
    ResponseEntity<ProductDTO> update(@PathVariable Integer id, @RequestBody ProductDTO productDTO){
        productDTO.setId(id);
        Product product = productService.update(id, convertToEntity(productDTO));
        return ResponseEntity.ok(convertToDto(product));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ProductDTO convertToDto(Product dto){
        return mapper.map(dto, ProductDTO.class);
    }
    public Product convertToEntity(ProductDTO entity){
        return mapper.map(entity, Product.class);
    }
}
