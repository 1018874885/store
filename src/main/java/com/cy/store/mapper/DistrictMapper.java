package com.cy.store.mapper;

import com.cy.store.entity.District;
import org.springframework.stereotype.Repository;

import java.util.List;

/** 省市区的接口 因为可复用程度较高，因此单独拿出来使用 */
@Repository
public interface DistrictMapper {
    /**
     * 根据父代号查询某个区域下所有的区域列表
     * @param parent  父区域代号
     * @return  子区域的列表
     */
    List<District> findByParent(String parent);

    /**
     * 根据省市区的code获取对应区域的名称
     * @param code   区域code
     * @return  区域名称 name
     */
    String getNameByCode(String code);
}
