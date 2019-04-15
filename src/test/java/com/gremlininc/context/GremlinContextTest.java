package com.gremlininc.context;

import com.gremlininc.failure.ExceptionFailure;
import com.gremlininc.failure.Failure;
import com.sun.xml.internal.ws.api.EndpointAddress;
import com.sun.xml.internal.ws.client.RequestContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Optional;

import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RequestContext.class)
public class GremlinContextTest {

    @Test
    public void testGremlinContext() throws Exception {
        RequestContext context = PowerMockito.mock(RequestContext.class);

        when(context.getEndpointAddress()).thenReturn(new EndpointAddress("hello"));
        Config config = Config.createExceptionConfig(Config.Type.ENDPOINT_REQUEST, "test", "hello", IndexOutOfBoundsException.class);

        Optional<Failure> fail = GremlinContext.shouldFail(context, config);
        assert fail.get() instanceof ExceptionFailure;

        try {
            fail.get().apply();
        } catch (IndexOutOfBoundsException e) {
            // Catch IndexOutOfBoundsException as we are expecting it to be thrown.
            // Any other exception will cause failure
        }

    }

    @Test
    public void testGremlinContextSatellite() throws Exception {
        RequestContext context = PowerMockito.mock(RequestContext.class);

        when(context.get("test")).thenReturn("hello");
        Config config = Config.createExceptionConfig(Config.Type.SATELLITE, "test", "hello", SQLException.class);

        Optional<Failure> fail = GremlinContext.shouldFail(context, config);
        assert fail.get() instanceof ExceptionFailure;

        try {
            fail.get().apply();
        } catch (SQLException e) {
            // Catch IndexOutOfBoundsException as we are expecting it to be thrown.
            // Any other exception will cause failure
        }
    }


    @Test
    public void testGremlinContextSoapAction() throws Exception {
        RequestContext context = PowerMockito.mock(RequestContext.class);

        when(context.getSoapAction()).thenReturn("hello");
        Config config = Config.createExceptionConfig(Config.Type.SOAP_ACTION, "test", "hello", NegativeArraySizeException.class);

        Optional<Failure> fail = GremlinContext.shouldFail(context, config);
        assert fail.get() instanceof ExceptionFailure;

        try {
            fail.get().apply();
        } catch (NegativeArraySizeException e) {
            // Catch IndexOutOfBoundsException as we are expecting it to be thrown.
            // Any other exception will cause failure
        }
    }

}
