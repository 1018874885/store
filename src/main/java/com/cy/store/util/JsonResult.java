package com.cy.store.util;

import java.io.Serializable;

/**
 * Json格式的数据进行响应
 */
public class JsonResult<E> implements Serializable {
    /** 状态码 */
    private Integer state;
    /** 描述信息 */
    private String message;
    /** 数据  不确定数据类型时可以用泛型 */
    private E data;

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    //捕获异常时返回的数据信息
    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public String getMessage() {
        return message;
    }

    public E getData() {
        return data;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
