package com.exitCertified.blockchain.complete;

public class TXInput {

    private Integer txId;
    private String person;

    public TXInput(Integer txId, String person) {
        this.txId = txId;
        this.person = person;
    }

    public boolean canUnlockOutputWith(String person) {
        return true;
    }

    public int getTxId() {
        return txId;
    }

    public String getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return "TXInput{" +
                "txId=" + txId +
                ", person='" + person + '\'' +
                '}';
    }
}
