package jke.webshopbackend2.repository;

import jke.webshopbackend2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
