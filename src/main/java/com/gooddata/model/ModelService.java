package com.gooddata.model;

import com.gooddata.AbstractService;
import com.gooddata.project.Project;
import com.gooddata.task.AsyncTask;
import com.gooddata.task.TaskStatus;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

/**
 * TODO
 */
public class ModelService extends AbstractService {

    public ModelService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public ModelDiff getProjectModelDiff(Project project, DiffRequest diffRequest) {
        final AsyncTask asyncTask = restTemplate.postForObject(DiffRequest.URI, diffRequest, AsyncTask.class, project.getId());
        return poll(URI.create(asyncTask.getUri()), new StatusOkConditionCallback(), ModelDiff.class);
    }

    public ModelDiff getProjectModelDiff(Project project, String targetModel) {
        return getProjectModelDiff(project, new DiffRequest(targetModel));
    }

    public ModelDiff getProjectModelDiff(Project project, InputStream targetModel) {
        try {
            return getProjectModelDiff(project, new String(IOUtils.toByteArray(targetModel)));
        } catch (IOException e) {
            throw new ModelException("Can't read target model", e);
        }
    }

    public void updateProjectModel(Project project, ModelDiff projectModelDiff) {
        for (ModelDiff.UpdateScript updateScript : projectModelDiff.getUpdateScripts()) {
            for (String maql : updateScript.getMaqlChunks()) {
                updateProjectModel(project, new MaqlDdl(maql));
            }
        }
    }

    public void updateProjectModel(Project project, MaqlDdl maqlDdl) {
        final LinkEntries linkEntries = restTemplate.postForObject(MaqlDdl.URI, maqlDdl, LinkEntries.class, project.getId());
        TaskStatus taskStatus = poll(URI.create(linkEntries.getStatusLink()), TaskStatus.class);
        if (!taskStatus.isSuccess()) {
             throw new ModelException("Update project model finished with status " + taskStatus.getStatus());
        }
    }



    private static class LinkEntries {
        private static final String TASKS_STATUS = "tasks-status";
        private final List<LinkEntry> entries;

        @JsonCreator
        private LinkEntries(@JsonProperty("entries") List<LinkEntry> entries) {
            this.entries = entries;
        }

        public String getStatusLink() {
            for (LinkEntry linkEntry : entries) {
                if (TASKS_STATUS.equals(linkEntry.getCategory())) {
                    return linkEntry.getLink();
                }
            }
            return null;
        }
    }


    private static class LinkEntry {
        private final String link;
        private final String category;

        @JsonCreator
        private LinkEntry(@JsonProperty("link") String link, @JsonProperty("category") String category) {
            this.link = link;
            this.category = category;
        }

        public String getLink() {
            return link;
        }

        public String getCategory() {
            return category;
        }
    }
}