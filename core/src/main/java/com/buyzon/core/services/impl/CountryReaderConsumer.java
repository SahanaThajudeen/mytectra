package com.buyzon.core.services.impl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component(service = CountryReaderConsumer.class)
public class CountryReaderConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(CountryReaderConsumer.class);
    @Reference(service = CountryReaderService.class, cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    private volatile List<CountryReaderService> services;

    public List<String> getAllConfigDetails() {
        List<String> details = new ArrayList<>();
        for (CountryReaderService service : services) {
            details.add(service.getDetails());
        }
        return details;
    }
}

