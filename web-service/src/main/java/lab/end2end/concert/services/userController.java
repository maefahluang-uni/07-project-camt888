package lab.end2end.concert.services;

import java.util.Collection;
import java.util.Optional;
import java.util.List;

import lab.end2end.concert.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lab.end2end.concert.services.userRespository;

@RestController
public class userController {
    // Create Hashmap for DB
    @Autowired
    private userRespository userRespository;

    @Autowired
    private userMapper userMapper;

    // Select all Employee
    @GetMapping("/users")
    public Collection<User> getAllEmployees() {
        return userRespository.findByOrderByusernameAsc();
    }

    // Select Employee by ID
    @GetMapping("/users/{id}")
    public ResponseEntity getEmployeeById(@PathVariable long id) {
        Optional<User> optUsers = userRespository.findById(id);

        // check if id is null
        if (!optUsers.isPresent()) {
            // return error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = optUsers.get();
        return ResponseEntity.ok(user);
    }

    // Select Employee by First name
    @GetMapping("/users/username/{name}")
    public ResponseEntity getEmployeeByName(@PathVariable String name) {
        List<User> users = userRespository.findByusername(name);

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(users);
    }

    // Create New Employee
    @PostMapping("/users")
    public ResponseEntity<String> createEmployee(@RequestBody User user) {
        // chesk if id already exist

        // add new employee to respository
        userRespository.save(user);

        // retrun success message
        return ResponseEntity.ok("User created success!");
    }

    // partial update employee with some fields using patch
    @PatchMapping("/users/{id}")
    public ResponseEntity<String> patchEmployee(@PathVariable long id, @RequestBody userDTO userDTO) {
        // find employee by id
        Optional<User> optUser = userRespository.findById(id);
        // check if id exists
        if (!optUser.isPresent()) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        // get employee from db
        User user = optUser.get();

        // update employee by using mapper from dto
        userMapper.updateEmployeeFromDto(userDTO, user);

        // save to db
        userRespository.save(user);

        return ResponseEntity.ok("User updated");
    }

    // update employee
    @PutMapping("/users/")
    public ResponseEntity<String> updateEmployee(@RequestBody User user) {
        // check if id not exists
        if (!userRespository.existsById(user.getId)) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // update employee
        userRespository.save(user);

        // return success message
        return ResponseEntity.ok("User updated");
    }

    // Delete Employee
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        // check if id not exists
        if (!userRespository.existsById(id)) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // delete employee
        userRespository.deleteById(id);

        // return success message
        return ResponseEntity.ok("User deleted");
    }

}
