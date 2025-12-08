package com.app.smartshop.infrastructure.controller;

import com.app.smartshop.domain.entity.search.ProductCriteria;
import com.app.smartshop.application.dto.product.ProductRequestDTO;
import com.app.smartshop.application.dto.product.ProductResponseDTO;
import com.app.smartshop.application.service.IProductService;
import com.app.smartshop.application.dto.DomainPageRequest;
import org.springframework.data.domain.Page;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createNewProduct(@RequestBody @Valid ProductRequestDTO product){
        ProductResponseDTO savedProduct = productService.createProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("update")
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestParam(value = "id") String id,@RequestBody @Valid ProductRequestDTO product){
        ProductResponseDTO saved = productService.updateProduct(id,product);
        return ResponseEntity.ok(saved);
    }

    @GetMapping(params = "id")
    public ResponseEntity<ProductResponseDTO> getProductById(@RequestParam(value = "id") String id){
        ProductResponseDTO existedProduct = productService.findProductById(id);
        return ResponseEntity.ok(existedProduct);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestParam(value = "id") String id){
        productService.deleteProductById(id);
        return ResponseEntity.ok("product deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductResponseDTO>> getAllProduct(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortBy", defaultValue = "unitPrice") String sortBy,
            @RequestParam(name = "sortDir", defaultValue = "DESC") String sortDir,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "unitPrice", required = false) BigDecimal price

    ){
        DomainPageRequest pageRequest = DomainPageRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .sortDir(sortDir)
                .build();

        ProductCriteria filters = ProductCriteria.builder()
                .name(name)
                .unitPrice(price)
                .build();

        Page<ProductResponseDTO> responsePage = productService.findAllProducts(pageRequest,filters);
        return ResponseEntity.ok(responsePage);
    }

}
