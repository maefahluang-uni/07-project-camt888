package lab.end2end.concert.services;
import lab.end2end.concert.domain.Random;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface RandomRepository extends CrudRepository<Random, Long>{
    List<Random> findAll();
}
