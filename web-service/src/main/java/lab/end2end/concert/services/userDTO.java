package lab.end2end.concert.services;

public class userDTO {
    private Long id;
    private String username;
    private long balance;

    public userDTO() {
    }

    public userDTO(Long id, String username, long balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
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

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

}
