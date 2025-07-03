package com.buyzon.core.services.impl;


import com.buyzon.core.services.PageList;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component(service= PageList.class)
public class PageListImpl implements PageList{

    @Override
    public List<Page> getChildPages(Resource resource){
        List<Page> childPages = new ArrayList<>();

        if(resource==null){
            return childPages;
        }
        ResourceResolver resolver = resource.getResourceResolver();
        PageManager pageManager = resolver.adaptTo(PageManager.class);

        if (pageManager != null) {
            Page currentPage = resource.adaptTo(Page.class);

            if (currentPage != null) {
                return getAllChildPages(currentPage);
                }
            }
        return childPages;
        }

    private List<Page> getAllChildPages(Page currentPage) {
        List<Page> childList = new ArrayList<>();
        Iterator<Page> children = currentPage.listChildren();
        while (children.hasNext()) {
            Page child = children.next();
            childList.add(child);
            childList.addAll(getAllChildPages(child));
        }
        return childList;
    }

@Override
    public Resource getResourceFromPath(String path, ResourceResolver resolver) {
        if (path != null && resolver != null) {
            return resolver.getResource(path);
        }
        return null;
    }
}
