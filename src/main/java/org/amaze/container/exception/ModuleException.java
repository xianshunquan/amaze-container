package org.amaze.container.exception;

/**
 * @author : shunqxian
 * @date : 2019-10-05
 */
public class ModuleException extends RuntimeException{

    public ModuleException() {
        super();
    }

    public ModuleException(String message) {
        super(message);
    }

    public ModuleException(String message, Throwable cause) {
        super(message, cause);
    }


    public ModuleException(Throwable cause) {
        super(cause);
    }

    protected ModuleException(String message, Throwable cause,
                              boolean enableSuppression,
                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
