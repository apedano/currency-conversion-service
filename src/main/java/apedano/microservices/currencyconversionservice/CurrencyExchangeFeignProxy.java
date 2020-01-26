/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apedano.microservices.currencyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *The name is the name of the service we want to consume from its property file
 * 
 * @author Alessandro
 */
//@FeignClient(name="currency-exchange-service", url="localhost:8000")
//@FeignClient(name="currency-exchange-service")
@FeignClient(name="netflix-zuul-api-gateway-server") //to use the api
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeFeignProxy {
    
    /*
    This is the mapping from the CurrencyExchangeController (that one we want to use)
    */
    //@GetMapping("/currency-exchange/from/{from}/to/{to}")
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversionBean retrieveExchangeValue(@PathVariable String from, @PathVariable String to);
}
