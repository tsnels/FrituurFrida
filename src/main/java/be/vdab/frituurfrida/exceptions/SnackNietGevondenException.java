package be.vdab.frituurfrida.exceptions;

public class SnackNietGevondenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SnackNietGevondenException(String message) {
        super(message);
    }

    public SnackNietGevondenException() {

    }
}