package com.test;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * 第三方安全控制类实例
 *
 * @author zhangyf
 */
public class ThirdSecurity {

    /**
     * 由单例类直接 初始化
     */
    private static final ThirdSecurity thirdChannelSecurity = new ThirdSecurity();

    /**
     * 鹏华公钥对象
     */
    private PublicKey phfundPublicKey = null;

    /**
     * 渠道公钥对象
     */
    private PublicKey channelPublicKey = null;

    /**
     * 渠道私钥对象
     */
    private PrivateKey channelPrivateKey = null;

    //private static String phPropName = PropUtil.getValue("application", "phfund.prop.name");


    private ThirdSecurity() {
        this.phfundPublicKey = initPublicKey("D://test/phfundPubKey.cer");
        this.channelPublicKey = initPublicKey("D://test/channelPubKey.cer");
        this.channelPrivateKey = initPrivateKey(
                "D://test/channelPriKey.jks",
                "channelFUND_2017",
                "channel",
                "channelFUND_2017"
        );

    }

    /**
     * 获取第三方安全控制类实例
     *
     * @return
     */
    public static ThirdSecurity getInstance() {
        return thirdChannelSecurity;
    }

    /**
     * 初始化公钥证书
     *
     * @param fileName
     * @return
     */
    private PublicKey initPublicKey(String fileName) {
        PublicKey publicKey = null;
        try {
            publicKey = getPublicKey(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("公钥证书文件[" + fileName + "]读取失败，请确保加载到了CLASSPATH中.");
        }

        return publicKey;
    }

    /**
     * 初始化私钥证书
     *
     * @param fileName
     * @return
     */
    private PrivateKey initPrivateKey(String fileName, String storePass,
                                      String keyAlias, String keyPass) {
        PrivateKey privateKey = null;
        try {
            privateKey = getPrivateKey(fileName, storePass, keyAlias, keyPass);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("私钥证书文件[" + fileName
                    + "]读取失败，请确保加载到了CLASSPATH中.");
        }

        return privateKey;
    }

    /**
     * 获取公钥
     *
     * @param publicKeyFile 公钥文件
     * @return 公钥对象
     * @throws Exception
     */
    private PublicKey getPublicKey(String publicKeyFile) throws Exception {
        InputStream inputStream = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            File file = new File(publicKeyFile);
            inputStream = new FileInputStream(file);
            Certificate cert = cf.generateCertificate(inputStream);
            return cert.getPublicKey();
        } catch (Exception e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 获取私钥
     *
     * @param privateKeyFile 私钥文件
     * @param storePass      store password
     * @param keyAlias       key alias
     * @param keyPass        key password
     * @return 私钥
     * @throws Exception
     */
    private PrivateKey getPrivateKey(String privateKeyFile, String storePass,
                                     String keyAlias, String keyPass) throws Exception {
        InputStream inputStream = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");

            inputStream = new FileInputStream(new File(privateKeyFile));

            keyStore.load(inputStream, storePass.toCharArray());
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias,
                    keyPass.toCharArray());

            return privateKey;
        } catch (Exception e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 鹏华公钥证书
     *
     * @return
     */
    public PublicKey getPhfundPublicKey() {
        return phfundPublicKey;
    }

    /**
     * 鹏华公钥证书
     *
     * @param phfundPublicKey
     */
    public void setPhfundPublicKey(PublicKey phfundPublicKey) {
        this.phfundPublicKey = phfundPublicKey;
    }


    /**
     * 渠道公钥证书
     *
     * @return
     */
    public PublicKey getChannelPublicKey() {
        return channelPublicKey;
    }

    /**
     * 渠道公钥证书
     *
     * @param channelPublicKey
     */
    public void setChannelPublicKey(PublicKey channelPublicKey) {
        this.channelPublicKey = channelPublicKey;
    }

    /**
     * 渠道私钥证书
     *
     * @return
     */
    public PrivateKey getChannelPrivateKey() {
        this.channelPrivateKey = initPrivateKey(
                "D://test/channelPriKey.jks",
                "channelFUND_2017",
                "channel",
                "channelFUND_2017"
        );
        return channelPrivateKey;
    }

    /**
     * 渠道私钥证书
     *
     * @param channelPrivateKey
     */
    public void setChannelPrivateKey(PrivateKey channelPrivateKey) {
        this.channelPrivateKey = channelPrivateKey;
    }


}