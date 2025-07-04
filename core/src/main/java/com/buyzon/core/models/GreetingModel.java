package com.buyzon.core.models;


import com.buyzon.core.services.GreetingService;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = org.apache.sling.api.resource.Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GreetingModel {

    @OSGiService(filter = "(type=english)")
    private GreetingService greetingService;

    public String getMessage() {
        return greetingService != null ? greetingService.getGreeting() : "No Greeting Available";
    }
}
