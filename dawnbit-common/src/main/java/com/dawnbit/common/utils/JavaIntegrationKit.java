package com.dawnbit.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author SouravSharma
 */

/**
 * @author SouravSharma
 *
 */
@Component
@Slf4j
public class JavaIntegrationKit {

    private static boolean error = false;

    public static String hashCal(final String type, final String str) {
        final byte[] hashseq = str.getBytes();
        final StringBuffer hexString = new StringBuffer();
        try {
            final MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            final byte messageDigest[] = algorithm.digest();
            for (final byte element : messageDigest) {
                final String hex = Integer.toHexString(0xFF & element);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }

        } catch (final NoSuchAlgorithmException nsae) {
        }
        return hexString.toString();
    }

    public static void trustSelfSignedSSL() {
        try {
            final SSLContext ctx = SSLContext.getInstance("TLS");
            final X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(final X509Certificate[] xcs, final String string)
                        throws CertificateException {
// do nothing
                }

                @Override
                public void checkServerTrusted(final X509Certificate[] xcs, final String string)
                        throws CertificateException {
// do nothing
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLContext.setDefault(ctx);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean empty(final String s) {
        if ((s == null) || s.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
