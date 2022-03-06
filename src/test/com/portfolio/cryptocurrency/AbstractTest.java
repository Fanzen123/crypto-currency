package com.portfolio.cryptocurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class AbstractTest {

    @Autowired
    protected MockMvc mockMvc;

    static protected final int port = 8089;

    static protected final String host = "localhost";



}
