package com.buyzon.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;

import org.osgi.service.component.annotations.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
    service = WorkflowProcess.class,
    property = {
        "process.label=Image Metadata Extractor"
    }
)
public class ImageMetadataExtractorProcess implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(ImageMetadataExtractorProcess.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaData) throws WorkflowException {
        try {
            String payloadPath = workItem.getWorkflowData().getPayload().toString();
            LOG.info("🔍 Processing asset at path: {}", payloadPath);

            ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);
            if (resolver == null) {
                LOG.info("❌ Could not adapt WorkflowSession to ResourceResolver");
                return;
            }

            Resource assetResource = resolver.getResource(payloadPath);
            if (assetResource == null) {
                LOG.info("⚠️ Asset resource not found at path: {}", payloadPath);
                return;
            }

            Resource original = assetResource.getChild("jcr:content/metadata");
            if (original == null) {
                LOG.info("⚠️ Metadata node not found under asset");
                return;
            }

            ValueMap metadata = original.getValueMap();
            String title = metadata.get("dc:title", "");
            String creator = metadata.get("dc:creator", "");

            LOG.info("🎨 Asset Title: {}", title.isEmpty() ? "N/A" : title);
            LOG.info("🧑‍🎨 Asset Creator: {}", creator.isEmpty() ? "N/A" : creator);

        } catch (Exception e) {
            LOG.error("📛 Failed to process asset metadata", e);
        }
    }
}
