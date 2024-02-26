package com.svalero.euroapi.repository;
import com.svalero.euroapi.domain.Edition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EditionRepository extends CrudRepository<Edition, Long> {
    List<Edition> findAll();
}
