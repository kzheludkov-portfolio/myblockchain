package com.exitCertified.blockchain;

import java.util.ArrayList;
import java.util.List;

public class Blockchain0 {

    private List<Block0> myBlockchain;

    public Blockchain0(int block_size) {
        this.myBlockchain = new ArrayList<>(block_size);
    }

    public List<Block0> getMyBlockchain() {
        return myBlockchain;
    }

    public boolean isChainValid() throws RuntimeException {

        if (myBlockchain.size() < 1) {
            return false;
        } else if (myBlockchain.size() == 1) {
            return true;
        }

        String szHashTarget = "0";

        for (int ix = 1; ix < myBlockchain.size(); ix++) {
            Block0 currentBlock0 = myBlockchain.get(ix);
            Block0 previousBlock0 = myBlockchain.get(ix - 1);

            if (!(currentBlock0.getHash().equals(currentBlock0.calculateHash())))
                return false;

            if (!(previousBlock0.getHash().equals(currentBlock0.calculateHash())))
                return false;

            if (!(currentBlock0.getHash().substring(0, 1).equals(szHashTarget)))
                return false;
        }

        return true;
    }

    public static void main(String... args) {
        int block_size = 2;
        Blockchain0 bc = new Blockchain0(block_size);
        Block0 prevBlock0 = new Block0("0", "Hi im the first block");
        bc.myBlockchain.add(prevBlock0);
        for (int ix = 0; ix < block_size - 1; ix++) {
            prevBlock0 = new Block0(prevBlock0.getHash(), String.format("Iterator block:%s", ix));
            bc.myBlockchain.add(prevBlock0);
        }

        System.out.println(bc.myBlockchain);
        System.out.println(bc.isChainValid());
    }
}
