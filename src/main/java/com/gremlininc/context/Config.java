package com.gremlininc.context;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Config {

    enum FailureType {
        DELAY,
        EXCEPTION
    }

    enum Type {
        SOAP_ACTION,
        ENDPOINT_REQUEST,
        SATELLITE,
        ALWAYS // Will inject failure into every request
    }

    private FailureType failureType;
    private Type type;
    private String key;
    private Object value;
    private Class<? extends Exception> exceptionType;
    private int delay;

    /*
     * Utility method for creating an exception failure with a type of SOAP_ACTION
     * Since I could create 6 differnt methods but will just use this one as an example
     */
    public static Config createSoapActionExceptionConfig(String key, String value, Class<? extends Exception> exceptionType) {
        return Config.builder()
                .failureType(FailureType.EXCEPTION)
                .type(Type.SOAP_ACTION)
                .key(key)
                .value(value)
                .exceptionType(exceptionType)
                .build();

    }

    /*
     * Utitlity method for creating a config for an exception failure. Allows you to specify the exception thrown.
     */
    public static Config createExceptionConfig(Type type, String key, String value, Class<? extends Exception> exceptionType) {
        return Config.builder()
                .failureType(FailureType.EXCEPTION)
                .type(type)
                .key(key)
                .value(value)
                .exceptionType(exceptionType)
                .build();
    }

    /*
     * Utitlity method for creating a config for an exception failure. Uses a default Exception
     */
    public static Config createGenericExceptionConfig(Type type, String key, String value) {
        return Config.builder()
                .failureType(FailureType.EXCEPTION)
                .type(type)
                .key(key)
                .value(value)
                .build();
    }

    /*
     * Utility for creating a delay failure config
     */
    public static Config createDelayConfig(Type type, String key, String value, int delay) {
        return Config.builder()
                .failureType(FailureType.DELAY)
                .type(type)
                .key(key)
                .value(value)
                .delay(delay)
                .build();
    }

}