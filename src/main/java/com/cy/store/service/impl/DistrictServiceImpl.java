package com.cy.store.service.impl;

import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> findByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        /**
         * 网络传输中，为了尽量避免无效数据的传输，可以将无效数据设置为Null 这样可以节省流量，提高效率
         */
        for (District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.getNameByCode(code);
    }
}
