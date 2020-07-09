package cn.antswl.ds.algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SHA 256 哈希算法
 *
 * @author David Liu
 */
public class Sha256HashAlgorithm implements HashAlgorithm {

    private final MessageDigest messageDigest;

    public Sha256HashAlgorithm() {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("初始化 SHA-256 哈希算法失败");
        }
    }

    @Override
    public long hash(String key) {
        byte[] digest = messageDigest.digest(key.getBytes());

        long h = 0;
        for (int i = 0; i < 4; i++) {
            h <<= 8;
            h |= ((int) digest[i]) & 0xFF;
        }
        return h;
    }
}
