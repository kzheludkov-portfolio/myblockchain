package com.exitCertified.blockchain.complete;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private static int count = 0;

    private int txId;

    private List<TXInput> inputTxList;

    private List<TXOutput> outputTxList;

    public Transaction(List<TXInput> inputTxList, List<TXOutput> outputTxList) {
        this.inputTxList = inputTxList;
        this.outputTxList = outputTxList;
        this.txId = Transaction.count++;
    }

    public static Transaction newCoinbaseTX(String person) {
        TXInput txInput = new TXInput(-1, person);
        TXOutput txOutput = new TXOutput(100, person);

        List<TXInput> inputTxList = new ArrayList<>();
        List<TXOutput> outputTxList = new ArrayList<>();

        inputTxList.add(txInput);
        outputTxList.add(txOutput);

        return new Transaction(inputTxList, outputTxList);
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Transaction.count = count;
    }

    public List<TXInput> getInputTxList() {
        return inputTxList;
    }

    public void setInputTxList(List<TXInput> inputTxList) {
        this.inputTxList = inputTxList;
    }

    public List<TXOutput> getOutputTxList() {
        return outputTxList;
    }

    public void setOutputTxList(List<TXOutput> outputTxList) {
        this.outputTxList = outputTxList;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "txId=" + txId +
                ", inputTxList=" + inputTxList +
                ", outputTxList=" + outputTxList +
                '}';
    }
}
