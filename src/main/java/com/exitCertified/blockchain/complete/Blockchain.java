package com.exitCertified.blockchain.complete;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class Blockchain {

    private List<Block> blocks;

    private static final String LEDGER_NAME = "ECC_ledger.json";

    public Blockchain() {
    }

    public Blockchain(List<Block> blocks) {
        // The first constructor has one parameter – a list of blocks.
        // Initialize Blockchain list with this parameter.
        this.blocks = blocks;
    }

    public Blockchain(List<Transaction> transactions, boolean dummy) {
        // The second constructor has one parameter, which is list of transactions.
        // Create the genesis block with the transactions.
        // Call the method CreateGenesisBlock.
        this.blocks = new ArrayList<>();
        createGenesisBlock(transactions);
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void createGenesisBlock(List<Transaction> transactions) {
        // Create the first block of the blockchain.
        // This method has a single parameter, which is an array of transactions.
        // 1. Create an array of transactions initialized with a single transaction,
        // which is a coinbase transaction.
        // 2. Create a new block.
        // 3. Add the block to the blockchain.
        addBlock(transactions);
    }

    public void addBlock(List<Transaction> transactions) {
        // Adds a new block to the blockchain.
        // The only parameter is an array of transactions.
        // Returns nothing.

        // Create a new block using Block.NewBlock with the transactions.
        // Add the block to the list of blocks. Save the block to a Json file:
        // BlockChain.SaveChain.
        String prevHash = "";
        if (blocks.size() > 0) {
            prevHash = blocks.get(blocks.size() - 1).getCurrentHash();
        }
        Block newBlock = Block.newBlock(prevHash, transactions);
        blocks.add(newBlock);
        try {
            this.saveChain();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean containsInInputTx(TXOutput txOutput) {
        // his method finds unused transactions. The only parameter is a TXOutput.
        // Search for the output transaction in the list of input transactions.
        //• Iterate the blocks of the blockchain.
        //• Iterate the transactions of the block.
        //• If the output transaction is assigned to a input transaction, return true.
        // Otherwise, return false.
        for (Block block : blocks) {
            for (Transaction transaction : block.getTransactions()) {
                for (TXInput txInput : transaction.getInputTxList()) {
                    if (txOutput.getTxId() == txInput.getTxId()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public List<TXOutput> findUTXO(String person) {
        //This method is finds unspent transactions for a particular person.
        // The method has a single parameter, which is the person.
        // Returns the UTXO for that person.

        //1. Create an empty list of TXOutput transactions. This is the list of UTXO.
        //2. Iterate the blocks of the blockchain.
        //3. Iterate the transactions of each block.
        //4. Iterate the array of TXOutput for each transaction.
        //5. If ContainsInInputTx returns false, add to the UTXO list.
        List<TXOutput> utxoOutput = new ArrayList<>();
        for (Block block : blocks) {
            for (Transaction transaction : block.getTransactions()) {
                for (TXOutput txOutput : transaction.getOutputTxList()) {
                    if (!containsInInputTx(txOutput) && person.equals(txOutput.getPerson())) {
                        utxoOutput.add(txOutput);
                    }
                }
            }
        }

        return utxoOutput;
    }

    public Transaction newOutputTransaction(String fromPerson, String toPerson, Integer value) {
        //This function sends money from one person to another.
        // You are spending UTXO. There are three parameters: who is sending the money,
        // who is receiving the money, and the amount being sent.
        //• Create an empty list of TXInputs and TXOutputs.
        //• Create an empty array of TXOutput, which will hold the UTXO. Initialize with FindUTXO.
        //• Iterate the list of UTXO transactions. Keep a running total of the transaction value.
        //• Create a new TXInput transaction for each UTXO transaction. Add to the TXInputs list.
        // Break when the total is greater than the amount being spent.
        //• Create a new TXOutput transaction for the total amount.
        //• If there is change, create a new TXOutput transaction for that amount. Send to yourself.
        //• Create a new Transaction from the list of TXInputs and TXOutputs. Set the Transaction id.
        //• Return the Transaction.
        List<TXInput> txInputsList = new ArrayList<>();
        List<TXOutput> txOutputsList = new ArrayList<>();

        //find UTXO transactions
        List<TXOutput> txOutputsUTXOList = findUTXO(fromPerson);
        int total = 0;
        for (TXOutput txOutput : txOutputsUTXOList) {
            total += txOutput.getCoin();

            if (value > total)
                break;

            TXInput txInput = new TXInput(txOutput.getTxId(), fromPerson);
            txInputsList.add(txInput);
        }

        if (value > total) {
            throw new RuntimeException("Balance limit has been exceeded");
        }

        TXOutput txOutput = new TXOutput(value, toPerson);
        txOutputsList.add(txOutput);

        int change = total - value;
        txOutput = new TXOutput(change, fromPerson);
        txOutputsList.add(txOutput);

        return new Transaction(txInputsList, txOutputsList);
    }

    public Integer getBalance(String person) {
        // This method returns the balance for a person.
        // There is one parameter, which identifies the person.
        // Iterate and total the UTXO. Return the total.
        List<TXOutput> txOutputs = findUTXO(person);
        int total = 0;
        for (TXOutput txOutput : txOutputs) {
            if (person.equals(txOutput.getPerson()))
                total += txOutput.getCoin();
        }
        return total;
    }

    public void displayChain() {
        try {
            System.out.println(Blockchain.readChain());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveChain() throws IOException {
        try (Writer writer = new FileWriter(Blockchain.LEDGER_NAME)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(blocks, writer);
        }
    }

    public static Blockchain readChain() throws IOException {
        Gson gson = new Gson();
        ArrayList<Block> blocks = null;

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(Blockchain.LEDGER_NAME));
            java.lang.reflect.Type blockType =
                    new TypeToken<ArrayList<Block>>() {
                    }.getType();
            blocks = gson.fromJson(br, blockType);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        Blockchain blockChain = new Blockchain(blocks);
        updateCounters(blockChain);

        return blockChain;
    }

    public static void updateCounters(Blockchain blockChain) {
        int blockCount = 0;
        int transactionsCount = 0;
        int txOutputCount = 0;
        for (Block block : blockChain.blocks) {
            blockCount++;
            for (Transaction transaction : block.getTransactions()) {
                transactionsCount++;
                for (TXOutput txOutput : transaction.getOutputTxList()) {
                    txOutputCount++;
                }
            }
        }
        Block.setSequence(blockCount+1);
        Transaction.setCount(transactionsCount+1);
        TXOutput.setCount(txOutputCount+1);
    }

    public static void main(String... args) {
        List<Block> blocks = new ArrayList<>();

        Block block0 = new Block("0", "Hi im the first block");
        blocks.add(block0);
        Blockchain bc = new Blockchain(blocks);

        try {
            bc.saveChain();
            System.out.println(bc);
            Blockchain bc_new = Blockchain.readChain();
            System.out.println(bc_new);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "Blockchain{" +
                "blocks=" + blocks +
                '}';
    }
}
