package com.stuttgartspeed.backend.adapter.in.web;

public class InvalidRequestException extends RuntimeException
{
    public InvalidRequestException(String message)
    {
        super(message);
    }
}
