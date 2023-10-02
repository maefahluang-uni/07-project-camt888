package lab.end2end.concert.services;
import lab.end2end.concert.domain.Concert;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ConcertRepository extends CrudRepository<Concert, Long>{
    List<Concert> findAll();
}
