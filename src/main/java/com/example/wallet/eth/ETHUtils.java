package com.example.wallet.eth;

import com.example.wallet.module.WalletInitMethod;
import com.example.wallet.module.WalletModule;
import com.example.wallet.core.contnat.Constants;
import org.web3j.crypto.*;

import java.io.IOException;
import java.util.Arrays;

public class ETHUtils {

    /**
     * 生成一个ETH钱包（主网）
     * 包含生成keyStore
     *
     * @return WalletModule
     * @throws CipherException e
     * @throws IOException     e
     */
    public WalletModule createWallet() throws CipherException, IOException {
        WalletModule module = new WalletModule();
        Bip39Wallet walletEthereum = WalletInitMethod.createWalletEthereum();
        String mnemonic = walletEthereum.getMnemonic();
        Credentials credentials = WalletUtils.loadBip39Credentials(Constants.PASSWORD, mnemonic);
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        module.setMnemonic(Arrays.asList(walletEthereum.getMnemonic().split(",")));
        module.setKeyStoreFileName(walletEthereum.getFilename());
        module.setPublicKey(ecKeyPair.getPublicKey().toString(16));
        module.setPrivateKey(ecKeyPair.getPrivateKey().toString(16));
        module.setAddress(credentials.getAddress());
        return module;
    }

    /**
     * 这里传 A B C D这样的助记词，懒得做验证再处理了
     * 当然这里没有keyStore
     *
     * @param mnemonic 助记词
     * @return WalletModule
     */
    public static WalletModule createWalletByMnemonic(String mnemonic) {
        WalletModule module = new WalletModule();
        Credentials credentials = WalletUtils.loadBip39Credentials(Constants.PASSWORD, mnemonic);
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        module.setPublicKey(ecKeyPair.getPublicKey().toString(16));
        module.setPrivateKey(ecKeyPair.getPrivateKey().toString(16));
        module.setAddress(credentials.getAddress());
        return module;
    }

    /**
     * 根据私钥生成一个ETH钱包
     *
     * @param privateKey 私钥(16进制的私钥哦)
     * @return WalletModule
     */
    public static WalletModule createWalletByPrivateKey(String privateKey) {
        WalletModule module = new WalletModule();
        Credentials credentials = Credentials.create(privateKey);
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        module.setPublicKey(ecKeyPair.getPublicKey().toString(16));
        module.setPrivateKey(ecKeyPair.getPrivateKey().toString(16));
        module.setAddress(credentials.getAddress());
        return module;
    }

    /**
     * 根绝keyStore 生成一个钱包
     *
     * @param password  这个注意，密码一定得正确 要不然就是 Invalid password provided
     * @param file_Path keyStore 文件路径
     * @return WalletModule
     * @throws CipherException e
     * @throws IOException     e
     */
    public static WalletModule createWalletByKeyStore(String password, String file_Path) throws CipherException, IOException {
        WalletModule module = new WalletModule();
        //用loadCredentials 方法就行
        Credentials credentials = WalletUtils.loadCredentials(password, file_Path);
        ECKeyPair ecKeyPair = credentials.getEcKeyPair();
        module.setPublicKey(ecKeyPair.getPublicKey().toString(16));
        module.setPrivateKey(ecKeyPair.getPrivateKey().toString(16));
        module.setAddress(credentials.getAddress());
        return module;
    }


}
