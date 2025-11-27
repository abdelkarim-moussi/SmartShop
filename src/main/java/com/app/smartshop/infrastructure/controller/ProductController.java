package com.app.smartshop.infrastructure.controller;

import com.app.smartshop.application.dto.client.ProductRequestDTO;
import com.app.smartshop.application.dto.client.ProductResponseDTO;
import com.app.smartshop.application.service.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
