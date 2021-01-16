package pl.solventive.LocalFarmer.LocalFarmerApi.util;

public class ValidationError {

    int errorsCount;
    String message;
    Iterable<String> errorMessages;

    public ValidationError(int errorsCount, String message, Iterable<String> errorMessages) {
        this.errorsCount = errorsCount;
        this.message = message;
        this.errorMessages = errorMessages;
    }

    public int getErrorsCount() {
        return errorsCount;
    }

    public void setErrorsCount(int errorsCount) {
        this.errorsCount = errorsCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Iterable<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(Iterable<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
