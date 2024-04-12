package com.example.wallet.module;

import com.example.wallet.core.contnat.Constants;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.WalletUtils;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

public  class WalletInitMethod {

    /**
     * 创建keyStore的文件目录
     */
    private static void checkFilePath(){
        File directory = new File(Constants.KEYSTORE_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * 生成web3的助记词
     * @return list
     * @throws MnemonicException.MnemonicLengthException e
     */
    public static List<String> createMnemonic() throws MnemonicException.MnemonicLengthException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] entropy = new byte[DeterministicSeed.DEFAULT_SEED_ENTROPY_BITS / 8];
        secureRandom.nextBytes(entropy);
        //生成12位助记词
        return MnemonicCode.INSTANCE.toMnemonic(entropy);
    }

    /**
     * 以太创建keyStore
     * ps:password自己改哦，现在我写死的是123456
     * @return Bip39Wallet
     * @throws CipherException Exception
     * @throws IOException Exception
     */
    public static Bip39Wallet createWalletEthereum() throws CipherException, IOException {
        checkFilePath();
        //用为web3的公共Utils 创建一个Bip39 类型的钱包
        return WalletUtils.generateBip39Wallet(Constants.PASSWORD, new File(Constants.KEYSTORE_PATH));
    }

    /**
     * 十六进制字符串转换为字节数组
     * @param s 16进制的字符串，我们的私钥就是一个16进制的字符串
     * @return byte[]
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * 创建一个指定长度的 byte[]
     * @param length 长度
     * @return byte[]
     */
    public static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    /**
     * byte[]数组转换成十六进制字符串，我们私钥刚创建出来是一个byte数组，
     * 然后需要转换成16进制字符串才能正常使用
     * @param bytes bytes
     * @return String
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
