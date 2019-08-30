package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.CityMapper;
import com.xiupeilian.carpart.model.City;
import com.xiupeilian.carpart.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityMapper cityMapper;
    @Override
    //ָ���÷����ķ���ֵ���ᱻspring-cache cachemanager���л��棬��Ҫָ��ʹ����һ������
    @Cacheable(value = "redisCache")
    public List<City> findCityByParentId(int parentId) {
        return cityMapper.findCityByParentId(parentId);
    }

    @Override
    //��Ҫ��������ֶ�ά�����棨ɾ������գ�
    @CacheEvict(value = "redisCache",allEntries = true)
    public void deleteCityById(int id) {
        cityMapper.deleteByPrimaryKey(id);
    }
}
