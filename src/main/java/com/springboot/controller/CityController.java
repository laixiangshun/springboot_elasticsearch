package com.springboot.controller;

import com.springboot.domain.City;
import com.springboot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lailai on 2017/11/30.
 */
@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/api/city",method = RequestMethod.POST)
    public Long createCity(@RequestBody City city){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
        return cityService.saveCity(city);
    }

    @RequestMapping(value = "/api/city/search",method = RequestMethod.GET)
    public List<City> searchCity(@RequestParam(value = "pageNumber") Integer pageNumber,
                                 @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                 @RequestParam(value = "searchContent") String searchContent){
        return cityService.searchCity(pageNumber,pageSize,searchContent);
    }
}
