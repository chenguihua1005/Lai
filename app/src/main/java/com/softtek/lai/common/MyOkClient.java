package com.softtek.lai.common;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import retrofit.client.Request;
import retrofit.client.UrlConnectionClient;

/**
 * Created by jerry.guan on 4/26/2017.
 */

public class MyOkClient extends UrlConnectionClient {

    private static OkHttpClient generateDefaultOkHttp() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(15 * 1000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(20 * 1000, TimeUnit.MILLISECONDS);
        TrustManager tm=new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        SSLContext sslContext=null;
        TrustManagerFactory trustManagerFactory=null;
        try {
            sslContext=SSLContext.getInstance("TLS");
            trustManagerFactory=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            sslContext.init(null,new TrustManager[]{tm},null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.setSslSocketFactory(sslContext.getSocketFactory());
        client.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        return client;
    }

    private final OkUrlFactory okUrlFactory;

    public MyOkClient() {
        this(generateDefaultOkHttp());
    }

    public MyOkClient(OkHttpClient client) {
        this.okUrlFactory = new OkUrlFactory(client);
    }

    @Override protected HttpURLConnection openConnection(Request request) throws IOException {
        return okUrlFactory.open(new URL(request.getUrl()));
    }
}
