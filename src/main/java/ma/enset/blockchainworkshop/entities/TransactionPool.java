package ma.enset.blockchainworkshop.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter @Setter
public class TransactionPool {
    private List<Transaction> PendingTransactions;

    public TransactionPool(){
        this.PendingTransactions=new ArrayList<>();
    }

    public void addTransaction(Transaction T){
        this.PendingTransactions.add(T);
    }
    public List<Transaction> getPendingTransactions(){
        return this.PendingTransactions;
    }
    public void removeTransaction(Transaction t){
        this.PendingTransactions.remove(t);
    }

}
