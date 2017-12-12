package com.example.timy.loriproject.restApi.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ActivityType extends RealmObject implements Serializable,Parcelable{

    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    public ActivityType() {
    }

    public ActivityType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    protected ActivityType(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public static final Creator<ActivityType> CREATOR = new Creator<ActivityType>() {
        @Override
        public ActivityType createFromParcel(Parcel in) {
            return new ActivityType(in);
        }

        @Override
        public ActivityType[] newArray(int size) {
            return new ActivityType[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityType that = (ActivityType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActivityType{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
