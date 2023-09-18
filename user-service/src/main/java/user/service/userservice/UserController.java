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

    // Select all Player
    @GetMapping("/users")
    public Collection<User> getAllPlayers() {
        return userRepository.findAll();
    }

    // Select Player by ID
    @GetMapping("/users/{id}")
    public ResponseEntity getPlayerById(@PathVariable long id) {
        Optional<User> optUser = userRepository.findById(id);

        // check if id is null
        if (!optUser.isPresent()) {
            // return error 404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        User user = optUser.get();
        return ResponseEntity.ok(user);
    }

    // Select Player by User name
    @GetMapping("/users/username/{username}")
    public ResponseEntity getPlayerByName(@PathVariable String username) {
        List<User> users = userRepository.findByUsername(username);

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(users);
    }

    // Create New Player
    @PostMapping("/users")
    public ResponseEntity<String> createPlayer(@RequestBody User user) {
        // chesk if id already exist

        // add new Player to respository
        userRepository.save(user);

        // retrun success message
        return ResponseEntity.ok("User created success!");
    }

    // partial update player with some fields using patch
    // @PatchMapping("/users/{id}")
    // public ResponseEntity<String> patchPlayer(@PathVariable long id, @RequestBody
    // PlayerDT0 playerDTO) {
    // // find player by id
    // Optional<Player> optPlayer = playerRepository.findById(id);
    // // check if id exists
    // if (!optPlayer.isPresent()) {
    // // return error message
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player not found");
    // }
    // // get player from db
    // Player player = optPlayer.get();

    // // update player by using mapper from dto
    // playerMapper.updatePlayerFromDto(playerDTO, player);

    // // save to db
    // playerRepository.save(player);

    // return ResponseEntity.ok("Player updated");
    // }

    // update employee
    @PutMapping("/users/")
    public ResponseEntity<String> updatePlayer(@RequestBody User user) {
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

    // Delete Employee
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deletePlayer(@PathVariable long id) {
        // check if id not exists
        if (!userRepository.existsById(id)) {
            // return error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // delete employee
        userRepository.deleteById(id);

        // return success message
        return ResponseEntity.ok("Player deleted");
    }

}