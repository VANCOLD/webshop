package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 *
 * Unser Base-Repository
 * Springboot benötigt den @Primary Tag sonst wüsste es nicht wie es die Beans verwenden sollte.
 * Unsere Abgeleiteten Repositories sind alle als Qualifier eingetragen, d.h diese werden konsumiert (benutzt) wenn das
 * Programm löuft.
 * Wir benutzen ein JPA Repository weil dieses die Funktionalität von CRUD-Repo hat UND von Pagnation & Sorting Repo.
 *
 * @param <T> unser Generic Datentyp. Wird pro Unterrepository angepasst
 */
@Primary @Repository
public interface ProductRepository<T extends Product> extends JpaRepository<T,  Long> {}
