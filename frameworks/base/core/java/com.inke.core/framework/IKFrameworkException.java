package com.inke.core.framework;

public class IKFrameworkException extends RuntimeException {
    public IKFrameworkException(String msg) {
        super(msg);
    }

    public IKFrameworkException(String msg, Throwable e) {
        super(msg, e);
    }
}
