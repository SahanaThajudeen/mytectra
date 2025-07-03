package com.buyzon.core.models;

import com.buyzon.core.services.PageList;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PageListModel {

    @ValueMapValue
    private String parentPath;

    @OSGiService
    private PageList pageList;

    @Self
    private Resource currentResource;

    private List<Page> childPages;

    @PostConstruct
    protected void init() {
        if (parentPath != null && !parentPath.isEmpty()) {
            Resource parentResource = pageList.getResourceFromPath(parentPath, currentResource.getResourceResolver());
            childPages = pageList.getChildPages(parentResource);
        } else {
            childPages = Collections.emptyList();
        }
    }

    public List<Page> getChildPages() {
        return childPages;
    }
}
