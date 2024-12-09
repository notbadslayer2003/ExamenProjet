package com.stuttgartspeed.backend.adapter.in.web;

public class OperationNotAllowedException extends RuntimeException
{
    public OperationNotAllowedException(String message)
    {
        super(message);
    }
}
