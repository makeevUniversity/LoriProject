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

public class TimeEntry extends RealmObject implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;
    @SerializedName("task")
    @Expose
    private Task task;
    @SerializedName("createTs")
    @Expose
    private String createTs;
    @SerializedName("createdBy")
    @Expose
    private String createdBy;
    @SerializedName("date")
    @Expose
    private String date;
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
    private String description;
    @SerializedName("rejectionReason")
    @Expose
    @Ignore
    private Object rejectionReason;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("taskName")
    @Expose
    private String taskName;
    @SerializedName("timeInHours")
    @Expose
    private String timeInHours;
    @SerializedName("timeInMinutes")
    @Expose
    private String timeInMinutes;
    @SerializedName("updateTs")
    @Expose
    private String updateTs;

    @SerializedName("updatedBy")
    @Expose
    @Ignore
    private Object updatedBy;

    public final static Parcelable.Creator<TimeEntry> CREATOR = new Creator<TimeEntry>() {

        public TimeEntry createFromParcel(Parcel in) {
            TimeEntry instance = new TimeEntry();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.createTs = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdBy = ((String) in.readValue((String.class.getClassLoader())));
            instance.date = ((String) in.readValue((String.class.getClassLoader())));
            instance.deleteTs = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.deletedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.description = ((String) in.readValue((Object.class.getClassLoader())));
            instance.rejectionReason = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.status = ((String) in.readValue((String.class.getClassLoader())));
            instance.taskName = ((String) in.readValue((String.class.getClassLoader())));
            instance.timeInHours = ((String) in.readValue((String.class.getClassLoader())));
            instance.timeInMinutes = ((String) in.readValue((String.class.getClassLoader())));
            instance.updateTs = ((String) in.readValue((String.class.getClassLoader())));
            instance.updatedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.task = (Task) in.readValue(Task.class.getClassLoader());
            return instance;
        }

        public TimeEntry[] newArray(int size) {
            return (new TimeEntry[size]);
        }

    };
    private final static long serialVersionUID = -4499326040926321005L;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(Object rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTimeInHours() {
        return timeInHours;
    }

    public void setTimeInHours(String timeInHours) {
        this.timeInHours = timeInHours;
    }

    public String getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(String timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
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
        dest.writeValue(createTs);
        dest.writeValue(createdBy);
        dest.writeValue(date);
        dest.writeValue(deleteTs);
        dest.writeValue(deletedBy);
        dest.writeValue(description);
        dest.writeValue(rejectionReason);
        dest.writeValue(status);
        dest.writeValue(taskName);
        dest.writeValue(timeInHours);
        dest.writeValue(timeInMinutes);
        dest.writeValue(updateTs);
        dest.writeValue(updatedBy);
    }

    public int describeContents() {
        return 0;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
