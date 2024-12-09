package com.stuttgartspeed.backend.web;

public class OperationNotAllowedException extends RuntimeException
{
    public OperationNotAllowedException(String message)
    {
        super(message);
    }
}
