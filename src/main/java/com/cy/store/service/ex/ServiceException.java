package com.cy.store.service.ex;

/** 业务层异常的基类  throws new ServiceException("产生了未知错误")
 *  根据业务层不同的功能来详细定义具体的异常类型 ，统一的去继承ServiceException异常类*/
public class ServiceException extends RuntimeException{
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
