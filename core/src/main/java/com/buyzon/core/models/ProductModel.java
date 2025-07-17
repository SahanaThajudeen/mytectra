package com.buyzon.core.models;

import com.buyzon.core.services.ProductDataService;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import javax.annotation.PostConstruct;
import java.util.List;

@Model(adaptables = org.apache.sling.api.resource.Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductModel {

    @OSGiService
    private ProductDataService productDataService;

    private List<ProductItem> products;

    @PostConstruct
    protected void init() {
        products = productDataService.getProductList();
    }

    public List<ProductItem> getProducts() {
        return products;
    }
}
