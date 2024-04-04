package com.example.wallet.core.contnat;

import com.google.common.collect.ImmutableList;
import org.bitcoinj.crypto.ChildNumber;

public class Constants {

    /**
     * 主网路径  m44'/60'/0'/0/0 主流钱包一般用的都是这个
     * ps:其余的钱包可能有自定义路径或者是  m44'/60'/0'/0
     *    我们已诸王路径为例子
     */
    public static final ImmutableList<ChildNumber> BIP44_ZERO_PATH = ImmutableList.of(new ChildNumber(44, true), new ChildNumber(60, true),
            ChildNumber.ZERO_HARDENED, ChildNumber.ZERO, ChildNumber.ZERO);

    public static final String TRX = "TRX";
    public static final String ETH = "ETH";
    public static final String BTC = "BTC";

    /**
     * 默认密码（创建钱包的时候用）
     */
    public static final String PASSWORD = "123456";

    /**
     * keyStore 文件路径;
     * ps:keyStore 是钱包中的一个重要节点，备份可以使用（当然助记词也可以）
     */
    public static final String KEYSTORE_PATH = System.getProperty("user.dir")+"/keyStore";


}
