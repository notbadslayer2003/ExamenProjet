package com.stuttgartspeed.backend.web;

public class InvalidRequestException extends RuntimeException
{
    public InvalidRequestException(String message)
    {
        super(message);
    }
}
