package com.exitCertified.blockchain.complete;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Block {

    private static int sequence = 0;

    private String currentHash;
    private String previousHash;

    private Long timestamp;
    private Integer blockHeight;
    private String data;
    private int nonce;

    private List<Transaction> transactions;

    public Block(String previousHash, String data) {
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();
        this.blockHeight = Block.sequence++;
        this.nonce = 0;
        this.currentHash = calculateHash();
        this.data = data;
        POW pow = new POW(this, 2);
        pow.mine_block();
    }

    public static Block newBlock(String previousHash, List<Transaction> transactions) {
        Block newBlock = null;

        if (Block.sequence == 0) {
            newBlock = new Block("0", "Hi im the first block");
        } else {
            newBlock = new Block(previousHash, "");
        }

        newBlock.transactions = transactions;
        Block.sequence++;
        return newBlock;
    }

    public String calculateHash() {


        try {
            this.timestamp = System.currentTimeMillis();
            return Utilities.setHash(
                    String.format("%s%d%s%d", this.previousHash, this.timestamp, this.data, this.nonce));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public void incrementNonce() {
        this.nonce++;
    }


    public String getPreviousHash() {
        return previousHash;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getData() {
        return data;
    }

    public String getCurrentHash() {
        return currentHash;
    }

    public void setCurrentHash(String currentHash) {
        this.currentHash = currentHash;
    }

    public static Integer getSequence() {
        return sequence;
    }

    public static void setSequence(Integer sequence) {
        Block.sequence = sequence;
    }

    @Override
    public String toString() {
        return "Block{" +
                "currentHash='" + currentHash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", timestamp=" + timestamp +
                ", blockHeight=" + blockHeight +
                ", data='" + data + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
