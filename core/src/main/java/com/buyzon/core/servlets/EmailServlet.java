package com.buyzon.core.servlets;

import com.buyzon.core.workflow.EmailNotificationProcess;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;

@Component(service = Servlet.class,
        property = {"sling.servlet.paths=/bin/myEmailServlet",
                "sling.servlet.methods=GET"
        })
public class EmailServlet extends SlingAllMethodsServlet {
    private static final Logger LOG = LoggerFactory.getLogger(EmailServlet.class);

    @Reference
    private MessageGatewayService messageGatewayService;

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        try {
            LOG.info("Starting Custom Email Notification Workflow...");

            HtmlEmail email = new HtmlEmail();
            email.setFrom("sahana2saha@gmail.com"); // This shows as sender
            email.addTo("sahana2saha@gmail.com");  // Fixed recipient for now
            email.setSubject("Hello from AEM");
            email.setHtmlMsg("<h2>Hi </h2><p>This is your test email from AEM workflow!<br/></p>");

            MessageGateway<Email> gateway = messageGatewayService.getGateway(HtmlEmail.class);
            if (gateway != null) {
                gateway.send((Email) email);
                LOG.info("Email sent to sahana2saha@gmail.com");
            } else {
                LOG.error("MessageGateway not available");
            }

        } catch (EmailException e) {
            LOG.error("Error sending email", e);
        }
    }
}
