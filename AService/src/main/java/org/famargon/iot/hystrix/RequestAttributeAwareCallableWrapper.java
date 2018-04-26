package org.famargon.iot.hystrix;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import io.jmnarloch.spring.boot.hystrix.context.HystrixCallableWrapper;

/**
 * Para que se reenvie el token oauth a las llamadas a sucesivos servicios
 */
@Component
public class RequestAttributeAwareCallableWrapper implements HystrixCallableWrapper {
 
    @Override
    public <T> Callable<T> wrapCallable(Callable<T> callable) {
        return new RequestAttributeAwareCallable<>(callable, RequestContextHolder.currentRequestAttributes());
    }
 
    private static class RequestAttributeAwareCallable<T> implements Callable<T> {
 
        private final Callable<T> callable;
        private final RequestAttributes requestAttributes;
 
        public RequestAttributeAwareCallable(Callable<T> callable, RequestAttributes requestAttributes) {
            this.callable = callable;
            this.requestAttributes = requestAttributes;
        }
 
        @Override
        public T call() throws Exception {
 
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                return callable.call();
            } finally {
                RequestContextHolder.resetRequestAttributes();
            }
        }
    }
}
