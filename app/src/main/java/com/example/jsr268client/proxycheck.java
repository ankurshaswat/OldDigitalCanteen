package com.example.jsr268client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import com.android.volley.toolbox.HurlStack;

public class proxycheck extends HurlStack {

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {

        // Start the connection by specifying a proxy server
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                InetSocketAddress.createUnresolved("10.10.78.22", 3128));//the proxy server(Can be your laptop ip or company proxy)
        HttpURLConnection returnThis = (HttpURLConnection) url
                .openConnection(proxy);

        return returnThis;
    }
}