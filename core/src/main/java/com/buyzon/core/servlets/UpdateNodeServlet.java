package com.buyzon.core.servlets;

import com.day.cq.commons.jcr.JcrConstants;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.framework.Constants;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Reference;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Simple Node Update Servlet",
                "sling.servlet.methods=GET",
                "sling.servlet.paths=/bin/updateNode"
        })
public class UpdateNodeServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        ResourceResolver serviceResolver = null;
        try {
            // Login with system user
            serviceResolver = resolverFactory.getServiceResourceResolver(
                    java.util.Collections.singletonMap(
                            ResourceResolverFactory.SUBSERVICE, "node-writer-service")
            );

            String path = request.getParameter("path"); // Path to node
            String propertyName = request.getParameter("property");
            String propertyValue = request.getParameter("value");

            if (path == null || propertyName == null || propertyValue == null) {
                response.setStatus(SlingHttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing parameters!");
                return;
            }

            Resource resource = serviceResolver.getResource(path);
            if (resource != null) {
                ModifiableValueMap properties = resource.adaptTo(ModifiableValueMap.class);
                if (properties != null) {
                    properties.put(propertyName, propertyValue);
                    serviceResolver.commit();
                    response.getWriter().write("Node updated successfully!");
                } else {
                    response.setStatus(SlingHttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Could not adapt to ModifiableValueMap");
                }
            } else {
                response.setStatus(SlingHttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Resource not found!");
            }
        } catch (LoginException e) {
            throw new ServletException("LoginException: ", e);
        } finally {
            if (serviceResolver != null && serviceResolver.isLive()) {
                serviceResolver.close();
            }
        }
    }
}
