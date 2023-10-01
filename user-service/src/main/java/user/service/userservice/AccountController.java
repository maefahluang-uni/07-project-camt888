package user.service.userservice;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
        // Check if the username already exists
        if (accountRepository.existsByUsername(account.getUsername())) {
            // Return an error response if the username is already taken
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account already exists");
        }

        // If the username is not taken, proceed with account creation
        accountRepository.save(account);

        // Return a success response
        return ResponseEntity.ok("Account created successfully");
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

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // Check if a user with the provided username exists
        if (!accountRepository.existsByUsername(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
        }

        // Retrieve the user's account from the database
        Account userAccount = accountRepository.findByUsername(username);

        long balance = accountRepository.findByUsername(username).getBalance();

        Map<String, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("balance", balance);

        // Check if the provided password matches the stored password
        if (!userAccount.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }

        // Authentication successful
        return ResponseEntity.ok("Login to: " + username + " successful!");
    }

    @GetMapping("/getUserData")
    public ResponseEntity<?> getUserData(@RequestParam Long userId) {
        // Check if a user with the provided userId exists in the database
        Optional<Account> optAcc = accountRepository.findById(userId);

        if (!optAcc.isPresent()) {
            // Return an error response if the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Retrieve the user's data and create a response
        Account userAccount = optAcc.get();
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", userAccount.getUsername());
        userData.put("balance", userAccount.getBalance());

        // Return the user data as a JSON response
        return ResponseEntity.ok(userData);
    }

    @GetMapping("/getUserDataByUsername")
    public ResponseEntity<?> getUserDataByUsername(@RequestParam String username) {
        // Check if a user with the provided username exists in the database
        Account userAccount = accountRepository.findByUsername(username);

        if (userAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
        }

        // Map the user data to a custom structure
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", userAccount.getUsername());
        userData.put("balance", userAccount.getBalance()); // Assuming you want to map the balance to "data"

        // Return the user data as a JSON response
        return ResponseEntity.ok(userData);
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
