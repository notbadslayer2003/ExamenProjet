package com.stuttgartspeed.backend.adapter.in.web;

public class DuplicateResourceException extends RuntimeException
{
    public DuplicateResourceException(String message)
    {
        super(message);
    }
}
