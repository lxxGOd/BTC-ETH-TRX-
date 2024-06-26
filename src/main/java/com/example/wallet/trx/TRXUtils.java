package com.example.wallet.trx;

import com.example.wallet.core.WalletInitMethod;
import com.example.wallet.core.WalletModule;
import org.tron.common.crypto.ECKey;
import org.tron.common.utils.Base58;
import org.web3j.crypto.MnemonicUtils;

import java.util.Arrays;

/**
 * trx的钱包创建这块
 * @author lxz
 */
public class TRXUtils {


    /**
     * 创建TRX钱包
     * 这个没有keysTRON
     * 不过可以试一下熵值创建，然后吧这些信息保存起来
     * key,address等信息存起来自己定义一个keyStrone
     * @return module
     */
    public WalletModule createWallet(){
        WalletModule module = new WalletModule();
        byte[] entropy = WalletInitMethod.generateRandomBytes(16);//其实这个你就可以保存起来，因为是生成助记词很重要的一个熵，没办法逆推算，写入到keyStron种
        String mnemonic = MnemonicUtils.generateMnemonic(entropy);
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        // 从种子生成私钥
        ECKey ecKey = ECKey.fromPrivate(seed);

        // 获取公钥和地址
        byte[] publicKeyBytes = ecKey.getPubKey();
        String address = Base58.encode(ecKey.getAddress());
        module.setAddress(address);
        module.setPublicKey(WalletInitMethod.bytesToHex(publicKeyBytes));
        module.setPrivateKey(WalletInitMethod.bytesToHex(ecKey.getPrivateKey()));
        module.setMnemonic(Arrays.asList(mnemonic.split(" ")));
        return module;
    }

    /**
     * 其实就是省了创建助记词的步骤，
     * @param mnemonic 助记词，A B C D 这种隔空格二的形式
     * @return
     */
    public WalletModule createWalletByMnemonic(String mnemonic){
        WalletModule module = new WalletModule();
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
        // 从种子生成私钥
        ECKey ecKey = ECKey.fromPrivate(seed);

        // 获取公钥和地址
        byte[] publicKeyBytes = ecKey.getPubKey();
        String address = Base58.encode(ecKey.getAddress());
        module.setAddress(address);
        module.setPublicKey(WalletInitMethod.bytesToHex(publicKeyBytes));
        module.setPrivateKey(WalletInitMethod.bytesToHex(ecKey.getPrivateKey()));
        module.setMnemonic(Arrays.asList(mnemonic.split(" ")));
        return module;
    }

    /**
     * 根据私钥生成钱包，因为没有原始熵的原因，所以就没有生成助记词
     * @param privateKey 私钥 16进制的
     * @return WalletModule
     */
    public WalletModule createWalletByPrivateKey(String privateKey){
        WalletModule module = new WalletModule();

        // 将私钥转换为字节数组
        byte[] privateKeyBytes = WalletInitMethod.hexStringToByteArray(privateKey);
        // 使用私钥生成 ECKey 对象
        ECKey ecKey = ECKey.fromPrivate(privateKeyBytes);

        // 获取公钥和地址
        byte[] publicKeyBytes = ecKey.getPubKey();
        String address = Base58.encode(ecKey.getAddress());

        module.setAddress(address);
        module.setPublicKey(WalletInitMethod.bytesToHex(publicKeyBytes));
        module.setPrivateKey(WalletInitMethod.bytesToHex(ecKey.getPrivateKey()));
        return module;
    }




    public static void main(String[] args) throws Exception {

        String privte = "2af57d403c0d47b8e805939bee77741df4e65c1e63e10c10859178f89d5e9004";
        String publick = "045f3ef9c4676f4303517e01c07123db61c1d47433f82ce737dd40b1b0eb7cb60d8ad5472e0ec5bf7dc75b26f17b348625e667ff478ed9655f10ce0423ae850429";
        String address = "0x51Hu4dn1bsqWFoVonaff5j6vTduEY";
        String zjc = "lumber twist style call deal indicate artist spell bracket wire regret there";

//        WalletModule walletByMnemonic = createWalletByMnemonic(zjc);
//        WalletModule walletByMnemonic = createWalletByPrivateKey(privte);
//        System.out.println(JSONUtil.parse(walletByMnemonic));

//        byte[] entropy = generateRandomBytes(16);
//        String mnemonic = MnemonicUtils.generateMnemonic(entropy);
//        byte[] seed = MnemonicUtils.generateSeed(mnemonic, "");
//
//        // 从种子生成私钥
//        ECKey ecKey = ECKey.fromPrivate(seed);
//
//        // 获取公钥和地址
//        byte[] publicKeyBytes = ecKey.getPubKey();
//        String address = Base58.encode(ecKey.getAddress());

        // 打印私钥、公钥和地址
//        System.out.println("Private Key: " + bytesToHex(ecKey.getPrivateKey()));
//        System.out.println("Public Key: " + bytesToHex(publicKeyBytes));
//        System.out.println("Address: 0x" + address);
//        System.out.println("Address: 0x" + mnemonic);


    }


}
