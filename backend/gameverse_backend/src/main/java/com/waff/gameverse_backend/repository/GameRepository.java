package com.waff.gameverse_backend.repository;

import com.waff.gameverse_backend.datamodel.Game;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Qualifier
@Repository
public interface GameRepository extends ProductRepository<Game>  {
}
