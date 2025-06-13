package com.accolite.JPAHibernate.service;

import com.accolite.JPAHibernate.dto.ProductDTO;
import java.util.List;

public interface ProductService {
    ProductDTO saveProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    void deleteProduct(Long id);
}
