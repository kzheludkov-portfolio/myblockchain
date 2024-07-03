package com.exitCertified.blockchain.dao;

import com.exitCertified.blockchain.complete.Block;
import com.exitCertified.blockchain.complete.Blockchain;
import com.exitCertified.blockchain.complete.Transaction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class Storage {
    private Blockchain blockChain;
    private Set<String> users;

    private static final String USERS_NAME = "ECC_users.json";

    public Storage() {
        users = new HashSet<>();
        try {
            blockChain = Blockchain.readChain();
            users = Storage.readUsers();

        } catch (IOException e) {
            e.printStackTrace();

            Transaction transaction1 = Transaction.newCoinbaseTX("Donis");
            blockChain = new Blockchain(Collections.singletonList(transaction1), false);
        }
    }

    public Blockchain getBlockChain() {
        return blockChain;
    }

    public Set<String> getUsers() {
        return users;
    }

    public Set<String> addUser(String userName) {
        users.add(userName);
        return saveUsers();
    }

    public Set<String> saveUsers() {
        try (Writer writer = new FileWriter(Storage.USERS_NAME)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(users, writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public static Set<String> readUsers() throws IOException {
        Gson gson = new Gson();
        Set<String> users = null;

        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(Storage.USERS_NAME));
            java.lang.reflect.Type userType =
                    new TypeToken<Set<String>>() {
                    }.getType();
            users = gson.fromJson(br, userType);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

        return users;
    }

}
