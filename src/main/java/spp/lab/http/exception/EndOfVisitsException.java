package spp.lab.http.exception;

public class EndOfVisitsException extends ExceptionWithJson {

    @Override
    public String getJsonError()
    {
        return "{ error : end of visits }";
    }
}
