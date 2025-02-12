package ma.enset.blockchainworkshop.entities;

import lombok.Getter;
import lombok.Setter;
import ma.enset.blockchainworkshop.tools.HashUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String currentHash;
    private String data;
    private List<Transaction> transactions;
    private int nonce;

    public Block(int index, long timestamp, String previousHash, String currentHash, String data) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.currentHash = currentHash;
        this.data = data;
        this.transactions = new ArrayList<>();
        this.nonce=0;
    }

    public void incrementNonce() {
        nonce++;
    }

    public String generateHash() {
        String input = this.index + "" + this.timestamp + this.previousHash + this.nonce + this.data;
        return HashUtil.applySha256(input);
    }

    public boolean validateHash(){
        String calculatedHash= generateHash();
        return calculatedHash.equals(this.currentHash);
    }
}
