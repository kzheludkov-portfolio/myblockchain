package com.exitCertified.blockchain.complete;

public class TXOutput {
    private static int count = 0;

    private Integer coin;
    private String person;
    private Integer txId;

    public TXOutput(Integer coin, String person) {
        this.coin = coin;
        this.person = person;
        this.txId = TXOutput.count++;
    }

    public static Integer getCount() {
        return count;
    }

    public static void setCount(Integer count) {
        TXOutput.count = count;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getTxId() {
        return txId;
    }

    public void setTxId(Integer txId) {
        this.txId = txId;
    }

    public boolean canUnlockOutputWith(String person) {
        return true;
    }

    @Override
    public String toString() {
        return "TXOutput{" +
                "count='" + count + '\'' +
                ", coin='" + coin + '\'' +
                ", person='" + person + '\'' +
                ", txId=" + txId +
                '}';
    }
}
