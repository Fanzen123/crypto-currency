package com.portfolio.cryptocurrency.api;

import com.contract.cryptocurrency_portfolio_contract.api.ItemApiController;
import com.contract.cryptocurrency_portfolio_contract.api.ItemApiDelegate;
import com.contract.cryptocurrency_portfolio_contract.dto.CryptoCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class ItemApi extends ItemApiController {

    public ItemApi() {
        super(new ItemApiDelegateImpl());
    }
}
