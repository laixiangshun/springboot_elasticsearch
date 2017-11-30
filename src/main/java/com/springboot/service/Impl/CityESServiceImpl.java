package com.springboot.service.Impl;

import com.springboot.domain.City;
import com.springboot.repository.CityRepository;
import com.springboot.service.CityService;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lailai on 2017/11/30.
 */
@Service
public class CityESServiceImpl implements CityService {

    private static final Logger logger= LoggerFactory.getLogger(CityESServiceImpl.class);

    //分页参数
    Integer PAGE_SIZE=10;//每页数量
    Integer DFAULT_PAGE_NUMBER=0;//默认当前页码
    /*搜索模式*/
    String SCORE_MODE_SUM="sum";//权重分求和模式
    Float MIN_SCORE=10.0F;//由于无相关性的分值默认为1，设置权重分最小值为10

    @Autowired
    private CityRepository cityRepository;
    @Override
    public Long saveCity(City city) {
        City cityResult=cityRepository.save(city);
        return cityResult.getId();
    }

    @Override
    public List<City> searchCity(Integer pageNumber, Integer pageSize, String searchContent) {
        if(pageSize==null || pageSize<=0){
            pageSize=PAGE_SIZE;
        }
        if(pageNumber==null || pageNumber<DFAULT_PAGE_NUMBER){
            pageNumber=DFAULT_PAGE_NUMBER;
        }
        logger.info("\n searchCity: searchContent["+searchContent+"]\n");
        SearchQuery searchQuery=getCitySearchQuery(pageNumber,pageSize,searchContent);
        logger.info("\n searchCity: searchContent["+searchContent+"]\n DSL=\n"+searchQuery.getQuery().toString());
        Page<City> cityPage=cityRepository.search(searchQuery);
        return cityPage.getContent();
    }

    /**
     * 根据搜索词构建搜索查询语句
     * 代码流程：
     *  权重分查询
     *  短语匹配
     *  设置权重分最小值
     *  设置分页参数
     * @param pageNumber
     * @param pageSize
     * @param searchContent
     * @return
     */
    private SearchQuery getCitySearchQuery(Integer pageNumber,Integer pageSize,String searchContent){

        //短语匹配到的搜索词，求和模式累加权重分
        FunctionScoreQueryBuilder functionScoreQueryBuilder= QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.matchPhraseQuery("name",searchContent), ScoreFunctionBuilders.weightFactorFunction(1000))
                .add(QueryBuilders.matchPhraseQuery("description",searchContent),ScoreFunctionBuilders.weightFactorFunction(500))
                .scoreMode(SCORE_MODE_SUM)
                .setMinScore(MIN_SCORE);
        //分页参数
        Pageable pageable=new PageRequest(pageNumber,pageSize);
        return new NativeSearchQueryBuilder().withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }
}
