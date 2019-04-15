package com.gremlininc.failure;

import org.junit.Test;

import java.io.IOException;

public class ExceptionFailureTest {

    @Test(expected = Exception.class)
    public void applyTestDefault() throws Exception {
        ExceptionFailure failure = new ExceptionFailure();
        failure.apply();
    }

    @Test(expected = NullPointerException.class)
    public void applyTestNullPointer() throws Exception {
        ExceptionFailure failure = new ExceptionFailure(NullPointerException.class);
            failure.apply();
    }

    @Test(expected = IOException.class)
    public void applyIOException() throws Exception {
        ExceptionFailure failure = new ExceptionFailure(IOException.class);
            failure.apply();
    }

    @Test(expected = IllegalAccessException.class)
    public void applyIllegalAccessException() throws Exception {
        ExceptionFailure failure = new ExceptionFailure(IllegalAccessException.class);
        failure.apply();
    }


    @Test(expected = InstantiationException.class)
    public void applyInstantiationException() throws Exception {
        ExceptionFailure failure = new ExceptionFailure(InstantiationException.class);
        failure.apply();
    }

}
