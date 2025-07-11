package com.buyzon.core.servlets;

import com.buyzon.core.services.GreetingService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(service= Servlet.class,
        property = {
        "sling.servlet.methods=GET",
        "sling.servlet.resourceTypes=buyzon/components/teammembers",
        "sling.servlet.extensions=json"
})
public class GreetingResourceTypeServlet extends SlingAllMethodsServlet {

    @Reference(target = "(type=hindi)")
    private GreetingService greetingService;

    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException{
        res.setContentType("application/json");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message",greetingService.getGreeting());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        res.getWriter().write(jsonObject.toString());

    }

}
