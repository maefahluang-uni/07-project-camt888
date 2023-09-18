package com.example.randomnumber;

public class Random {
    public enum RandomType {
        one, two, three, four, five
    };

    public RandomType number;
    public RandomType number1;
    public RandomType number2;

    public Random() {
    }

    public Random(RandomType randomType, RandomType randomType1, RandomType randomType2) {
        this.number = randomType;
        this.number1 = randomType1;
        this.number2 = randomType2;
    }

    public RandomType getNumber() {
        return number;
    }

    public void setNumber(RandomType number) {
        this.number = number;
    }

}
