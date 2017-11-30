package com.springboot.service;

import com.springboot.domain.City;

import java.util.List;

/**
 * Created by lailai on 2017/11/30.
 */
public interface CityService {

    Long saveCity(City city);

    List<City> searchCity(Integer pageNumber,Integer pageSize,String searchContent);
}
