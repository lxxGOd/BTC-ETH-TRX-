package com.example.wallet.module;

import lombok.Data;

import java.util.List;

@Data
public class WalletModule {

    /**
     * 钱包地址
     */
    private String address;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 帮记词
     */
    private List<String> mnemonic;
    /**
     * keyStore 文件内容
     */
    private String keyStore;
    /**
     * keyStore 文件名称
     */
    private String keyStoreFileName;
}
