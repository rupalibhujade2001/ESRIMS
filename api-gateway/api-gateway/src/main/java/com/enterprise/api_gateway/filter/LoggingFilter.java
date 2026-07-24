package com.enterprise.api_gateway.filter;

//ppackage com.enterprise.api_gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private static final Logger logger =
            LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        long startTime = System.currentTimeMillis();

        logger.info("Incoming Request: {} {}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getURI());

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    long executionTime =
                            System.currentTimeMillis() - startTime;

                    logger.info("Response Status: {}",
                            exchange.getResponse().getStatusCode());

                    logger.info("Execution Time: {} ms",
                            executionTime);
                }));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}