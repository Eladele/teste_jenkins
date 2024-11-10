package list.produit.Crud_Product.repository;

import list.produit.Crud_Product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
