package lab.end2end.concert.services;

import lab.end2end.concert.domain.Concert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ConcertController {

    private static Logger LOGGER = LoggerFactory.getLogger(ConcertController.class);

    @Autowired
    private ConcertRepository concertRepository;

    @GetMapping("/concerts/{id}")
    public ResponseEntity<Concert> retrieveConcert(@PathVariable Long id) {
        Optional<Concert> concertOptional = concertRepository.findById(id);

        if (concertOptional.isPresent()) {
            Concert concert = concertOptional.get();
            return new ResponseEntity<>(concert, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/concerts")
    public ResponseEntity<List<Concert>> retrieveAllConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        return new ResponseEntity<>(concerts, HttpStatus.OK);
    }

    @PostMapping("/concerts")
    public ResponseEntity<String> createConcert(@RequestBody Concert concert) {
        concertRepository.save(concert);
        return new ResponseEntity<>("Concert created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/concerts")
    public ResponseEntity<Void> updateConcert(@RequestBody Concert updatedConcert) {
        Optional<Concert> existingConcertOptional = concertRepository.findById(updatedConcert.getId());

        if (existingConcertOptional.isPresent()) {
            Concert existingConcert = existingConcertOptional.get();
            existingConcert.setTitle(updatedConcert.getTitle());
            existingConcert.setDate(updatedConcert.getDate());
            existingConcert.setPerformer(updatedConcert.getPerformer());

            concertRepository.save(existingConcert);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 Not Found
        }
    }

    @DeleteMapping("/concerts/{id}")
    public ResponseEntity<String> deleteConcert(@PathVariable Long id) {
        Optional<Concert> concertOptional = concertRepository.findById(id);

        if (concertOptional.isPresent()) {
            concertRepository.deleteById(id);
            return new ResponseEntity<>("Concert deleted successfully", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Concert not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/concerts")
    public ResponseEntity<String> deleteAllConcerts() {
        concertRepository.deleteAll();
        return new ResponseEntity<>("All concerts deleted successfully", HttpStatus.NO_CONTENT);
    }
}
