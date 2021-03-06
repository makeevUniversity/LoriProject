package com.example.timy.loriproject.restApi.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Generate Plain Old Java Objects from JSON or JSON-Schema
 * www.jsonschema2pojo.org
 */

public class Project extends RealmObject implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("createTs")
    @Expose
    private String createTs;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("deleteTs")
    @Expose
    @Ignore
    private Object deleteTs;
    @SerializedName("deletedBy")
    @Expose
    @Ignore
    private Object deletedBy;
    @SerializedName("description")
    @Expose
    @Ignore
    private Object description;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("timeEntryNamePattern")
    @Expose
    @Ignore
    private Object timeEntryNamePattern;
    @SerializedName("updateTs")
    @Expose
    private String updateTs;
    @SerializedName("updatedBy")
    @Expose
    @Ignore
    private Object updatedBy;
    public final static Parcelable.Creator<Project> CREATOR = new Creator<Project>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Project createFromParcel(Parcel in) {
            Project instance = new Project();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.code = ((String) in.readValue((String.class.getClassLoader())));
            instance.createTs = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdBy = ((String) in.readValue((String.class.getClassLoader())));
            instance.deleteTs = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.deletedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.description = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.timeEntryNamePattern = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.updateTs = ((String) in.readValue((String.class.getClassLoader())));
            instance.updatedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            return instance;
        }

        public Project[] newArray(int size) {
            return (new Project[size]);
        }

    };
    private final static long serialVersionUID = 7399485980997587958L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Object getDeleteTs() {
        return deleteTs;
    }

    public void setDeleteTs(Object deleteTs) {
        this.deleteTs = deleteTs;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getTimeEntryNamePattern() {
        return timeEntryNamePattern;
    }

    public void setTimeEntryNamePattern(Object timeEntryNamePattern) {
        this.timeEntryNamePattern = timeEntryNamePattern;
    }

    public String getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(code);
        dest.writeValue(createTs);
        dest.writeValue(createdBy);
        dest.writeValue(deleteTs);
        dest.writeValue(deletedBy);
        dest.writeValue(description);
        dest.writeValue(name);
        dest.writeValue(status);
        dest.writeValue(timeEntryNamePattern);
        dest.writeValue(updateTs);
        dest.writeValue(updatedBy);
    }

    public int describeContents() {
        return 0;
    }

}
