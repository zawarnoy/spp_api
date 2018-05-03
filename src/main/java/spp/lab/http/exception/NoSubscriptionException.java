package spp.lab.http.exception;

public class NoSubscriptionException extends ExceptionWithJson {
    @Override
    public String getJsonError() {
        return "{status : this user doesn't has subscription}";
    }
}
