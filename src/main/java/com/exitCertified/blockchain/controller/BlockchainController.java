package com.exitCertified.blockchain.controller;

import com.exitCertified.blockchain.Block0;
import com.exitCertified.blockchain.Blockchain0;
import com.exitCertified.blockchain.MerkleTree;
import com.exitCertified.blockchain.complete.Block;
import com.exitCertified.blockchain.complete.Blockchain;
import com.exitCertified.blockchain.complete.Transaction;
import com.exitCertified.blockchain.dao.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BlockchainController {

    @Autowired
    private Storage storage;

    public BlockchainController(Storage storage) {
        this.storage = storage;
    }

    @GetMapping
    public String index() {
        // create text block for the index page
        var text = "Blockchain demo. Use following uri: </br></br>" +
                "/create?size=10 </br>" +
                "/show </br>" +
                "/balances </br>" +
                "/balance?name=Donis </br>" +
                "/transfer?from=Donis&to=Bob&value=50 </br>" +
                "/merkleTree?transactions=a,b,c,d,e </br>";
        return text;
    }


    @GetMapping(value = "merkleTree")
    public BlockVO build(
            @RequestParam(value = "transactions") String transactions) {

        List<String> transactions_list = Arrays.asList(transactions.split(","));

        if (transactions_list.size() == 1 && transactions_list.get(0).isEmpty()) {
            transactions_list = new ArrayList<>();
            transactions_list.add("a");
            transactions_list.add("b");
            transactions_list.add("c");
            transactions_list.add("d");
            transactions_list.add("e");
        }

        MerkleTree merkle_tree = new MerkleTree(transactions_list);
        merkle_tree.merkle_tree();

        return new BlockVO(merkle_tree.getRoot(), merkle_tree.getDepth());
    }

    @GetMapping(value = "create")
    public BlockchainVO0 createBrockchain(
            @RequestParam(value = "size") int size) throws RuntimeException {

        if (size < 1)
            throw new RuntimeException("Illegal number of the size!");

        size--;
        Blockchain0 bc = new Blockchain0(size);
        Block0 prevBlock0 = new Block0("0", "Hi im the first block");
        bc.getMyBlockchain().add(prevBlock0);
        for (int ix = 0; ix < size; ix++) {
            prevBlock0 = new Block0(prevBlock0.getHash(), String.format("Iterator block:%s", ix));
            bc.getMyBlockchain().add(prevBlock0);
        }
        return new BlockchainVO0(bc.getMyBlockchain(), bc.isChainValid());
    }

    @GetMapping(value = "show")
    public BlockchainVO show() {
        BlockchainVO blockchainVO = new BlockchainVO(
                true, storage.getUsers(), storage.getBlockChain().getBlocks());
        return blockchainVO;
    }

    @GetMapping(value = "balance")
    public BalanceVO balance(
            @RequestParam(value = "name") String name) throws RuntimeException {

        return new BalanceVO(name, storage.getBlockChain().getBalance(name));
    }

    @GetMapping(value = "balances")
    public BalancesVO balances() throws RuntimeException {
        List<BalanceVO> balances = new ArrayList<>();
        for (String userName : storage.getUsers()) {
            balances.add(new BalanceVO(userName, storage.getBlockChain().getBalance(userName)));
        }

        return new BalancesVO(balances);
    }

    @GetMapping(value = "transfer")
    public TransferVO transfer(
            @RequestParam(value = "from") String fromPerson,
            @RequestParam(value = "to") String toPerson,
            @RequestParam(value = "value") int value
    ) throws RuntimeException {

        Transaction transaction2 = storage.getBlockChain().newOutputTransaction(
                fromPerson, toPerson, value);
        storage.getBlockChain().addBlock(Collections.singletonList(transaction2));

        storage.addUser(fromPerson);
        storage.addUser(toPerson);

        return new TransferVO(
                new BalanceVO(fromPerson, storage.getBlockChain().getBalance(fromPerson)),
                new BalanceVO(toPerson, storage.getBlockChain().getBalance(toPerson)));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Error error(Exception ex) {
        return new Error(ex.getMessage());
    }

    class Error {
        private String error;

        public Error(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    class BalanceVO {
        String name;
        int balance;

        public BalanceVO(String person, int balance) {
            this.name = person;
            this.balance = balance;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }

    }

    class BalancesVO {
        List<BalanceVO> balances;

        public BalancesVO(List<BalanceVO> balances) {
            this.balances = balances;
        }

        public List<BalanceVO> getBalances() {
            return balances;
        }
    }

    class TransferVO {
        BalanceVO payer;
        BalanceVO payee;

        public TransferVO(BalanceVO payer, BalanceVO payee) {
            this.payer = payer;
            this.payee = payee;
        }

        public BalanceVO getPayer() {
            return payer;
        }

        public BalanceVO getPayee() {
            return payee;
        }
    }

    class BlockVO {

        String root;
        int depth;

        BlockVO(String root, int depth) {
            this.root = root;
            this.depth = depth;
        }

        public String getRoot() {
            return root;
        }

        public void setRoot(String root) {
            this.root = root;
        }

        public int getDepth() {
            return depth;
        }

        public void setDepth(int depth) {
            this.depth = depth;
        }
    }


    class BlockchainVO0 {
        private List<Block0> block0;
        private boolean isValid;

        public BlockchainVO0(List<Block0> block0, boolean isValid) {
            this.block0 = block0;
            this.isValid = isValid;
        }

        public List<Block0> getBlock0() {
            return block0;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setBlock0(List<Block0> block0) {
            this.block0 = block0;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }
    }

    class BlockchainVO {
        private boolean isValid;
        private Set<String> users;
        private List<Block> blocks;

        public BlockchainVO(boolean isValid, Set<String> users, List<Block> blocks) {
            this.isValid = isValid;
            this.users = users;
            this.blocks = blocks;
        }

        public boolean isValid() {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        public Set<String> getUsers() {
            return users;
        }

        public void setUsers(Set<String> users) {
            this.users = users;
        }

        public List<Block> getBlocks() {
            return blocks;
        }

        public void setBlocks(List<Block> blocks) {
            this.blocks = blocks;
        }
    }
}
