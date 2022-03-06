package com.portfolio.cryptocurrency.api;

import com.google.gson.Gson;
import com.portfolio.cryptocurrency.AbstractTest;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;

import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest extends AbstractTest {

    Logger logger = LoggerFactory.getLogger(ApiTest.class);

    static final String URL = String.format("http://%s:%d/user/", host, port);

    @Test
    public void shouldReturnHttp200WhenUserAddCryptoCurrency() throws Exception {

        logger.info("GIVEN : an user want to add a cryptocurrency asset using the existing services available.");
        final CryptoCurrency cryptoCurrency = new CryptoCurrency();
        cryptoCurrency.setSymbol("btcusd");
        cryptoCurrency.setLocation("Bitfinex");
        cryptoCurrency.setValue(10L);
        cryptoCurrency.setDate(LocalDate.now());

        Gson gson = new Gson();

        final String jsonExpected = "{'id' : 1, 'symbol':'btcusd', 'amount': 10, 'dateEntry' : '20.01.2021', " +
                "'location':'Bitfinex', 'date', '20.01.2021', 'valueWhenPurchased' : 10, 'actualValue' : 10}";


        logger.info(String.format("WHEN : an user add a cryptocurrency asset by calling the service on endpoint %s with content = %s", URL, gson.toJson(cryptoCurrency)));
        final ResultActions result = this.mockMvc
                .perform(post(URL)
                        .content(gson.toJson(cryptoCurrency)));

        logger.info("THEN : an http code 200 is returned");
        Assert.assertEquals(200, result.andReturn().getResponse().getStatus());
        Assert.assertEquals(jsonExpected, result.andReturn().getResponse().getContentAsString());
    }

}
