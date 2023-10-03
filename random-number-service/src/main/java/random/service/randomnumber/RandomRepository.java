package random.service.randomnumber;
import  lab.end2end.concert.domain.Random;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface RandomRepository extends CrudRepository<Random, Long>{
    List<Random> findAll();
}
