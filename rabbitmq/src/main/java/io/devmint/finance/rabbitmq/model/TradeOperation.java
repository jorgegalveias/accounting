package io.devmint.finance.rabbitmq.model;

public enum TradeOperation {
    NEW_TRADE("new_trade"),
    UPDATE_TRADE("update_trade");

    private String exchange;

    TradeOperation(String exchange){
        this.exchange = exchange;
    }

    public String getExchange(){
        return this.exchange;
    }
}
