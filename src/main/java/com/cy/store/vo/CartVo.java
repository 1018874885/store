package com.cy.store.vo;

import lombok.Data;

import java.io.Serializable;

/** Cart实体类的VO(value object)类
 * 用于解决联表查询时无法返回实体类的问题*/
@Data
public class CartVo implements Serializable {
    private Integer cid;
    private Integer uid;
    private Integer pid;
    private Long price;
    private Integer num;
    private String title;
    private Long realPrice;
    private String image;
}
