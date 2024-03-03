package com.svalero.euroapi.exception;

public class CountryNotFoundException extends Exception{

    public CountryNotFoundException(){
        super();
    }

    public CountryNotFoundException(String message) {super(message);}

    public CountryNotFoundException(long id) { super("The country " + id + " doesn't exist"); }


}
