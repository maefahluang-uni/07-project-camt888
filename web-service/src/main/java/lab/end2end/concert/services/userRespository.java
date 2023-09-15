package lab.end2end.concert.services;

import org.apache.tomcat.jni.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface userRespository extends CrudRepository<User, Long> {
    public List<User> findAll();

    public List<User> findByusername(String firstName);

    public List<User> findByusernameStartingWith(String prefix);

    public List<User> findByOrderByusernameAsc();
}
