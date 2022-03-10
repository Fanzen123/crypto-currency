package com.portfolio.cryptocurrency.business;

import com.contract.cryptocurrency_portfolio_contract.dto.Symbol;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Market {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    public static final String URL_API_GET_VALUE = "https://api.bitfinex.com/v1/pubticker/";
    public static final String PRICE_PARAM = "last_price";

    public long getActualValue(Symbol symbol) throws IOException {

        String result = null;

        switch (symbol) {
            case BITCOIN:
                result = doGetRequest(URL_API_GET_VALUE + "btceur");
            case ETHERUM:
                result = doGetRequest(URL_API_GET_VALUE + "etheur");
        }

        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        JsonObject  jsonObject = gson.fromJson(result, JsonObject.class);

        return jsonObject.get(PRICE_PARAM).getAsLong();
    }

    public String doGetRequest(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(request);
        String result = EntityUtils.toString(response.getEntity());
        response.close();
        return result;
    }
}
