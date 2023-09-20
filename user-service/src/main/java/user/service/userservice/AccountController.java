package user.service.userservice;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    // Select all Player
    @GetMapping("/accounts")
    public Collection<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Select Player by ID
    @GetMapping("/accounts/{id}")
    public ResponseEntity getPlayerById(@PathVariable long id) {
        Optional<Account> optAcc = accountRepository.findById(id);

        // check if id is null
        if (!optAcc.isPresent()) {
            // return error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        Account account = optAcc.get();
        return ResponseEntity.ok(account);
    }

    // Create New Player
    @PostMapping("/accounts")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        // chesk if id already exist

        // add new Player to respository
        accountRepository.save(account);

        // retrun success message
        return ResponseEntity.ok("Account created success!");
    }

    @PutMapping("/accounts/")
    public ResponseEntity<String> updateAccount(@RequestBody Account account) {
        // check if id not exists
        if (!accountRepository.existsById(account.getId())) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        // update employee
        accountRepository.save(account);

        // return success message
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable long id) {
        // check if id not exists
        if (!accountRepository.existsById(id)) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        // delete employee
        accountRepository.deleteById(id);

        // return success message
        return ResponseEntity.ok("User deleted");
    }

}
