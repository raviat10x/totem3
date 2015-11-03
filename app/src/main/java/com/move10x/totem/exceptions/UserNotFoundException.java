package com.move10x.totem.exceptions;

/**
 * Created by Ravi on 10/9/2015.
 */
public class UserNotFoundException extends Exception {

        public UserNotFoundException()
        {
        }

        public UserNotFoundException(String message)
        {
            super(message);
        }

        public UserNotFoundException(Throwable cause)
        {
            super(cause);
        }

        public UserNotFoundException(String message, Throwable cause)
        {
            super(message, cause);
        }
}
