package random.service.randomnumber;

import java.util.List;
import java.util.Optional;

import lab.end2end.concert.domain.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RandomController {

    private static Logger LOGGER = LoggerFactory.getLogger(RandomController.class);

    // TODO: add repository
    @Autowired
    private RandomRepository randomRepository;

    // TODO: add @GET and @Path
    @GetMapping("/camt888s/{id}")
    public ResponseEntity<Random> retrieveRandom(@PathVariable long id) { // TODO: add @PathVariable for id
        Optional<Random> optcon = randomRepository.findById(id);
        if (!optcon.isPresent()) {
            return new ResponseEntity<Random>(HttpStatus.NOT_FOUND);
        }
        // TODO: find concert by ID suing em.find(...
        Random random = optcon.get();
        // TODO: Handle the case when no entity is found

        return new ResponseEntity<>(random, HttpStatus.OK);

    }

    // TODO: add @GET and @Path
    @GetMapping("/camt888s")
    public ResponseEntity<List<Random>> retrieveAllRandom() {
        // TODO: get all concert
        List<Random> randoms = randomRepository.findAll();

        return new ResponseEntity<>(randoms, HttpStatus.OK);

    }

    // TODO: add proper annotation Post verb
    @PostMapping("/camt888s")
    public ResponseEntity<Random> createRandom(@RequestBody Random random) {
        // Save the concert to the database using the repository
        random = randomRepository.save(random);

        // Return a ResponseEntity with the created concert in the response body
        return new ResponseEntity<>(random, HttpStatus.CREATED);
    }

   
    @PutMapping("/camt888s")
    public ResponseEntity<String> updateRandom(@RequestBody Random random) { 
        if (!randomRepository.existsById(random.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        randomRepository.save(random);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    @DeleteMapping("/camt888s/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) { 
        if (!randomRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        randomRepository.deleteById(id);
        

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

   
    @DeleteMapping("/camt888s")
    public ResponseEntity<String> deleteAllRandom() {

        randomRepository.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
