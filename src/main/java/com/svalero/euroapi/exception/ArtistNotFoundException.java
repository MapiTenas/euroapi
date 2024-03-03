package com.svalero.euroapi.exception;

public class ArtistNotFoundException extends Exception{

    public ArtistNotFoundException(){
        super();
    }

    public ArtistNotFoundException(String message) {super(message);}

    public ArtistNotFoundException(long id) { super("The artist " + id + " doesn't exist"); }
}
