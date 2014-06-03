package com.emilburzo.brainwallet.bruteforcer;

import com.google.bitcoin.core.Address;
import com.google.bitcoin.core.ECKey;
import com.google.bitcoin.params.MainNetParams;
import org.spongycastle.crypto.digests.RIPEMD160Digest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil {

    public static byte[] RIPEMD160(byte[] input) {
        RIPEMD160Digest d = new RIPEMD160Digest();

        d.update(input, 0, input.length);

        byte[] o = new byte[d.getDigestSize()];

        d.doFinal(o, 0);

        return o;
    }

    public static byte[] SHA256(byte[] input) {
        byte[] hash = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input);
            hash = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash;
    }

    public static ECKey createAddress(byte[] secret) {
        byte[] hash = SHA256(secret);

        ECKey key = new ECKey(hash, null);

        return key;
    }

    public static String getPublicAddress(String input) {
        ECKey address = createAddress(input.getBytes());

        byte[] hash160 = RIPEMD160(SHA256(address.getPubKey()));

        Address addr = new Address(MainNetParams.get(), hash160);

        return addr.toString();
    }

}
