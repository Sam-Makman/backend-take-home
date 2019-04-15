package com.gremlininc.failure;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * An implementation of {@link Failure} which causes an Exception to be thrown
 *
 * @author Sam Makman
 */
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionFailure implements Failure {

    private Class<? extends Exception> exceptionClass;

    /**
     *
     * @throws Exception Throws an exception basedo n the configured exceptionClass or defualts to Exception
     */
    @Override
    public void apply() throws Exception {
        if (exceptionClass == null) {
            throw new Exception();
        }

        /*
         * When creating a new instance of the desired exception class an IllegalAccessException or InstantiationException may occur
         * I made the decision that on an IllegalAccessException or an InstantiationException I would just print the stacktrace but not cause a failure.
         * This could cause an issue if one of those exceptions were desired by the user however I feel it is more important to let the user know their configuration
         * is breaking the failure library.
         *
         */

        /*
         * Class.newInstance() throws IllegalAccessException and InstantiationException
         * We need to check if these are the desired exceptions before preceding to the try catch block
         * Alternatively we could remove the try catch block but I would rather not have a failure of an unexpected type as
         * I believe that would be mroe difficult for the user to debug
         */
        if(exceptionClass.equals(IllegalAccessException.class) || exceptionClass.equals(InstantiationException.class) ){
            throw exceptionClass.newInstance();
        }

        try {
            throw exceptionClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            // In case of error throwing exception do not cause failure
            e.printStackTrace();
        }
    }
}
