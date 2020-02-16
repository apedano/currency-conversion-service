/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apedano.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Alessandro
 */
@RestController
public class CurrencyConversionController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);
    
    @Autowired
    private CurrencyExchangeFeignProxy currencyExchangeFeignProxy;
    
    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, 
            @PathVariable String to, 
            @PathVariable BigDecimal quantity) {
        return getBeanTraditionalWay(from, to, quantity);
    }
    
    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, 
            @PathVariable String to, 
            @PathVariable BigDecimal quantity) {
        CurrencyConversionBean responseCurrencyConversionBean = currencyExchangeFeignProxy.retrieveExchangeValue(from, to);
        
        CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(responseCurrencyConversionBean.getId(), 
                from,
                to,
                responseCurrencyConversionBean.getConversionMultiple(),
                quantity, 
                quantity.multiply(responseCurrencyConversionBean.getConversionMultiple()),
                responseCurrencyConversionBean.getPort()
        );
        LOGGER.info("[CURRENCY CONVERSION SERVICE] currencyConversionBean->{}", currencyConversionBean);
        return currencyConversionBean;
    }
    
    
    private CurrencyConversionBean getBeanTraditionalWay(String from, String to, BigDecimal quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        
        ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
                "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversionBean.class, 
                uriVariables);
        CurrencyConversionBean responseCurrencyConversionBean = responseEntity.getBody();
        
        return new CurrencyConversionBean(responseCurrencyConversionBean.getId(), 
                from, 
                to, 
                responseCurrencyConversionBean.getConversionMultiple(), 
                quantity, 
                quantity.multiply(responseCurrencyConversionBean.getConversionMultiple()),
                responseCurrencyConversionBean.getPort()
        );
    }
}
