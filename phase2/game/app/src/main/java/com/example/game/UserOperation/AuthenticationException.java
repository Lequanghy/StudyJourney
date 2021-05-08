package com.example.game.UserOperation;

class AuthenticationException extends Exception{
    /**
     * Custom Authentication Exception
     * @param message exception message
     */
    AuthenticationException(String message){
        super(message);
    }
}
