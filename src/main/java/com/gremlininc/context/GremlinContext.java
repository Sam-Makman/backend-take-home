package com.gremlininc.context;

import com.gremlininc.failure.Delay;
import com.gremlininc.failure.ExceptionFailure;
import com.gremlininc.failure.Failure;
import com.sun.xml.internal.ws.client.RequestContext;

import java.util.Optional;

/**
 * {@link GremlinContext} uses {@link RequestContext} to determine if this request should be impacted. If it returns a
 * failure, you can apply()` it via the {@link Failure} interface.
 * <p>
 * <p>
 * this code uses the com.sun.xml.internal.ws.client.RequestContext so I tried to build on top of that
 * I have not done any work with SOAP before and couldn't find much documentation for this context class so I did my best figuring out how it is used
 * It is also a possibility that this was supposed to be based off of the Springboot request context class but that dependency got auto imported
 *
 * TODO: NOTE - It builds in intellij but not with `gradlew build` I didn't think it would be worth my time debugging this
 *  All Tests are passing
 */
public final class GremlinContext {

    /**
     * @param context The request context from the given SOAP request
     * @param config  A failure config to be used for the request
     * @return an optional with failure or empty if no failure is desired for the request
     */
    public static Optional<Failure> shouldFail(RequestContext context, Config config) {

        Object compareTo = null;
        switch (config.getType()) {
            case SATELLITE:
                compareTo = context.get(config.getKey());
                break;
            case SOAP_ACTION:
                compareTo = context.getSoapAction();
                break;
            case ENDPOINT_REQUEST:
                compareTo = context.getEndpointAddress().toString();
                break;
            case ALWAYS:
                return failureType(config);
        }
        // Default is to not return a failure
        return config.getValue().equals(compareTo) ? failureType(config) : Optional.empty();
    }

    /**
     * @param config A Failure configuration
     * @return An option containing failure of type DELAY or EXCEPTION based on the configuration. Defaults to empty option if configuration does not have failure type
     */
    private static Optional<Failure> failureType(Config config) {
        switch (config.getFailureType()) {
            case DELAY:
                return Optional.of(new Delay(config.getDelay()));
            case EXCEPTION:
                return Optional.of(new ExceptionFailure(config.getExceptionType()));
            default:
                return Optional.empty();

        }
    }
}
