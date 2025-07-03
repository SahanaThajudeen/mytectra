package com.buyzon.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name="Country Site Configuration",description = "factory config for country specific setting")
public @interface CountryConfig {
    @AttributeDefinition(name = "Country Code", description = "e.g., US, UK, IN")
    String countryCode();

    @AttributeDefinition(name = "Site Name")
    String siteName();

    @AttributeDefinition(name = "Is Active")
    boolean isActive() default true;
}
