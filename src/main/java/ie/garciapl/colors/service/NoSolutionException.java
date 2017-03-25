package ie.garciapl.colors.service;

public class NoSolutionException extends RuntimeException {

    public NoSolutionException() {
        super("No solution exists", null, false, false);
    }
}