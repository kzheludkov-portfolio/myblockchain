package com.exitCertified.blockchain;

import java.security.MessageDigest;
import java.util.*;

public class MerkleTree {

    private List<String> transactions;
    private String root;
    private int depth;

    public MerkleTree(List<String> transactions) {
        this.transactions = transactions;
        this.root = "";
        depth = 0;
    }

    public String getRoot() {
        return root;
    }

    public int getDepth() {
        return depth;
    }

    public void merkle_tree() {

        List<String> tempTxList = new ArrayList<String>();
        for (String trans : transactions) {
            tempTxList.add(trans);
        }

        List<String> newTxList = getNextMerkleRow(tempTxList);
        depth = 1;

        while (newTxList.size() > 1) {
            newTxList = getNextMerkleRow(newTxList);
        }

        root = newTxList.get(0);
    }

    private List<String> getNextMerkleRow(List<String> transactions) {
        List<String> newRow = new ArrayList();
        for (int ix = 0; ix < transactions.size() - 1; ix += 2) {
            String left = transactions.get(ix);
            String right = transactions.get(ix + 1);
            String parent = getSHA2HexValue(left + right);
            newRow.add(parent);
            depth++;
        }

        if (transactions.size() % 2 != 0) {
            String left = transactions.get(transactions.size() - 1);
            String parent = getSHA2HexValue(left + "");
            newRow.add(parent);
        }

        return newRow;
    }


    private String getSHA2HexValue(String str) {
        byte[] cipher_byte;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            cipher_byte = md.digest();
            StringBuilder sb = new StringBuilder(2 * cipher_byte.length);
            for (byte b : cipher_byte) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String... args) {
        List<String> transactions = new ArrayList();

        transactions.add("a");
        transactions.add("b");
        transactions.add("c");
        transactions.add("d");
        transactions.add("e");
        transactions.add("f");
        transactions.add("g");

        MerkleTree merkle_tree = new MerkleTree(transactions);
        merkle_tree.merkle_tree();

        System.out.println(String.format("Root: %s, depth: %d", merkle_tree.getRoot(), merkle_tree.getDepth()));
    }
}
