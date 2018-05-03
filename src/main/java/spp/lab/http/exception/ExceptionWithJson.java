package spp.lab.http.exception;

public abstract class ExceptionWithJson extends Exception {

    public abstract String getJsonError();

}
