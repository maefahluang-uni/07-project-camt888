package user.service.userservice;

import java.time.LocalDateTime;

import org.springframework.kafka.core.KafkaTemplate;

public class AccountGenerator implements Runnable {

    private KafkaTemplate<String, LoginEvent> kafkaTemplate;
    private String username;

    public AccountGenerator(KafkaTemplate<String, LoginEvent> kafkaTemplate, String username) {
        this.kafkaTemplate = kafkaTemplate;
        this.username = username;
    }

    public KafkaTemplate<String, LoginEvent> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public void setKafkaTemplate(KafkaTemplate<String, LoginEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void run() {
        boolean interrupted = false;
        while (!interrupted) {
            LoginEvent loginEvent = new LoginEvent();
            loginEvent.setUsername(username);
            loginEvent.setTimestamp(LocalDateTime.now().toString());

            System.out.println(interrupted + " Login Event created: username - " + loginEvent.getUsername()
                    + ", time - " + loginEvent.getTimestamp());

            kafkaTemplate.send("login-events", loginEvent);

            try {
                // wait for 5 seconds before generating a new transaction
                Thread.sleep(0);
            } catch (InterruptedException e) {
                System.out.println("Thread is interrupted");
                // set to true to break out of this loop
                interrupted = true;
            }

        }

    }
}
