package br.com.ucb.cryptochat.util;

/**
 * Created by jonathan on 6/9/16.
 */
public class SettingsUtil {

    public static final String CERTIFICATE_KEYSTORE_PATH = "SecureServer/src/br/com/ucb/cryptochat/resources/certificate.jks";
    public static final String PASSWORD = "crypto_chat";

    public static void setSSLProperty() {
        System.setProperty("javax.net.ssl.keyStore", CERTIFICATE_KEYSTORE_PATH);
        System.setProperty("javax.net.ssl.trustStore", CERTIFICATE_KEYSTORE_PATH);
        System.setProperty("javax.net.ssl.keyStorePassword", PASSWORD);
        System.setProperty("javax.net.ssl.trustStorePassword", PASSWORD);
    }
}
