package io.devmint.finance.trade.accounting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    
    public static void main( String[] args )
    {
        LOGGER.error("hello");
        System.out.println( POJO.builder().id(1).name("Name").build());
    }
}
