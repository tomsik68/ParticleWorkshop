package sk.tomsik68.particleworkshop.commands.error;

public class InvalidArgumentException extends Exception {
    private static final long serialVersionUID = -3782344860639035986L;
    private final String arg;

    public InvalidArgumentException(String arg) {
        this.arg = arg;
    }

    public String getInvalidValue() {
        return arg;
    }
}
