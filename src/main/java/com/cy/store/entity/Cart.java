package com.cy.store.entity;

import lombok.Data;

import java.io.Serializable;
/** 购物车的实体类 */
@Data
public class Cart extends BaseEntity implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
}
