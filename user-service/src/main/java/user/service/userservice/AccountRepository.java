package user.service.userservice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
    public List<Account> findById(String id);

    public List<Account> findAll();

}
