package edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.stereotype.Component;

@Component
public class MetricCounter {
    Counter counter = Metrics.counter("message_count");

    public void increment() {
        counter.increment();
    }
}
