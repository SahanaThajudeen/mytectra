package com.buyzon.core.services;


import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;

public interface PageList {
    List<Page> getChildPages(Resource resource);
    Resource getResourceFromPath(String path, ResourceResolver resolver);
}
