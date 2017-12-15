package com.example.timy.loriproject.restApi.domain;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

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
//    @SerializedName("activityType")
//    @Expose
//    private ActivityType activityType;
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
    @SerializedName("tags")
    @Expose
    @Ignore
    private List<Tag> tags;

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
//            instance.activityType=(ActivityType) in.readValue(ActivityType.class.getClassLoader());
//            instance.tags = (List<Tag>) in.readValue(Tag.class.getClassLoader());
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

    public  List<Tag> getTag() {
        return tags;
    }

    public void setTag( List<Tag> tags) {
        this.tags = tags;
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
//        dest.writeValue(activityType);
        dest.writeValue(task);
//        dest.writeValue(tag);
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

//    public ActivityType getActivityType() {
//        return activityType;
//    }
//
//    public void setActivityType(ActivityType activityType) {
//        this.activityType = activityType;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeEntry timeEntry = (TimeEntry) o;

        if (id != null ? !id.equals(timeEntry.id) : timeEntry.id != null) return false;
        if (task != null ? !task.equals(timeEntry.task) : timeEntry.task != null) return false;
        if (createTs != null ? !createTs.equals(timeEntry.createTs) : timeEntry.createTs != null)
            return false;
        if (createdBy != null ? !createdBy.equals(timeEntry.createdBy) : timeEntry.createdBy != null)
            return false;
        if (date != null ? !date.equals(timeEntry.date) : timeEntry.date != null) return false;
        if (deleteTs != null ? !deleteTs.equals(timeEntry.deleteTs) : timeEntry.deleteTs != null)
            return false;
        if (deletedBy != null ? !deletedBy.equals(timeEntry.deletedBy) : timeEntry.deletedBy != null)
            return false;
        if (description != null ? !description.equals(timeEntry.description) : timeEntry.description != null)
            return false;
        if (rejectionReason != null ? !rejectionReason.equals(timeEntry.rejectionReason) : timeEntry.rejectionReason != null)
            return false;
        if (status != null ? !status.equals(timeEntry.status) : timeEntry.status != null)
            return false;
        if (taskName != null ? !taskName.equals(timeEntry.taskName) : timeEntry.taskName != null)
            return false;
        if (timeInHours != null ? !timeInHours.equals(timeEntry.timeInHours) : timeEntry.timeInHours != null)
            return false;
        if (timeInMinutes != null ? !timeInMinutes.equals(timeEntry.timeInMinutes) : timeEntry.timeInMinutes != null)
            return false;
        if (updateTs != null ? !updateTs.equals(timeEntry.updateTs) : timeEntry.updateTs != null)
            return false;
        if (updatedBy != null ? !updatedBy.equals(timeEntry.updatedBy) : timeEntry.updatedBy != null)
            return false;
//        return tag != null ? tag.equals(timeEntry.tag) : timeEntry.tag == null;
        return false;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (task != null ? task.hashCode() : 0);
        result = 31 * result + (createTs != null ? createTs.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (deleteTs != null ? deleteTs.hashCode() : 0);
        result = 31 * result + (deletedBy != null ? deletedBy.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (rejectionReason != null ? rejectionReason.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (taskName != null ? taskName.hashCode() : 0);
        result = 31 * result + (timeInHours != null ? timeInHours.hashCode() : 0);
        result = 31 * result + (timeInMinutes != null ? timeInMinutes.hashCode() : 0);
        result = 31 * result + (updateTs != null ? updateTs.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
//        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TimeEntry{" +
                "id='" + id + '\'' +
                ", task=" + task +
                ", createTs='" + createTs + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", date='" + date + '\'' +
                ", deleteTs=" + deleteTs +
                ", deletedBy=" + deletedBy +
                ", description='" + description + '\'' +
                ", rejectionReason=" + rejectionReason +
                ", status='" + status + '\'' +
                ", taskName='" + taskName + '\'' +
                ", timeInHours='" + timeInHours + '\'' +
                ", timeInMinutes='" + timeInMinutes + '\'' +
                ", updateTs='" + updateTs + '\'' +
                ", updatedBy=" + updatedBy +
                ", tags=" + tags +
                '}';
    }
}
