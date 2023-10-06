package com.example.demo;

import javax.persistence.*;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
    
    private String username;
    private long Deposit ;
    private long Withdraw;

    public History() {
    }

    public History(Long id, String username, long Deposit, long Withdraw) {
        this.id = id;
        this.username = username;
        this.Deposit = Deposit;
        this.Withdraw = Withdraw;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
   public long getDeposit() {
        return Deposit;
    }

    public void setDeposit(long deposit) {
        Deposit = deposit;
    }

    public long getWithdraw() {
        return Withdraw;
    }

    public void setWithdraw(long withdraw) {
        Withdraw = withdraw;
    }
}
