package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Product;
import com.waff.gameverse_backend.embedded.ProductType;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *
 * Unser Base-Repository
 * Springboot benötigt den @Primary Tag sonst wüsste es nicht wie es die Beans verwenden sollte.
 * Unsere Abgeleiteten Repositories sind alle als Qualifier eingetragen, d.h diese werden konsumiert (benutzt) wenn das
 * Programm löuft.
 * Wir benutzen ein JPA Repository weil dieses die Funktionalität von CRUD-Repo hat UND von Pagnation & Sorting Repo.
 *
 */
@Primary @Repository
public interface ProductRepository extends JpaRepository<Product,  Long>
{
    List<Product> findProductByType(ProductType productType);
}
