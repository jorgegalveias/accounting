package io.devmint.finance.trade.accounting.dao;

import io.devmint.finance.trade.accounting.AppEnvVars;
import io.devmint.finance.trade.accounting.model.Account;
import io.devmint.finance.trade.accounting.model.Asset;
import io.devmint.finance.trade.accounting.model.Trade;
import io.devmint.finance.trade.accounting.validation.generic.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPooled;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AccountDAO {

    public static final String ACCOUNTS_KEY = "accounts";
    public static final String ID = "id";
    public static final String CURRENCY = "currency";

    public static final String BROKER = "broker";
    public static final String PORTFOLIO = "portfolio";
    public static final String ACTIVE_TRADES = "activeTrades";
    public static final String CLOSED_TRADES = "closedTrades";

    private final String redisHost;
    private final Integer redisPort;
    private final JedisPooled jedis;

    public static final Logger LOGGER = LoggerFactory.getLogger(AccountDAO.class);


    public AccountDAO(){

        this.redisHost = System.getenv().getOrDefault(AppEnvVars.REDIS_HOST,"127.0.0.1");

        this.redisPort = Integer.valueOf(System.getenv().getOrDefault(AppEnvVars.REDIS_PORT,"6379"));

        this.jedis = new JedisPooled(this.redisHost, this.redisPort);

        LOGGER.info("Pointing to redis on {}:{}",this.redisHost,this.redisPort);
    }

    public final void write(Account account){
        Map<String,String> newAccount = new HashMap<String, String>();
        newAccount.put(BROKER,account.getBroker());
        newAccount.put(CURRENCY,account.getCurrency());
        newAccount.put(ID,account.getId());
        newAccount.put(PORTFOLIO,"");
        newAccount.put(ACTIVE_TRADES,"");
        newAccount.put(CLOSED_TRADES,"");

        this.jedis.hmset(getHashKey(account.getBroker(),account.getId()),newAccount);
        return;
    }

    public final Account read(String broker, String id){

        Map<String, String> accountRaw = this.jedis.hgetAll(getHashKey(broker, id));

        return Account.newBuilder()
                .setBroker(accountRaw.get(BROKER))
                .setCurrency(accountRaw.get(CURRENCY))
                .setId(accountRaw.get(ID))
                .addPortfolio(Asset.newBuilder().build())
                .addActiveTrades(Trade.newBuilder().build())
                .addClosedTrades(Trade.newBuilder().build())
                .build();
    }

    public String getHashKey(String broker,String id){
        return String.format("%s:%s:%s",ACCOUNTS_KEY,broker,id);
    }


}
