package com.emilburzo.brainwallet.bruteforcer.bruteforce;

import com.emilburzo.brainwallet.bruteforcer.bitcoin.DigestUtil;
import com.emilburzo.brainwallet.bruteforcer.log.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Controller {

    private final Set<String> balances = new HashSet<>();
    private final List<String> passwords = new ArrayList<>();

    private String pathBalances;
    private String pathPasswords;

    public Controller(String pathBalances, String pathPasswords) {
        this.pathBalances = pathBalances;
        this.pathPasswords = pathPasswords;

        // do the files actually exist?
        checkPaths();

        // self test
        doSelfTest();

        // parse existing bitcoin addresses
        parseValidAddresses();

        // parse password file
        parsePasswords();
    }

    private void checkPaths() {
        Log.logWithPrefix("balances: " + pathBalances);
        Log.logWithPrefix("passwords: " + pathPasswords);

        checkPath(pathBalances);
        checkPath(pathPasswords);
    }

    private void checkPath(String path) {
        File file = new File(path);

        if (!file.exists()) {
            throw new RuntimeException(String.format("Path '%s' doesn't exist", path));
        }

        if (file.isDirectory()) {
            throw new RuntimeException(String.format("Path '%s' is a directory", path));
        }

        if (!file.canRead()) {
            throw new RuntimeException(String.format("Can't read '%s'. Do I have permission?", path));
        }
    }

    private void doSelfTest() {
        Log.logWithPrefix("Running self tests");

        // test password -> public key generation
        String password = "test";
        String expectedPubAddress = "1HKqKTMpBTZZ8H5zcqYEWYBaaWELrDEXeE";
        String pubAddress = DigestUtil.getPublicAddress(password);

        if (pubAddress.equals(expectedPubAddress)) {
            Log.logWithPrefix("Password to public key generation: OK");
        } else {
            throw new RuntimeException("Password to public key generation self test failed");
        }
    }


    private void parseValidAddresses() {
        try {
            Log.logWithPrefix("Loading balances file");

            BufferedReader br = new BufferedReader(new FileReader(pathBalances));
            String line;

            while ((line = br.readLine()) != null) {
                balances.add(line);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsePasswords() {
        try {
            Log.logWithPrefix("Loading passwords file");

            BufferedReader br = new BufferedReader(new FileReader(pathPasswords));
            String line;

            while ((line = br.readLine()) != null) {
                passwords.add(line);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doBruteforce() {
        // TODO runtime option to HT or not
        int procs = Runtime.getRuntime().availableProcessors();
        int threads = procs > 1 ? procs / 2 : procs;
        int passwordSize = passwords.size();
        // handle very small password lists
        int chunk = passwordSize > threads ? passwordSize / threads : passwordSize;

        Log.logWithPrefix(String.format("Found %d addresses", balances.size()));
        Log.logWithPrefix(String.format("Found %d passwords", passwords.size()));
        Log.logWithPrefix(String.format("Chunk: %d", chunk));
        Log.logWithPrefix(String.format("Detected %d processors", procs));
        Log.logWithPrefix(String.format("Using %d threads", threads));

        int begin = 0;
        int end = chunk;

        for (int i = 0; i < threads; i++) {
            Log.logWithPrefix(String.format("Splitting list from %10d to %10d", begin, end));

            List<String> pwds = passwords.subList(begin, end);

            Bruteforcer bf = new Bruteforcer(balances, pwds);
            bf.start();

            begin = end;
            end += chunk;

            if (end > passwords.size()) {
                end = passwords.size();
            }

            if (begin >= passwords.size()) {
                break;
            }
        }
    }
}
