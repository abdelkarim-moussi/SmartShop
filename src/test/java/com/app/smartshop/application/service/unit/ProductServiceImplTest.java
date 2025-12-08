package com.app.smartshop.application.service.unit;

import com.app.smartshop.application.dto.DomainPageRequest;
import com.app.smartshop.application.dto.product.ProductRequestDTO;
import com.app.smartshop.application.dto.product.ProductResponseDTO;
import com.app.smartshop.application.exception.DataNotExistException;
import com.app.smartshop.application.exception.InvalidParameterException;
import com.app.smartshop.application.exception.ProductExistByNameException;
import com.app.smartshop.application.mapper.ProductMapper;
import com.app.smartshop.application.service.ProductServiceImpl;
import com.app.smartshop.domain.entity.Product;
import com.app.smartshop.domain.entity.search.ProductCriteria;
import com.app.smartshop.domain.repository.JpaProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private JpaProductRepository productRepository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product mockProduct;
    private ProductRequestDTO mockRequestDTO;
    private ProductResponseDTO mockResponseDTO;
    private final String PRODUCT_ID = "P001";

    @BeforeEach
    void setUp() {
        mockProduct = Product.builder()
                .id(PRODUCT_ID)
                .name("Laptop X")
                .unitPrice(BigDecimal.valueOf(1200.00))
                .stock(5)
                .build();

        mockRequestDTO = ProductRequestDTO.builder()
                .name("Laptop X")
                .unitPrice(BigDecimal.valueOf(1200.00))
                .stock(5)
                .build();

        mockResponseDTO = ProductResponseDTO.builder()
                .id(PRODUCT_ID)
                .name("Laptop X")
                .unitPrice(BigDecimal.valueOf(1200.00))
                .stock(5)
                .build();
    }

    @Test
    void createProduct_ShouldSaveNewProduct_WhenNameIsUnique() {
        // Arrange
        when(productRepository.existsByName("Laptop X")).thenReturn(false);
        when(mapper.toEntity(mockRequestDTO)).thenReturn(mockProduct);
        when(productRepository.save(mockProduct)).thenReturn(mockProduct);
        when(mapper.toResponseDTO(mockProduct)).thenReturn(mockResponseDTO);

        // Act
        ProductResponseDTO result = productService.createProduct(mockRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(PRODUCT_ID, result.getId());
        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void createProduct_ShouldThrowProductExistException_WhenNameExists() {
        // Arrange
        when(productRepository.existsByName("Laptop X")).thenReturn(true);

        // Act & Assert
        assertThrows(ProductExistByNameException.class,
                () -> productService.createProduct(mockRequestDTO),
                "Should throw exception if product name already exists.");

        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void createProduct_ShouldThrowInvalidParameterException_WhenRequestIsNull() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> productService.createProduct(null),
                "product data can not be null");
    }

    @Test
    void updateProduct_ShouldUpdateAndReturnProduct_WhenIdExists() {
        // Arrange
        ProductRequestDTO updateRequest = ProductRequestDTO.builder()
                .name("Laptop X Pro")
                .unitPrice(BigDecimal.valueOf(1500.00))
                .stock(10)
                .build();

        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(mockProduct));
        when(productRepository.save(any(Product.class))).thenReturn(mockProduct);
        when(mapper.toResponseDTO(mockProduct)).thenReturn(mockResponseDTO);

        // Act
        ProductResponseDTO result = productService.updateProduct(PRODUCT_ID, updateRequest);

        // Assert
        assertNotNull(result);

        assertEquals("Laptop X Pro", mockProduct.getName());
        assertEquals(10, mockProduct.getStock());

        verify(productRepository, times(1)).save(mockProduct);
    }

    @Test
    void updateProduct_ShouldThrowDataNotExistException_WhenIdNotFound() {
        // Arrange
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> productService.updateProduct(PRODUCT_ID, mockRequestDTO),
                "Should throw exception if product id not found.");
    }

    @Test
    void updateProduct_ShouldThrowInvalidParameterException_WhenIdIsNull() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> productService.updateProduct(null, mockRequestDTO),
                "id can not be null or empty");
    }

    @Test
    void findProductById_ShouldReturnProduct_WhenIdExists() {
        // Arrange
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(mockProduct));
        when(mapper.toResponseDTO(mockProduct)).thenReturn(mockResponseDTO);

        // Act
        ProductResponseDTO result = productService.findProductById(PRODUCT_ID);

        // Assert
        assertNotNull(result);
        assertEquals(PRODUCT_ID, result.getId());
        verify(productRepository, times(1)).findById(PRODUCT_ID);
    }

    @Test
    void findProductById_ShouldThrowDataNotExistException_WhenIdNotFound() {
        // Arrange
        when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> productService.findProductById(PRODUCT_ID),
                "there is no product with this id");
    }

    @Test
    void findProductById_ShouldThrowInvalidParameterException_WhenIdIsEmpty() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> productService.findProductById(""),
                "id can not be null or empty");
    }

    @Test
    void deleteProductById_ShouldCallDelete_WhenIdExists() {
        // Arrange
        when(productRepository.existsById(PRODUCT_ID)).thenReturn(true);

        // Act
        productService.deleteProductById(PRODUCT_ID);

        // Assert
        verify(productRepository, times(1)).deleteById(PRODUCT_ID);
    }

    @Test
    void deleteProductById_ShouldThrowDataNotExistException_WhenIdNotFound() {
        // Arrange
        when(productRepository.existsById(PRODUCT_ID)).thenReturn(false);

        // Act & Assert
        assertThrows(DataNotExistException.class,
                () -> productService.deleteProductById(PRODUCT_ID),
                "there is no product with this id");

        verify(productRepository, never()).deleteById(anyString());
    }

    @Test
    void deleteProductById_ShouldThrowInvalidParameterException_WhenIdIsNull() {
        // Act & Assert
        assertThrows(InvalidParameterException.class,
                () -> productService.deleteProductById(null),
                "id can not be null or empty");
    }

    @Test
    void findAllProducts_ShouldReturnPageOfDTOs_WhenCalledWithPaginationAndFilters() {
        // Arrange
        DomainPageRequest pageRequest = DomainPageRequest.builder()
                .page(0).size(10).sortBy("name").sortDir("asc").build();
        ProductCriteria filters = ProductCriteria.builder().name("Laptop").build();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        Page<Product> mockProductPage = new PageImpl<>(Collections.singletonList(mockProduct), pageable, 1);
        when(productRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockProductPage);

        when(mapper.toResponseDTO(mockProduct)).thenReturn(mockResponseDTO);

        // Act
        Page<ProductResponseDTO> resultPage = productService.findAllProducts(pageRequest, filters);

        // Assert
        assertNotNull(resultPage);
        assertFalse(resultPage.isEmpty());
        assertEquals(1, resultPage.getTotalElements());
        assertEquals(mockResponseDTO.getName(), resultPage.getContent().get(0).getName());

        verify(productRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }
}
