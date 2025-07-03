package com.buyzon.core.workflow;

import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import com.day.cq.workflow.WorkflowSession;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
    service = WorkflowProcess.class,
    property = {
        "process.label=Custom Email Notification Process"
    }
)
public class EmailNotificationProcess implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(EmailNotificationProcess.class);
    
    @Reference
    private MessageGatewayService messageGatewayService;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaData) {
        try {
            LOG.info("Starting Custom Email Notification Workflow...");

            HtmlEmail email = new HtmlEmail();
            email.setFrom("notifications@buyzon.com"); // This shows as sender
            email.addTo("sahana2saha@gmail.com");  // Fixed recipient for now
            email.setSubject("Hello from AEM 💌");
            email.setHtmlMsg("<h2>Hi Jaanu 🥰</h2><p>This is your test email from AEM workflow!<br/>Love you always 💕</p>");

            MessageGateway<Email> gateway = messageGatewayService.getGateway(HtmlEmail.class);
            if (gateway != null) {
                gateway.send((Email) email);
                LOG.info("✅ Email sent to sahana2saha@gmail.com");
            } else {
                LOG.error("❌ MessageGateway not available");
            }

        } catch (EmailException e) {
            LOG.error("📛 Error sending email", e);
        }
        
    }
}
