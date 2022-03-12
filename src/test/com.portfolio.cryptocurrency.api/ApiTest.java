package com.portfolio.cryptocurrency.api;

import com.contract.cryptocurrency_portfolio_contract.dto.Symbol;
import com.portfolio.cryptocurrency.AbstractTest;
import com.portfolio.cryptocurrency.business.Market;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 *  Integration test component
 */
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApiTest extends AbstractTest {

    private static final String URL_ENTRY = "/item";
    private static final String URL_GET_ENTRY = "/item/:symbol";
    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    private final String URL = String.format("http://%s:%d", host, port);

    private final String URL_ADD = URL + URL_ENTRY;

    private double BITCOIN_VALUE;

    private double ETHEREUM_VALUE;

    @BeforeEach
    public void defineValues() throws IOException {
        BITCOIN_VALUE = new Market().getActualValue(Symbol.BITCOIN);
        ETHEREUM_VALUE = new Market().getActualValue(Symbol.ETHEREUM);
    }

    @Test
    public void shouldReturnHttp201WhenUserAddCryptoCurrency() throws Exception {

        logger.info("GIVEN : an user want to add a cryptocurrency asset using the existing services available.");
        final String cryptoCurrency = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":10,\"date\":\"2021-06-08\"}";

        logger.info(String.format("WHEN : an user add a cryptocurrency asset by calling the service on endpoint %s with content = %s", URL_ADD, cryptoCurrency));
        final ResultActions result = this.mockMvc
                .perform(post(URL_ENTRY).content(cryptoCurrency).contentType(MediaType.APPLICATION_JSON));

        logger.info("THEN : an http code 201 is returned");
        Assert.assertEquals(HttpStatus.CREATED.value(), result.andReturn().getResponse().getStatus());
    }

    @Test
    public void shouldReturnCryptoCurrencyWhenUserAddCryptoCurrency() throws Exception {

        logger.info("GIVEN : an user want to add a cryptocurrency asset using the existing services available.");
        final String cryptoCurrency = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":10}";

        final String jsonExpected = "{\"id\":1,\"symbol\":\"BITCOIN\",\"amount\":10.0,\"entryDate\":\"" + LocalDate.now() + "\"," +
                "\"location\":\"BITFINEX\",\"oldMarketValue\":" + ( BITCOIN_VALUE * 10 )+ ",\"actualMarketValue\":" + ( BITCOIN_VALUE * 10 ) + "}";

        logger.info(String.format("WHEN : an user add a cryptoCurrency asset by calling the service on endpoint %s with content = %s", URL_ADD, cryptoCurrency));
        final ResultActions result = this.mockMvc
                .perform(post(URL_ENTRY).content(cryptoCurrency).contentType(MediaType.APPLICATION_JSON));

        logger.info("THEN : a json content describing similar data because it is the first entry for the cryptoCurrency.");
        Assert.assertEquals(jsonExpected, result.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void shouldAdditionValuesWhenUserAddCryptoCurrencyMultipleTime() throws Exception {

        logger.info("GIVEN : an user want to add a cryptocurrency asset using the existing services available.");
        final String cryptoCurrency1 = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":10}";
        final String cryptoCurrency2 = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":20}";

        final String jsonExpected = "{\"id\":1,\"symbol\":\"BITCOIN\",\"amount\":30.0,\"entryDate\":\"" + LocalDate.now() + "\"," +
                "\"location\":\"BITFINEX\",\"oldMarketValue\":" + ( BITCOIN_VALUE * 30 ) + ",\"actualMarketValue\":" + ( BITCOIN_VALUE * 30 ) + "}";

        logger.info(String.format("WHEN : an user add a cryptocurrency asset by twice calling the service on endpoint %s ", URL_ADD));
        this.mockMvc
                .perform(post(URL_ENTRY).content(cryptoCurrency1).contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult mvcResult = this.mockMvc
                .perform(post(URL_ENTRY).content(cryptoCurrency2).contentType(MediaType.APPLICATION_JSON)).andReturn();

        logger.info("THEN : a json content describing the global data cryptoCurrency.");
        Assert.assertEquals(jsonExpected, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnCryptoCurrencies() throws Exception {

        logger.info("GIVEN : an user want to get cryptocurrencies using the existing services available.");
        final String cryptoCurrency1 = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":10}";
        final String cryptoCurrency2 = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":20}";
        final String jsonExpected = "[{\"id\":1,\"symbol\":\"BITCOIN\",\"amount\":30.0,\"entryDate\":\"" + LocalDate.now() + "\"," +
                "\"location\":\"BITFINEX\",\"oldMarketValue\":" + ( BITCOIN_VALUE * 30 ) + ",\"actualMarketValue\":"
                + ( BITCOIN_VALUE * 30 ) + "}]";

        logger.info(String.format("WHEN : an user add a cryptocurrency asset by twice calling the service on endpoint %s ", URL_ADD));
        this.mockMvc.perform(post(URL_ENTRY).content(cryptoCurrency1).contentType(MediaType.APPLICATION_JSON)).andReturn();
        this.mockMvc.perform(post(URL_ENTRY).content(cryptoCurrency2).contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult mvcResult = this.mockMvc.perform(get(URL_ENTRY).contentType(MediaType.APPLICATION_JSON)).andReturn();

        logger.info("THEN : a json content describing cryptoCurrencies.");
        Assert.assertEquals(jsonExpected, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void shouldReturnCryptoCurrency() throws Exception {

        logger.info("GIVEN : an user want to get cryptocurrencies using the existing services available.");
        final String cryptoCurrency1 = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":10}";
        final String cryptoCurrency2 = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":20}";
        final String jsonExpected = "{\"id\":1,\"symbol\":\"BITCOIN\",\"amount\":30.0,\"entryDate\":\"" + LocalDate.now() + "\"," +
                "\"location\":\"BITFINEX\",\"oldMarketValue\":" + ( BITCOIN_VALUE * 30 ) + ",\"actualMarketValue\":"
                + ( BITCOIN_VALUE * 30 ) + "}";

        logger.info(String.format("WHEN : an user add a cryptocurrency asset by twice calling the service on endpoint %s ", URL_ADD));
        this.mockMvc.perform(post(URL_ENTRY).content(cryptoCurrency1).contentType(MediaType.APPLICATION_JSON)).andReturn();
        this.mockMvc.perform(post(URL_ENTRY).content(cryptoCurrency2).contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult mvcResult =
                this.mockMvc.perform(get(URL_GET_ENTRY.replace(":symbol", Symbol.BITCOIN.name()))
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        logger.info("THEN : a json content describing cryptoCurrencies.");
        Assert.assertEquals(jsonExpected, mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void shouldDeleteCryptoCurrency() throws Exception {

        logger.info("GIVEN : an user want to delte a cryptocurrency using the existing services available.");
        final String cryptoCurrency1 = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":10}";
        final String cryptoCurrency2 = "{\"symbol\":\"BITCOIN\",\"location\":\"BITFINEX\",\"amount\":20}";
        final String jsonExpected = "{\"id\":1,\"symbol\":\"BITCOIN\",\"amount\":30.0,\"entryDate\":\"" + LocalDate.now() + "\"," +
                "\"location\":\"BITFINEX\",\"oldMarketValue\":" + ( BITCOIN_VALUE * 30 ) + ",\"actualMarketValue\":"
                + ( BITCOIN_VALUE * 30 ) + "}";

        logger.info(String.format("WHEN : an user add a cryptocurrency asset by twice calling the service on endpoint %s ", URL_ADD));
        this.mockMvc.perform(post(URL_ENTRY).content(cryptoCurrency1).contentType(MediaType.APPLICATION_JSON)).andReturn();
        this.mockMvc.perform(post(URL_ENTRY).content(cryptoCurrency2).contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult mvcResult =
                this.mockMvc.perform(get(URL_GET_ENTRY.replace(":symbol", Symbol.BITCOIN.name()))
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        Assert.assertEquals(jsonExpected, mvcResult.getResponse().getContentAsString());

        this.mockMvc.perform(delete(URL_GET_ENTRY.replace(":symbol", Symbol.BITCOIN.name()))
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        mvcResult =
                this.mockMvc.perform(get(URL_GET_ENTRY.replace(":symbol", Symbol.BITCOIN.name()))
                        .contentType(MediaType.APPLICATION_JSON)).andReturn();

        logger.info("THEN : an empty json content.");
        Assert.assertEquals("", mvcResult.getResponse().getContentAsString());
    }
}
