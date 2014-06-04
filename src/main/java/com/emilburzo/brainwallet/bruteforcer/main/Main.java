package com.emilburzo.brainwallet.bruteforcer.main;

import com.emilburzo.brainwallet.bruteforcer.bruteforce.Controller;
import com.emilburzo.brainwallet.bruteforcer.log.Log;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            Log.logWithoutPrefix("Arguments don't look right\n");
            Log.logWithoutPrefix("Usage: ./brainwallet-bruteforcer <balances file> <passwords file>\n");

            System.exit(1);
        }

        String pathBalances = args[0];
        String pathPasswords = args[1];

        Controller controller = new Controller(pathBalances, pathPasswords);
        controller.doBruteforce();
    }


}
