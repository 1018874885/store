package com.cy.store.controller.ex;

/** 文件上传异常类的基类 */
/** 因为controller先接收到用户上传的文件，当检测到异常时无需继续把文件传递到service层进行处理，而是自己抛出异常即可 */
public class FileUploadException extends RuntimeException{
    public FileUploadException() {
        super();
    }

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadException(Throwable cause) {
        super(cause);
    }

    protected FileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
