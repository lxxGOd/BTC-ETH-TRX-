package com.example.wallet.btc;

import com.example.wallet.core.WalletModule;
import com.example.wallet.core.WalletInitMethod;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

import java.util.Date;
import java.util.List;

/**
 * BTC的钱包创建这块
 * @author lxz
 */
public class BTCUtils {

    /**
     * 创建BTC钱包
     * @return WalletModule
     */
    public WalletModule createWallet(){
        WalletModule module = new WalletModule();
        try {
            List<String> list = WalletInitMethod.createMnemonic();
            byte[] seed = MnemonicCode.INSTANCE.toEntropy(list);
            DeterministicSeed deterministicSeed = new DeterministicSeed(list,seed,null,new Date().getTime());
            Wallet wallet = Wallet.fromSeed(NetworkParameters.fromID("org.bitcoin.production"), deterministicSeed);//主网，你也可以选择测试网
            module.setAddress(wallet.currentReceiveAddress().toString());
            wallet.getIssuedReceiveKeys();
            //ps:这里为什么要做一个这样的处理，是因为BitcoinJ 库会从比特币网络中下载区块并同步交易历史，
            // 这个过程可能需要一些时间，特别是在刚开始使用钱包时，或者你可以添加一个监听器addChangeEventListener 也行
            while (wallet.getIssuedReceiveKeys().isEmpty()){
                Thread.sleep(2000);//直接等待五秒
            }
            ECKey ecKey  = wallet.getIssuedReceiveKeys().get(0);
            module.setMnemonic(list);
            module.setPrivateKey(ecKey.getPrivateKeyAsHex());
            module.setPublicKey(ecKey.getPublicKeyAsHex());
        } catch (MnemonicException.MnemonicWordException | MnemonicException.MnemonicChecksumException |
                 MnemonicException.MnemonicLengthException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return module;
    }

    /**
     * 这个就是把助记词传过来就行
     * @param mnemonic mnemonic
     * @return WalletModule
     */
    public WalletModule createWalletByMnemonic(List<String> mnemonic){
        WalletModule module = new WalletModule();
        try {
            List<String> list = mnemonic;
            byte[] seed = MnemonicCode.INSTANCE.toEntropy(list);
            DeterministicSeed deterministicSeed = new DeterministicSeed(list,seed,null,new Date().getTime());
            Wallet wallet = Wallet.fromSeed(NetworkParameters.fromID("org.bitcoin.production"), deterministicSeed);//主网，你也可以选择测试网
            module.setAddress(wallet.currentReceiveAddress().toString());
            wallet.getIssuedReceiveKeys();
            //ps:这里为什么要做一个这样的处理，是因为BitcoinJ 库会从比特币网络中下载区块并同步交易历史，
            // 这个过程可能需要一些时间，特别是在刚开始使用钱包时，或者你可以添加一个监听器addChangeEventListener 也行
            while (wallet.getIssuedReceiveKeys().isEmpty()){
                Thread.sleep(2000);//直接等待五秒
            }
            ECKey ecKey  = wallet.getIssuedReceiveKeys().get(0);
            module.setMnemonic(list);
            module.setPrivateKey(ecKey.getPrivateKeyAsHex());
            module.setPublicKey(ecKey.getPublicKeyAsHex());
        } catch (MnemonicException.MnemonicWordException | MnemonicException.MnemonicChecksumException |
                 MnemonicException.MnemonicLengthException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return module;
    }



    /**
     * 根据私钥生成助记地址
     * @param privateKey privateKey
     * @return WalletModule
     */
    public WalletModule createWalletByPrivate(String privateKey){
        WalletModule module = new WalletModule();
        byte[] bytes = hexStringToByteArray(privateKey);
        ECKey ecKey = ECKey.fromPrivate(bytes);
        MainNetParams mainNetParams = MainNetParams.get();
        String address = ecKey.toAddress(mainNetParams).toString();
        module.setAddress(address);
        module.setPrivateKey(ecKey.getPrivateKeyAsHex());
        module.setPublicKey(ecKey.getPublicKeyAsHex());
        return module;
    }


    /**
     * 把16进制的字符串转换成数组
     * @param s
     * @return
     */
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
