package com.jeeplus.flowable.model;

import lombok.Data;
import org.flowable.engine.repository.Model;

import java.util.Date;

@Data
public class ModelRepresentation {

    protected String id;
    protected String name;
    protected String key;
    protected String description;
    protected String createdBy;
    protected String lastUpdatedBy;
    protected Date lastUpdated;
    protected boolean latestVersion;
    protected int version;
    protected String comment;
    protected Integer modelType;
    protected String tenantId;

    public ModelRepresentation(Model model) {
        this.initialize(model);
    }

    public ModelRepresentation() {
    }

    public void initialize(Model model) {
        this.id = model.getId();
        this.name = model.getName();
        this.key = model.getKey();
        this.version = model.getVersion();
        this.tenantId = model.getTenantId();

    }

}
