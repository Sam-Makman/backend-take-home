package com.gremlininc.context;

import com.gremlininc.failure.Failure;
import com.sun.xml.internal.ws.client.RequestContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;


/**
 * A wrapper class around GremlinContext that manages config
 */
@AllArgsConstructor
@Getter
@Setter
public class ConfigurableGremlinContext {
    private Config config;

    public Optional<Failure> shouldFail(RequestContext requestContext){
        return GremlinContext.shouldFail(requestContext, config);
    }
}


