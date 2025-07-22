package com.buyzon.core.workflow;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.Replicator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;

@Component(service = WorkflowProcess.class,
        property = {
                "process.label=Buyzon - Auto Publish Asset"
        })
public class AssetAutoPublisherWorkflow implements WorkflowProcess {

        private static final Logger log = LoggerFactory.getLogger(AssetAutoPublisherWorkflow.class);

        @Reference
        private Replicator replicator;

        @Override
        public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap args) {

                String payloadPath = workItem.getWorkflowData().getPayload().toString();
                log.info("### [Buyzon Workflow] Starting publish workflow for asset: {}", payloadPath);

                try {
                        ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);

                        if (resolver != null) {
                                Resource asset = resolver.getResource(payloadPath);

                                if (asset != null) {
                                        // Publish the asset
                                        replicator.replicate(resolver.adaptTo(Session.class), ReplicationActionType.ACTIVATE, payloadPath);
                                        log.info("### [Buyzon Workflow] Asset published successfully: {}", payloadPath);

                                        // Optional: mark it with metadata (can also be saved)
                                        log.info("### [Buyzon Workflow] You can add custom metadata here if needed");
                                } else {
                                        log.warn("### [Buyzon Workflow] Asset resource not found: {}", payloadPath);
                                }

                        } else {
                                log.error("### [Buyzon Workflow] Could not get ResourceResolver");
                        }

                } catch (Exception e) {
                        log.error("### [Buyzon Workflow] Failed to publish asset: ", e);
                }
        }
}
