package com.emilburzo.brainwallet.bruteforcer;

public class Main {

    public static void main(String[] args) {
        String pathBalances = args[0];
        String pathPasswords = args[1];

        System.out.println("pathPasswords = " + pathPasswords);
        System.out.println("pathBalances = " + pathBalances);

        Controller controller = new Controller(pathBalances, pathPasswords);
        controller.doBruteforce();
    }
}
