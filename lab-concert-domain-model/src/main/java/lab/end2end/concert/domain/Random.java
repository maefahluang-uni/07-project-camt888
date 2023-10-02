package lab.end2end.concert.domain;

import javax.persistence.*;

@Entity
public class Random {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int number1;
    private int number2;
    private int number3;

    public Random() {
        // Default constructor required by JPA
    }

    public Random(int number1, int number2, int number3) {
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
    }

    // Getters and setters for id, number1, number2, and number3
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getNumber3() {
        return number3;
    }

    public void setNumber3(int number3) {
        this.number3 = number3;
    }
}

