package com.emilburzo.brainwallet.bruteforcer.bitcoin;

public class BitcoinAddress implements Comparable<BitcoinAddress> {

    public String pub;
    public Double balance;

    public BitcoinAddress(String pub, Double balance) {
        this.pub = pub;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Address{" +
                "pub='" + pub + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public int compareTo(BitcoinAddress o) {
        return this.pub.compareTo(o.pub);
    }
}
