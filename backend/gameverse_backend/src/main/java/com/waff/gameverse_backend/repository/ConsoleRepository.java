package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Console;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier @Repository
public interface ConsoleRepository extends ProductRepository<Console> {
}
