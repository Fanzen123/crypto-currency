package com.portfolio.cryptocurrency.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.portfolio.cryptocurrency.AbstractTest;
import junit.framework.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;

import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiTest extends AbstractTest {

    Logger logger = LoggerFactory.getLogger(ApiTest.class);

    public final String URL = String.format("http://%s:%d", host, port);

    public final String URL_ADD = URL + "/item";

    @Test
    public void shouldReturnHttp200WhenUserAddCryptoCurrency() throws Exception {

        logger.info("GIVEN : an user want to add a cryptocurrency asset using the existing services available.");
        final String cryptoCurrency = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":10,\"date\":\"2021-06-08\"}";

        final String jsonExpected = "{\"id\":1,\"symbol\":\"BITCOIN\",\"amount\":10,\"entryDate\":\"2021-06-08\"," +
                "\"location\":\"BITFINEX\",\"oldMarketValue\":10,\"actualMarketValue\":10}";

        logger.info(String.format("WHEN : an user add a cryptocurrency asset by calling the service on endpoint %s with content = %s", URL_ADD, cryptoCurrency));
        final ResultActions result = this.mockMvc
                .perform(post("/item")
                        .content(cryptoCurrency).contentType(MediaType.APPLICATION_JSON));

        logger.info("THEN : an http code 201 is returned");
        Assert.assertEquals(HttpStatus.CREATED.value(), result.andReturn().getResponse().getStatus());
        Assert.assertEquals(jsonExpected, result.andReturn().getResponse().getContentAsString());
    }

}
