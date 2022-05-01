package io.devmint.finance.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trade implements Serializable {
    TradeOperation tradeOperation;
    Instant timestamp;
    String origin;
}
