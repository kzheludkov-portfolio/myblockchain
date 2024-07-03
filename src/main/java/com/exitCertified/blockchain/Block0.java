package com.exitCertified.blockchain;

import java.beans.Transient;

public class Block0 {
    private static Integer count=0;

    private Integer blockid;
    private Long timestamp;

    private String hash;
    private String previousHash;
    private String data;

    public Block0(String previousHash, String data) {
        this.previousHash = previousHash;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.hash = this.calculateHash();
        this.blockid = Block0.count++;
    }

    public String calculateHash() {
        return StringUtil.getSHA2HexValue(String.format("%s%d%s", this.previousHash, this.timestamp, this.data));
    }

    public Integer getBlockid() {
        return blockid;
    }

    public void setBlockid(Integer blockid) {
        this.blockid = blockid;
    }

    @Transient
    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    @Transient
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Block0{" +
                "blockid=" + blockid +
                ", timestamp=" + timestamp +
                ", hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
