package com.cy.store.entity;

import lombok.Data;

import java.io.Serializable;

/** 省/市/区实体类 */
@Data
public class District implements Serializable {
    private Integer id;
    private String parent;
    private String code;
    private String name;
}
