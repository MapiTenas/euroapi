package com.svalero.euroapi.exception;

public class EditionNotFoundException extends Exception {

    public EditionNotFoundException(){
        super();
    }

    public EditionNotFoundException(String message) {super(message);}

    public EditionNotFoundException(long id) { super("The edition " + id + " doesn't exist"); }

}
