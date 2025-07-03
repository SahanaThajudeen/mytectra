package com.buyzon.core.services.impl;

import com.buyzon.core.services.CountryConfig;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = CountryReaderService.class,
        configurationPolicy = ConfigurationPolicy.REQUIRE
)
@Designate(ocd = CountryConfig.class, factory = true)
public class CountryReaderService {
    private static final Logger LOG = LoggerFactory.getLogger(CountryReaderService.class);
    private String countryCode;
    private String siteName;
    private boolean isActive;

    @Activate
    protected void activate(CountryConfig config) {
        LOG.info("[Activate] CountryService started for country: {}", config.countryCode());
        loadConfig(config);
    }

    @Modified
    protected void modified(CountryConfig config) {
        LOG.info("[Modified] CountryService updated for country: {}", config.countryCode());
        loadConfig(config);
    }

    // Shared logic
    private void loadConfig(CountryConfig config) {
        this.countryCode = config.countryCode();
        this.siteName = config.siteName();
        this.isActive = config.isActive();
    }

    @Deactivate
    protected void deactivate() {
        LOG.info("Deactivated CountryService for countryCode='{}'", countryCode);
    }

    public String getDetails() {
        return "Country: " + countryCode + ", Site: " + siteName + ", Active: " + isActive;
    }

}
