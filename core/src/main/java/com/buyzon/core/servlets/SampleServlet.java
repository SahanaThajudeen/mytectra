package com.buyzon.core.servlets;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

@Component(service = Servlet.class,
        property = {"sling.servlet.paths=/bin/mySampleServlet",
                "sling.servlet.methods=GET"
        })
public class SampleServlet extends SlingSafeMethodsServlet {
    String path = "/content/we-retail/language-masters/en/experience";
    String paths;
    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource resource = resourceResolver.getResource(path);
        JsonArray jsonArray = new JsonArray();

        if(Objects.nonNull(resource)) {
            Iterator<Resource> resourceIterable = resource.listChildren();
            while(resourceIterable.hasNext()){
                JsonObject jsonObject = new JsonObject();
                Resource res = resourceIterable.next();
                String path = res.getPath();
                jsonObject.addProperty("path",path);
                jsonArray.add(jsonObject);
                //paths = paths.concat(","+path);
            }
        }
        response.setContentType("application/json");
        response.getWriter().print(jsonArray);
    }
}
