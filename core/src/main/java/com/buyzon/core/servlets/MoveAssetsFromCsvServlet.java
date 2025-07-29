package com.buyzon.core.servlets;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.*;
import java.util.Collections;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Move Assets from CSV (Manual Copy/Delete)",
                "sling.servlet.methods=POST",
                "sling.servlet.paths=/bin/move-assets-from-csv"
        })
public class MoveAssetsFromCsvServlet extends SlingAllMethodsServlet {

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        StringBuilder output = new StringBuilder();

        try (ResourceResolver resolver = getServiceResolver()) {
            Session session = resolver.adaptTo(Session.class);
            InputStream csvStream = request.getRequestParameter("file").getInputStream();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream))) {
                String line;
                boolean isFirstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }

                    if (StringUtils.isBlank(line) || !line.contains(",")) continue;

                    String[] parts = line.split(",", -1);
                    if (parts.length < 2) continue;

                    String sourcePath = parts[0].trim();
                    String targetFolderPath = parts[1].trim();

                    if (sourcePath.isEmpty() || targetFolderPath.isEmpty()) continue;

                    String fileName = sourcePath.substring(sourcePath.lastIndexOf("/") + 1);
                    String targetFullPath = targetFolderPath + "/" + fileName;

                    Resource sourceResource = resolver.getResource(sourcePath);
                    Resource targetFolderResource = resolver.getResource(targetFolderPath);

                    if (sourceResource == null) {
                        output.append("✖ Source not found: ").append(sourcePath).append("\n");
                        continue;
                    }

                    if (targetFolderResource == null) {
                        output.append("✖ Target folder not found: ").append(targetFolderPath).append("\n");
                        continue;
                    }

                    try {
                        session.getWorkspace().copy(sourcePath, targetFullPath); // Copy the node
                        Node sourceNode = sourceResource.adaptTo(Node.class);
                        if (sourceNode != null) {
                            sourceNode.remove(); // Remove original
                        }
                        output.append("✔ Moved: ").append(sourcePath).append(" → ").append(targetFullPath).append("\n");
                    } catch (RepositoryException e) {
                        output.append("✖ Error moving ").append(sourcePath).append(": ").append(e.getMessage()).append("\n");
                    }
                }
                session.save();
            }

        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("✖ Servlet error: " + e.getMessage());
            return;
        }

        response.getWriter().write(output.toString());
    }

    private ResourceResolver getServiceResolver() throws LoginException {
        return resolverFactory.getServiceResourceResolver(
                Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "writeService")
        );
    }
}
