package ma.enset.blockchainworkshop.entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Transaction {
    private String sender;
    private String recipient;
    private double amount;
    private String signature;


    public Transaction(){

    }

    public Transaction(String sender, String recipient, double amount, String signature) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.signature = signature;
    }

}
