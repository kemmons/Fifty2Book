package com.emmons.kelsey.umkc.fifty2book;

/**
 * Created by KE034178 on 4/9/2016.
 */
public class InvalidInputError extends Exception{
    public InvalidInputError()
    {
    }

    public InvalidInputError(String message)
    {
        super(message);
    }

    public InvalidInputError(Throwable cause)
    {
        super(cause);
    }

    public InvalidInputError(String message, Throwable cause)
    {
        super(message, cause);
    }
    public InvalidInputError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause);
    }
}
