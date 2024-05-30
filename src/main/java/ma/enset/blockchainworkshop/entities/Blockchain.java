package ma.enset.blockchainworkshop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Getter @Setter
public class Blockchain {
    private List<Block> chain;
    private TransactionPool transactionPool;
    private int difficulty;
    private static final int DIFFICULTY_ADJUSTMENT_INTERVAL = 10; // Adjust difficulty every 10 blocks
    private static final long TARGET_BLOCK_TIME = 60000; // Desired time to mine a block in milliseconds (e.g., 1 minute)
    private HashMap<String, Double> balances;

    public Blockchain(List<Block> blocks){
        this.chain=blocks;
    }
    public Blockchain(){

        chain=new ArrayList<>();
        this.transactionPool = new TransactionPool();
        chain.add(createGenesisBlock());
    }
    private Block createGenesisBlock() {
        return new Block(0,System.currentTimeMillis(), "0", "Genesis Block","");
    }
    public Block getLatestBlock(){
        return chain.get((chain.size()-1));
    }
    public Block getBlockByIndex(int index){
        return chain.get(index);
    }
    public void addBlock(Block block){
        List<Transaction> transactions = transactionPool.getPendingTransactions();
        block.setTransactions(transactions);
        block.setPreviousHash(getLatestBlock().getCurrentHash());
        block.setCurrentHash(block.generateHash());
        chain.add(block);
        transactions.clear();
    }
    public boolean validateChain(){
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);
            if (!currentBlock.validateHash() || !currentBlock.getPreviousHash().equals(previousBlock.getCurrentHash())) {
                return false;
            }
        }
        return true;
    }
    public void mineBlock(Block block, int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!block.getCurrentHash().substring(0, difficulty).equals(target)) {
            block.incrementNonce();
            if(block.getNonce()%20==0){
                System.out.println(block.getNonce());
            }
            block.setCurrentHash(block.generateHash());
        }
    }
    public void adjustDifficulty() {
        Block latestBlock = getLatestBlock();
        Block blockToCompare = chain.get(chain.size() - DIFFICULTY_ADJUSTMENT_INTERVAL);
        long timeExpected = DIFFICULTY_ADJUSTMENT_INTERVAL * TARGET_BLOCK_TIME;
        long timeTaken = latestBlock.getTimestamp() - blockToCompare.getTimestamp();

        if (timeTaken < timeExpected / 2) {
            difficulty++;
        } else if (timeTaken > timeExpected * 2) {
            difficulty--;
        }
    }
    public boolean validateBlock(Block currentBlock, Block previousBlock) {
        // Calculate the hash of the block
        String calculatedHash = currentBlock.generateHash();

        // Check if the calculated hash matches the stored hash
        if (!calculatedHash.equals(currentBlock.getCurrentHash())) {
            return false;
        }

        // Check if the calculated hash satisfies the difficulty requirement
        String target = new String(new char[difficulty]).replace('\0', '0');
        if (!calculatedHash.substring(0, difficulty).equals(target)) {
            return false;
        }

        // Validate other block attributes
        if (!currentBlock.getPreviousHash().equals(previousBlock.getCurrentHash())) {
            return false;
        }

        return true;
    }



}
