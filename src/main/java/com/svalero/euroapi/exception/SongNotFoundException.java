package com.svalero.euroapi.exception;

public class SongNotFoundException  extends  Exception {

    public SongNotFoundException(){
        super();
    }

    public SongNotFoundException(String message) {super(message);}

    public SongNotFoundException(long id) { super("The song " + id + " doesn't exist"); }

}
