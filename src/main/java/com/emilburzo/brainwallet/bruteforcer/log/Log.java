package com.emilburzo.brainwallet.bruteforcer.log;

public class Log {

    private static void log(String msg, boolean prefix) {
        String output = msg;

        if (prefix) {
            output = "[*] " + output;
        }

        System.out.println(output);
    }

    public static void logWithPrefix(String msg) {
        log(msg, true);
    }

    public static void logWithoutPrefix(String msg) {
        log(msg, false);
    }

}
