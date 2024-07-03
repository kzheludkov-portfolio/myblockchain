package com.exitCertified.blockchain.complete;

public class POW {

    private Block block;
    private int difficult;
    private String target2Mine;
    private int maxNonce;

    public POW(Block block, int difficult) {
        this.block = block;
        this.difficult = difficult;
        this.target2Mine = new String(
                new char[this.difficult]).replace('\0', '0');
        this.maxNonce = 1000000000;
    }

    public Block getBlock() {
        return block;
    }

    public int getDifficult() {
        return difficult;
    }

    public String prepareData() {

        return String.format("%s%d", this.block.getData(), block.getNonce());
    }

    public void mine_block() {
        String hash = block.calculateHash();
        while (!hash.substring(0, difficult).equals(this.target2Mine)) {
            block.incrementNonce();
            if (block.getNonce() > maxNonce) {
                break;
            }
            hash = block.calculateHash();
        }
        this.block.setCurrentHash(hash);
    }

    @Override
    public String toString() {
        return "POW{" +
                "block=" + block +
                ", difficuties=" + difficult +
                '}';
    }

    public static void main(String... args) {
    }
}
