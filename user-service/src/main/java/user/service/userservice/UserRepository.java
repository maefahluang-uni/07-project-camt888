package user.service.userservice;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public List<User> findAll();

    public List<User> findById(String id);

    public List<User> findByUsername(String username);

}
