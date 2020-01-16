/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apedano.microservices.currencyconversionservice;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alessandro
 */
@RestController
public class CurrencyConversionController {
    
    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversionBean convertCurrency(@PathVariable String from, 
            @PathVariable String to, 
            @PathVariable BigDecimal quantity) {
        return new CurrencyConversionBean(1l, 
                from, 
                to, 
                BigDecimal.ZERO, 
                quantity, 
                BigDecimal.TEN);
    }
    
}
