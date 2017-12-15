package com.example.timy.loriproject.restApi.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Tag extends RealmObject implements Serializable,Parcelable {

    @SerializedName("id")
    @PrimaryKey
    @Expose
    private String id;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    @SerializedName("createTs")
    @Expose
    private String createTs;

    @SerializedName("deleteTs")
    @Expose
    private String deleteTs;

    @SerializedName("deletedBy")
    @Expose
    private String deletedBy;

    @SerializedName("updatedBy")
    @Expose
    private String updatedBy;

    @SerializedName("updateTs")
    private String updateTs;

    public Tag() {
    }

    public Tag(String id, String code, String name, String description, String createdBy, String createTs, String deleteTs, String deletedBy, String updatedBy, String updateTs) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.createdBy = createdBy;
        this.createTs = createTs;
        this.deleteTs = deleteTs;
        this.deletedBy = deletedBy;
        this.updatedBy = updatedBy;
        this.updateTs = updateTs;
    }

    protected Tag(Parcel in) {
        id = in.readString();
        name = in.readString();
        code = in.readString();
        description = in.readString();
        createdBy = in.readString();
        createTs = in.readString();
        deleteTs = in.readString();
        deletedBy = in.readString();
        updatedBy = in.readString();
        updateTs = in.readString();
    }

    public static final Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel in) {
            return new Tag(in);
        }

        @Override
        public Tag[] newArray(int size) {
            return new Tag[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public String getDeleteTs() {
        return deleteTs;
    }

    public void setDeleteTs(String deleteTs) {
        this.deleteTs = deleteTs;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(String updateTs) {
        this.updateTs = updateTs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
        if (code != null ? !code.equals(tag.code) : tag.code != null) return false;
        if (name != null ? !name.equals(tag.name) : tag.name != null) return false;
        if (description != null ? !description.equals(tag.description) : tag.description != null)
            return false;
        if (createdBy != null ? !createdBy.equals(tag.createdBy) : tag.createdBy != null)
            return false;
        if (createTs != null ? !createTs.equals(tag.createTs) : tag.createTs != null) return false;
        if (deleteTs != null ? !deleteTs.equals(tag.deleteTs) : tag.deleteTs != null) return false;
        if (deletedBy != null ? !deletedBy.equals(tag.deletedBy) : tag.deletedBy != null) return false;
        if (updatedBy != null ? !updatedBy.equals(tag.updatedBy) : tag.updatedBy != null)
            return false;
        return updateTs != null ? updateTs.equals(tag.updateTs) : tag.updateTs == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createTs != null ? createTs.hashCode() : 0);
        result = 31 * result + (deleteTs != null ? deleteTs.hashCode() : 0);
        result = 31 * result + (deletedBy != null ? deletedBy.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (updateTs != null ? updateTs.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createTs='" + createTs + '\'' +
                ", deleteTs='" + deleteTs + '\'' +
                ", deletedBy='" + deletedBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", updateTs='" + updateTs + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(code);
        dest.writeValue(description);
        dest.writeValue(createdBy);
        dest.writeValue(createTs);
        dest.writeValue(deleteTs);
        dest.writeValue(deletedBy);
        dest.writeValue(updatedBy);
        dest.writeValue(updateTs);
    }
}
