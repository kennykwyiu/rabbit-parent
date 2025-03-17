package com.kenny.rabbit.producer.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.kenny.rabbit.producer.*"})
public class RabbitProducerAutoConfiguration {
}
