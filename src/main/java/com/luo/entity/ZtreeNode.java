package com.luo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString(callSuper=true)
public class ZtreeNode {
    private String id;
    @JsonProperty(value = "pId")
    private String pId;
    private String name;

    public ZtreeNode(String id, String pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
