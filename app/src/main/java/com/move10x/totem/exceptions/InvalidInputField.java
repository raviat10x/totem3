package com.move10x.totem.exceptions;

/**
 * Created by Ravi on 10/26/2015.
 */
public class InvalidInputField extends Exception {

    public InvalidInputField()
    {
    }

    public InvalidInputField(String message)
    {
        super(message);
    }

    public InvalidInputField(Throwable cause)
    {
        super(cause);
    }

    public InvalidInputField(String message, Throwable cause)
    {
        super(message, cause);
    }
}
