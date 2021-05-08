package com.example.game.UserOperation;

import android.content.Context;

public class Authentication {

    private String username;
    private String password;
    private UserParser userParser;

    public Authentication(Context context, String username, String password){
        userParser = new UserParser(context);
        this.username = username.trim();
        this.password = password.trim();
    }

    /**
     * Authenticate if the inputted information matches any of the stored user record
     * @throws AuthenticationException throws when the user/password is incorrect or the
     * required entered field is empty
     */
    public void authenticate() throws AuthenticationException{

        if (username.isEmpty() || password.isEmpty()){
            throw new AuthenticationException("Username or password is empty!");
        }

        AuthenticationException authenticationException =
                new AuthenticationException("Username or password does not match!");

        try{
            UserObject userObject = userParser.parseSingle(username);

            if (!userObject.getPassword().equals(password)){
                throw authenticationException;
            }
            return;

        } catch (ParserException ex){
            ex.printStackTrace();
        }

        throw authenticationException;
    }

    /**
     * Check if the user exists
     * @return true if the user exists and vice versa
     */
    public boolean doesUserExist(){
        try{
            UserObject userObject = userParser.parseSingle(username);
        }catch(ParserException ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }



}
