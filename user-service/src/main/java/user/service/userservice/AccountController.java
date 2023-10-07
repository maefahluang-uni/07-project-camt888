package user.service.userservice;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import javax.security.auth.login.AccountNotFoundException;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private KafkaTemplate<String, LoginEvent> kafkaTemplate;

    // Select all Employee
    @GetMapping("/accounts")
    public Collection<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Select Employee by ID
    @GetMapping("/accounts/{id}")
    public ResponseEntity getAccountById(@PathVariable long id) {
        Optional<Account> optAcc = accountRepository.findById(id);

        // check if id is null
        if (!optAcc.isPresent()) {
            // return error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        Account acc = optAcc.get();
        return ResponseEntity.ok(acc);
    }

    // Create New Employee
    @PostMapping("/accounts")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        // chesk if id already exist

        // add new employee to respository
        accountRepository.save(account);

        // retrun success message
        return ResponseEntity.ok("Account created success!");
    }

    // partial update employee with some fields using patch
    @PatchMapping("/accounts/{id}")
    public ResponseEntity<String> patchAccount(@PathVariable long id, @RequestBody AccountDTO accountDto) {
        // find employee by id
        Optional<Account> optAcc = accountRepository.findById(id);
        // check if id exists
        if (!optAcc.isPresent()) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        // get employee from db
        Account acc = optAcc.get();

        // update employee by using mapper from dto
        accountMapper.updateAccountFromDto(accountDto, acc);

        // save to db
        accountRepository.save(acc);

        return ResponseEntity.ok("Account updated");
    }

    // update employee
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
        return ResponseEntity.ok("Account updated");
    }

    // Delete Employee
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
        return ResponseEntity.ok("Account deleted");
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

        // Create and configure the AccountGenerator
        AccountGenerator accGen = new AccountGenerator(kafkaTemplate, username);
        // accGen.setKafkaTemplate(kafkaTemplate);

        // Start AccountGenerator as a separate thread
        Thread accountGeneratorThread = new Thread(accGen);
        accountGeneratorThread.start();
        accountGeneratorThread.interrupt();

        // Authentication successful
        return ResponseEntity.ok("Login to: " + username + " successful!");
    }

    @PostMapping("/addFunds")
    public ResponseEntity<?> addFunds(@RequestParam String username, @RequestParam long amount) {
        // Check if a user with the provided username exists in the database
        Account userAccount = accountRepository.findByUsername(username);

        if (userAccount == null) {
            // Return an error response as JSON
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Update the user's balance in the database
        userAccount.setBalance(userAccount.getBalance() + amount);
        accountRepository.save(userAccount);

        // Return a success response as JSON
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Funds added successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bonuses")
    public ResponseEntity<?> bonus(@RequestParam String username, @RequestParam long amount) {
        // Check if a user with the provided username exists in the database
        Account userAccount = accountRepository.findByUsername(username);

        if (userAccount == null) {
            // Return an error response as JSON
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Update the user's balance in the database
        userAccount.setBalance(amount);
        accountRepository.save(userAccount);

        // Return a success response as JSON
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Funds added successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdrawFunds")
    public ResponseEntity<?> withdrawFunds(@RequestParam String username, @RequestParam long amount) {
        // Check if a user with the provided username exists in the database
        Account userAccount = accountRepository.findByUsername(username);

        if (userAccount == null) {
            // Return an error response as JSON
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if the user has sufficient balance for withdrawal
        if (userAccount.getBalance() >= amount) {
            // Update the user's balance in the database
            userAccount.setBalance(userAccount.getBalance() - amount);
            accountRepository.save(userAccount);

            // Return a success response as JSON
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Funds withdrawn successfully");
            return ResponseEntity.ok(response);
        } else {
            // Return an error response as JSON for insufficient balance
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Insufficient balance for withdrawal");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
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

}
