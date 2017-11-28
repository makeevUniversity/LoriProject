package com.example.timy.loriproject.restApi.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("_entityName")
    @Expose
    private String entityName;
    @SerializedName("_instanceName")
    @Expose
    private String instanceName;
    @SerializedName("otherEntityFields")
    @Expose
    private String otherEntityFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getOtherEntityFields() {
        return otherEntityFields;
    }

    public void setOtherEntityFields(String otherEntityFields) {
        this.otherEntityFields = otherEntityFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (entityName != null ? !entityName.equals(user.entityName) : user.entityName != null)
            return false;
        if (instanceName != null ? !instanceName.equals(user.instanceName) : user.instanceName != null)
            return false;
        return otherEntityFields != null ? otherEntityFields.equals(user.otherEntityFields) : user.otherEntityFields == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (entityName != null ? entityName.hashCode() : 0);
        result = 31 * result + (instanceName != null ? instanceName.hashCode() : 0);
        result = 31 * result + (otherEntityFields != null ? otherEntityFields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", entityName='" + entityName + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", otherEntityFields='" + otherEntityFields + '\'' +
                '}';
    }
}
