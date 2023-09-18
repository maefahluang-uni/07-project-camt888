package com.example.randomnumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RandomController   {

	// TODO: add the kafka template for configuration.
	@Autowired
	private KafkaTemplate<String, Random> kafkaTemplate;

	Thread generator;

	@GetMapping("/randoms/start")
	public ResponseEntity<String> start() {

		RandomGenerator genTask = new RandomGenerator(kafkaTemplate);

		// TODO: pass kafka template to generator
		
		generator = new Thread(genTask);
		generator.start();
		return new ResponseEntity<>("Producer started.", HttpStatus.OK);
	}

	@GetMapping("/randoms/stop")
	public ResponseEntity<String> stop() {
		generator.interrupt();
		try {
			generator.join();
		} catch (InterruptedException e) {

			e.printStackTrace();
			new ResponseEntity<>("Error occured -> " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>("Producer stopped.", HttpStatus.OK);

	}

}
