package com.springboot.repository;

import com.springboot.domain.City;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by lailai on 2017/11/30.
 */
public interface CityRepository extends ElasticsearchRepository<City,Long>{

    @Override
    Iterable<City> search(QueryBuilder query);

    @Override
    Page<City> search(QueryBuilder query, Pageable pageable);

    @Override
    Page<City> search(SearchQuery searchQuery);

    @Override
    Page<City> searchSimilar(City entity, String[] fields, Pageable pageable);
}
