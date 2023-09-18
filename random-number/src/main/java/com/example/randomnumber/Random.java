package com.example.randomnumber;

public class Random {
    public enum RandomType {one, two,three,four,five};
    public RandomType number;

    
    public Random() {
    }

    public Random(RandomType randomType) {
        this.number = randomType;
    }

    public RandomType getNumber() {
        return number;
    }

    public void setNumber(RandomType number) {
        this.number = number;
    }
    
  

 
}
