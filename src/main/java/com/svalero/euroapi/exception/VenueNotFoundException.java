package com.svalero.euroapi.exception;

public class VenueNotFoundException extends Exception{

    public VenueNotFoundException(){
        super();
    }

    public VenueNotFoundException(String message) {super(message);}

    public VenueNotFoundException(long id) { super("The venue " + id + " doesn't exist"); }

}
