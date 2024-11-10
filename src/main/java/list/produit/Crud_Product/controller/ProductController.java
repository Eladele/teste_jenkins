package list.produit.Crud_Product.controller;

import list.produit.Crud_Product.entity.Product;
import list.produit.Crud_Product.repository.ProductRepository;
import list.produit.Crud_Product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200") // Permettre les requêtes depuis Angular
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    //    @GetMapping
//    public ResponseEntity<List<Product>> getProducts(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int perPage) {
//        List<Product> products = productService.getProducts(page, perPage);
//        return new ResponseEntity<>(products, HttpStatus.OK);
//    }
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int perPage) {
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Product> products = productService.getProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }



//@GetMapping
//public ResponseEntity<Page<Product>> getProducts(
//        @RequestParam(defaultValue = "0") int page,
//        @RequestParam(defaultValue = "10") int perPage) {
//
//    // Create a PageRequest object
//    PageRequest pageRequest = PageRequest.of(page, perPage);
//
//    // Fetch the products using the service
//    Page<Product> productPage = productService.getProducts(pageRequest);
//
//    // Return the page of products
//    return ResponseEntity.ok(productPage);
//}


    // Obtenir un produit par ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/products/batch")
    public ResponseEntity<List<Product>> addProducts(@RequestBody List<Product> products) {
        List<Product> savedProducts = productRepository.saveAll(products);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducts);
    }

    // Ajouter un nouveau produit
    @PostMapping(path = "products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // Mettre à jour un produit existant
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {
        Optional<Product> updatedProduct = productService.updateProduct(id, product);
        return updatedProduct.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Supprimer un produit par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}