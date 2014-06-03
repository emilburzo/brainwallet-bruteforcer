package com.emilburzo.brainwallet.bruteforcer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private final Map<String, Double> balances = new HashMap<>();
    private final List<String> passwords = new ArrayList<>();

    private String pathBalances;
    private String pathPasswords;

    public Controller(String pathBalances, String pathPasswords) {
        this.pathBalances = pathBalances;
        this.pathPasswords = pathPasswords;

        // self test
        doSelfTest();

        // parse existing bitcoin addresses which have a >0 balance
        parseValidAddresses();

        // parse password file
        parsePasswords();
    }

    private void doSelfTest() {
        System.out.println("[*] Running self tests");

        // test password -> public key generation
        String password = "test";
        String expectedPubAddress = "1HKqKTMpBTZZ8H5zcqYEWYBaaWELrDEXeE";
        String pubAddress = DigestUtil.getPublicAddress(password);

        if (pubAddress.equals(expectedPubAddress)) {
            System.out.println("[*] Password to public key generation: OK");
        } else {
            throw new RuntimeException("Password to public key generation self test failed");
        }
    }


    private void parseValidAddresses() {
        try {
            System.out.println("[*] Loading balances file");

            Reader in = new FileReader(pathBalances);

            Iterable<CSVRecord> parser = CSVFormat.DEFAULT.parse(in);

            balances.put("1HKqKTMpBTZZ8H5zcqYEWYBaaWELrDEXeE", 0.00);

            for (CSVRecord record : parser) {
                String addr = record.get(0).trim();
                String balance = record.get(1).trim();

                if (addr.isEmpty()) {
                    continue;
                }

                balances.put(addr, Double.parseDouble(balance));
            }

            System.out.println(String.format("[*] Found %d addresses", balances.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parsePasswords() {
        try {
            System.out.println("[*] Loading passwords file");

            BufferedReader br = new BufferedReader(new FileReader(pathPasswords));
            String line;

            passwords.add("test");

            while ((line = br.readLine()) != null) {
                passwords.add(line.trim());
            }

            br.close();

            System.out.println(String.format("[*] Found %d passwords", passwords.size()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doBruteforce() {
        // TODO option to HT or not
        int procs = Runtime.getRuntime().availableProcessors();
        int threads = procs > 1 ? procs / 2 : procs;
        int chunk = passwords.size() / threads;

        System.out.println(String.format("[*] Detected %d processors", procs));
        System.out.println(String.format("[*] Using %d threads", threads));

        int begin = 0;
        int end = chunk;

        for (int i = 0; i < threads; i++) {
            System.out.println(String.format("[*] Splitting list from %5d to %5d", begin, end));

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
