package user.service.userservice;

import java.util.Collection;
import java.util.List;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    // Create Hashmap for DB
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapping userMapping;

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable long id) {
        Optional<User> optUser = userRepository.findById(id);

        // check if id is null
        if (!optUser.isPresent()) {
            // return error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = optUser.get();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity getUserByName(@PathVariable String username) {
        List<User> users = userRepository.findByFname(username);

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // chesk if id already exist

        // add new Player to respository
        userRepository.save(user);

        // retrun success message
        return ResponseEntity.ok("User created success!");
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<String> patchUser(@PathVariable long id, @RequestBody UserDTO userDTO) {
        // find player by id
        Optional<User> optUser = userRepository.findById(id);
        // check if id exists
        if (!optUser.isPresent()) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        // get player from db
        User user = optUser.get();

        // update player by using mapper from dto
        userMapping.updateUserFromDTO(userDTO, user);

        // save to db
        userRepository.save(user);

        return ResponseEntity.ok("User updated");
    }

    @PutMapping("/users/")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        // check if id not exists
        if (!userRepository.existsById(user.getId())) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // update employee
        userRepository.save(user);

        // return success message
        return ResponseEntity.ok("User updated");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        // check if id not exists
        if (!userRepository.existsById(id)) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // delete employee
        userRepository.deleteById(id);

        // return success message
        return ResponseEntity.ok("User deleted");
    }

}
