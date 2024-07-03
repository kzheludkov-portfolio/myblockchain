package com.exitCertified.blockchain;

import com.exitCertified.blockchain.complete.Blockchain;
import com.exitCertified.blockchain.complete.Transaction;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlockchainApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void blockchainTest() {
        createChain();
        readBlockChain();
    }

    private void createChain() {
        Transaction transaction1 = Transaction.newCoinbaseTX("Donis");

        Blockchain blockChain = new Blockchain(Collections.singletonList(transaction1), false);
        System.out.println(String.format("Block0: %s", blockChain.getBlocks()));

        Transaction transaction2 = blockChain.newOutputTransaction(
                "Donis", "Bob", 50);
        blockChain.addBlock(Collections.singletonList(transaction2));

        Transaction transaction3 = blockChain.newOutputTransaction(
                "Donis", "Fred", 25);
        blockChain.addBlock(Collections.singletonList(transaction3));

        Transaction transaction4 = blockChain.newOutputTransaction(
                "Bob", "Fred", 12);
        blockChain.addBlock(Collections.singletonList(transaction4));

        try {
            blockChain.saveChain();
        } catch (IOException e) {
            e.printStackTrace();
        }
        blockChain.displayChain();

        checkAssertion("Donis", 25, blockChain.getBalance("Donis"));
        checkAssertion("Bob", 38, blockChain.getBalance("Bob"));
        checkAssertion("Fred", 37, blockChain.getBalance("Fred"));
    }

    private void readBlockChain() {
        try {
            Blockchain blockChain = Blockchain.readChain();

            checkAssertion("Donis", 25, blockChain.getBalance("Donis"));
            checkAssertion("Bob", 38, blockChain.getBalance("Bob"));
            checkAssertion("Fred", 37, blockChain.getBalance("Fred"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAssertion(String name, int target, int actuals) {
        Assert.assertEquals(String.format("Wrong balance for %s", name), target, actuals);
        System.out.println(String.format("The balance is %s %d", name, actuals));

    }

}
