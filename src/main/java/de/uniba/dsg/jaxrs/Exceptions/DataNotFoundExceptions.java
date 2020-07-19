package de.uniba.dsg.jaxrs.Exceptions;

public class DataNotFoundExceptions extends RuntimeException{

    private static final long serialVersionUID = -5693750015640964236L;

    public DataNotFoundExceptions(String message) {
        super(message);
    }
}
