package models;

import java.util.Date;

public class Accounts {
    private Integer id;
    private Integer clientIdFk;
    private Double balance;
    private Date accountDateCreation;

    public Accounts(){
        this.balance = 0.00;
    }

    public Accounts(Integer id, Integer clientIdFk, Double balance, Date accountDateCreation) {
        this.id = id;
        this.clientIdFk = clientIdFk;
        this.balance = balance;
        this.accountDateCreation = accountDateCreation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientIdFk() {
        return clientIdFk;
    }

    public void setClientIdFk(Integer clientIdFk) {
        this.clientIdFk = clientIdFk;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getAccountDateCreation() {
        return accountDateCreation;
    }

    public void setAccountDateCreation(Date accountDateCreation) {
        this.accountDateCreation = accountDateCreation;
    }

    @Override
    public String toString() {
        return "Accounts{" +
                "id=" + id +
                ", clientIdFk=" + clientIdFk +
                ", balance=" + balance +
                ", accountDateCreation=" + accountDateCreation +
                '}';
    }
}
