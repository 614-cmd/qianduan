package com.jeeplus.flowable.model;

import lombok.Data;

@Data
public class ModelKeyRepresentation {
    protected boolean keyAlreadyExists;
    protected String key;
    protected String id;
    protected String name;
}
