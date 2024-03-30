package edu.java.scrapper.configuration;

import java.util.List;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public class CustomRetryPolicy extends SimpleRetryPolicy {

    private final List<Integer> retryableStatuses;

    public CustomRetryPolicy(List<Integer> retryableStatuses) {
        this.retryableStatuses = retryableStatuses;
    }

    @Override
    public boolean canRetry(RetryContext context) {
        var t = context.getLastThrowable();
        return (t == null || this.retryForException(t)) && context.getRetryCount() < this.getMaxAttempts();
    }

    private boolean retryForException(Throwable throwable) {
        if (throwable instanceof HttpClientErrorException clientErrorException) {
            var statusCode = clientErrorException.getStatusCode();
            return retryableStatuses.contains(statusCode.value());
        } else if (throwable instanceof HttpServerErrorException serverErrorException) {
            var statusCode = serverErrorException.getStatusCode();
            return retryableStatuses.contains(statusCode.value());
        }
        return false;
    }
}

