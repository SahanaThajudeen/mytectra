package com.buyzon.core.services.impl;

import com.buyzon.core.services.GreetingService;
import org.osgi.service.component.annotations.Component;

@Component(
        service = GreetingService.class,
        property = {
                "type=hindi"
        }
)
public class HindiGreetingService implements GreetingService {
    @Override
    public String getGreeting() {
        return "Namaste!";
    }
}