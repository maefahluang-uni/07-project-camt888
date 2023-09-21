package com.example.randomnumber;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import com.example.randomnumber.Random.RandomType;

public class RandomGenerator implements Runnable {
	@Autowired
	private KafkaTemplate<String, Random> kafkaTemplate;

	public RandomGenerator(KafkaTemplate<String, Random> kafkaTemplate2) {
		this.kafkaTemplate = kafkaTemplate2;
	}

	@Override
	public void run() {
		boolean interrupted = false;
		while (!interrupted) {

			// generate a transaction randomly
			int randomFirst = ThreadLocalRandom.current().nextInt(0, 5);
			int randomSec = ThreadLocalRandom.current().nextInt(0, 5);
			int randomThird = ThreadLocalRandom.current().nextInt(0, 5);
			Random random = new Random(RandomType.values()[randomFirst], RandomType.values()[randomSec],
					RandomType.values()[randomThird]);
			System.out
					.println(interrupted + " Random created " + randomFirst + " - " + randomSec + " - " + randomThird);
			// TODO: push Random to messaging broker
			kafkaTemplate.send("camt888", random);
			try {
				// wait for 3 seconds before loop to generate a new transaction
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.out.println("Thread is interrupted");
				// set to true to break out of this loop
				interrupted = true;
			}

		}

	}

}
