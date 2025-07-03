package com.buyzon.core.workflow;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
    service = WorkflowProcess.class,
    property = {
        "process.label=🧪 Test Hello Workflow Step"
    }
)
public class HelloWorkflowStep implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(HelloWorkflowStep.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        LOG.error("✅✅✅ HelloWorkflowStep triggered! Payload: {}", workItem.getWorkflowData().getPayload().toString());
    }
}
