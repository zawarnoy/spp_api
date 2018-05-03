package spp.lab.http.exception;

public class SubscriptionDelayException extends ExceptionWithJson {

    @Override
    public String getJsonError() {
        return " { error : subscription ended }";
    }
}
