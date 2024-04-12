package com.example.wallet;

import cn.hutool.json.JSONUtil;
import com.example.wallet.module.WalletInitMethod;
import com.example.wallet.module.WalletModule;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class WalletApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void btc(){
        WalletModule module = new WalletModule();
        try {
            List<String> list = WalletInitMethod.createMnemonic();
            byte[] seed = MnemonicCode.INSTANCE.toEntropy(list);
            DeterministicSeed deterministicSeed = new DeterministicSeed(list,seed,null,new Date().getTime());
            Wallet wallet = Wallet.fromSeed(NetworkParameters.fromID("org.bitcoin.production"), deterministicSeed);
            module.setAddress(wallet.currentReceiveAddress().toString());
            wallet.getIssuedReceiveKeys();
            //ps:这里为什么要做一个这样的处理，是因为BitcoinJ 库会从比特币网络中下载区块并同步交易历史，
            // 这个过程可能需要一些时间，特别是在刚开始使用钱包时
            while (wallet.getIssuedReceiveKeys().isEmpty()){
                Thread.sleep(2000);//直接等待五秒
            }
            ECKey ecKey  = wallet.getIssuedReceiveKeys().get(0);
            module.setMnemonic(list);
            module.setPrivateKey(ecKey.getPrivateKeyAsHex());
            module.setPublicKey(ecKey.getPublicKeyAsHex());
            System.out.println(JSONUtil.parse(module));
        } catch (MnemonicException.MnemonicWordException | MnemonicException.MnemonicChecksumException |
                 MnemonicException.MnemonicLengthException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void aa(){
        String oldADDRESS = "1PgxJhk2WcFQMi39Gzk4ivKYnxBiGMd5z4";
        String privatKey = "3e54c0ded3bb088d891d113a8f95907de492fb768c8aadc7a977aa0654ea3e70";
        byte[] bytes = hexStringToByteArray(privatKey);
        ECKey ecKey = ECKey.fromPrivate(bytes);
        MainNetParams mainNetParams = MainNetParams.get();
        String address = ecKey.toAddress(mainNetParams).toString();
    }


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
