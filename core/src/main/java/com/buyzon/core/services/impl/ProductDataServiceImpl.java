package com.buyzon.core.services.impl;

import com.buyzon.core.models.ProductItem;
import com.buyzon.core.services.ProductDataService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.osgi.service.component.annotations.Component;

import java.util.List;

@Component(service = ProductDataService.class)
public class ProductDataServiceImpl implements ProductDataService {

    private static final String JSON = "[{\"id\":\"101\",\"name\":\"Product A\",\"price\":150}," +
            "{\"id\":\"102\",\"name\":\"Product B\",\"price\":200}]";

    @Override
    public List<ProductItem> getProductList() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(JSON, new TypeReference<List<ProductItem>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
