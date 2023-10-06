package com.example.demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserHistoryController {

    @Autowired
    private UserHistoryRepository HistoryRepository;

    // @Autowired
    // private UserHistoryMapper accountMapper;

    // Select all Employee
    @GetMapping("/History")
    public Collection<History> getAllAccounts() {
        return HistoryRepository.findAll();
    }

    // Select Employee by ID
    @GetMapping("/History/{id}")
    public ResponseEntity getAccountById(@PathVariable Long id) {
        Optional<History> optAcc = HistoryRepository.findById(id);

        // check if id is null
        if (!optAcc.isPresent()) {
            // return error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
        History acc = optAcc.get();
        return ResponseEntity.ok(acc);
    }

    // Create New Employee
    @PostMapping("/History")
    public ResponseEntity<String> createAccount(@RequestBody History History) {
        // chesk if id already exist

        // add new employee to respository
        HistoryRepository.save(History);

        // retrun success message
        return ResponseEntity.ok("Account created success!");
    }

    // update employee
    @PutMapping("/History/")
    public ResponseEntity<String> updateAccount(@RequestBody History account) {
        // check if id not exists
        if (!HistoryRepository.existsById(account.getId())) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        // update employee
        HistoryRepository.save(account);

        // return success message
        return ResponseEntity.ok("Account updated");
    }

    // Delete Employee
    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable long id) {
        // check if id not exists
        if (!HistoryRepository.existsById(id)) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }

        // delete employee
        HistoryRepository.deleteById(id);

        // return success message
        return ResponseEntity.ok("Account deleted");
    }
    @PostMapping("/addhisFunds")
    public ResponseEntity<?> addFunds(@RequestParam String username, @RequestParam long amount) {
        // Check if a user with the provided username exists in the database
        History userAccount = HistoryRepository.findByUsername(username);

        if (userAccount == null) {
            // Return an error response as JSON
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Update the user's balance in the database
        userAccount.setDeposit(userAccount.getDeposit() + amount);
        HistoryRepository.save(userAccount);

        // Return a success response as JSON
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Funds added successfully");
        return ResponseEntity.ok(response);
    }
    @PostMapping("/withdrawhisFunds")
    public ResponseEntity<?> withdrawFunds(@RequestParam String username, @RequestParam long amount) {
        // Check if a user with the provided username exists in the database
        History userAccount = HistoryRepository.findByUsername(username);

        if (userAccount == null) {
            // Return an error response as JSON
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if the user has sufficient balance for withdrawal
        if (userAccount.getDeposit() >= amount) {
            // Update the user's balance in the database
            userAccount.setDeposit(userAccount.getDeposit() - amount);
            HistoryRepository.save(userAccount);

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
        Optional<History> optAcc = HistoryRepository.findById(userId);

        if (!optAcc.isPresent()) {
            // Return an error response if the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Retrieve the user's data and create a response
        History userAccount = optAcc.get();
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", userAccount.getUsername());
        userData.put("balance", userAccount.getDeposit());

        // Return the user data as a JSON response
        return ResponseEntity.ok(userData);
    }

    @GetMapping("/getUserDataByUsername")
    public ResponseEntity<?> getUserDataByUsername(@RequestParam String username) {
        // Check if a user with the provided username exists in the database
        History userAccount = HistoryRepository.findByUsername(username);

        if (userAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
        }

        // Map the user data to a custom structure
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", userAccount.getUsername());
        userData.put("balance", userAccount.getDeposit()); // Assuming you want to map the balance to "data"

        // Return the user data as a JSON response
        return ResponseEntity.ok(userData);
    }






}
