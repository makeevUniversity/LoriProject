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

public class User extends RealmObject implements Serializable, Parcelable {


    @SerializedName("id")
    @Expose
    @PrimaryKey
    private String id;

    @SerializedName("active")
    @Expose
    private String active;

    @SerializedName("changePasswordAtNextLogon")
    @Expose
    @Ignore
    private Object changePasswordAtNextLogon;

    @SerializedName("createTs")
    @Expose
    private String createTs;

    @SerializedName("createdBy")
    @Expose
    @Ignore
    private Object createdBy;

    @SerializedName("deleteTs")
    @Expose
    @Ignore
    private Object deleteTs;

    @SerializedName("deletedBy")
    @Expose
    @Ignore
    private Object deletedBy;

    @SerializedName("email")
    @Expose
    @Ignore
    private Object email;

    @SerializedName("firstName")
    @Expose
    @Ignore
    private Object firstName;

    @SerializedName("ipMask")
    @Expose
    @Ignore
    private Object ipMask;

    @SerializedName("language")
    @Expose
    @Ignore
    private Object language;

    @SerializedName("lastName")
    @Expose
    @Ignore
    private Object lastName;

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("loginLowerCase")
    @Expose
    private String loginLowerCase;

    @SerializedName("middleName")
    @Expose
    @Ignore
    private Object middleName;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("password")
    @Expose
    @Ignore
    private Object password;

    @SerializedName("position")
    @Expose
    @Ignore
    private Object position;

    @SerializedName("timeZone")
    @Expose
    @Ignore
    private Object timeZone;

    @SerializedName("timeZoneAuto")
    @Expose
    @Ignore
    private Object timeZoneAuto;

    @SerializedName("updateTs")
    @Expose
    @Ignore
    private Object updateTs;

    @SerializedName("updatedBy")
    @Expose
    @Ignore
    private Object updatedBy;

    @SerializedName("workHoursForWeek")
    @Expose
    private String workHoursForWeek;

    public final static Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        @SuppressWarnings({
                "unchecked"
        })

        public User createFromParcel(Parcel in) {
            User instance = new User();
            instance.id = ((String) in.readValue((String.class.getClassLoader())));
            instance.active = ((String) in.readValue((String.class.getClassLoader())));
            instance.changePasswordAtNextLogon = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.createTs = ((String) in.readValue((String.class.getClassLoader())));
            instance.createdBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.deleteTs = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.deletedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.email = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.firstName = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.ipMask = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.language = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.lastName = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.login = ((String) in.readValue((String.class.getClassLoader())));
            instance.loginLowerCase = ((String) in.readValue((String.class.getClassLoader())));
            instance.middleName = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.password = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.position = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.timeZone = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.timeZoneAuto = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.updateTs = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.updatedBy = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.workHoursForWeek = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }
    };

    private final static long serialVersionUID = 4800509440027541135L;

    public User() {
    }

    public User(String id, String active, Object changePasswordAtNextLogon, String createTs, Object createdBy, Object deleteTs, Object deletedBy, Object email, Object firstName, Object ipMask, Object language, Object lastName, String login, String loginLowerCase, Object middleName, String name, Object password, Object position, Object timeZone, Object timeZoneAuto, Object updateTs, Object updatedBy, String workHoursForWeek) {
        super();
        this.id = id;
        this.active = active;
        this.changePasswordAtNextLogon = changePasswordAtNextLogon;
        this.createTs = createTs;
        this.createdBy = createdBy;
        this.deleteTs = deleteTs;
        this.deletedBy = deletedBy;
        this.email = email;
        this.firstName = firstName;
        this.ipMask = ipMask;
        this.language = language;
        this.lastName = lastName;
        this.login = login;
        this.loginLowerCase = loginLowerCase;
        this.middleName = middleName;
        this.name = name;
        this.password = password;
        this.position = position;
        this.timeZone = timeZone;
        this.timeZoneAuto = timeZoneAuto;
        this.updateTs = updateTs;
        this.updatedBy = updatedBy;
        this.workHoursForWeek = workHoursForWeek;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Object getChangePasswordAtNextLogon() {
        return changePasswordAtNextLogon;
    }

    public void setChangePasswordAtNextLogon(Object changePasswordAtNextLogon) {
        this.changePasswordAtNextLogon = changePasswordAtNextLogon;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
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

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getIpMask() {
        return ipMask;
    }

    public void setIpMask(Object ipMask) {
        this.ipMask = ipMask;
    }

    public Object getLanguage() {
        return language;
    }

    public void setLanguage(Object language) {
        this.language = language;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLoginLowerCase() {
        return loginLowerCase;
    }

    public void setLoginLowerCase(String loginLowerCase) {
        this.loginLowerCase = loginLowerCase;
    }

    public Object getMiddleName() {
        return middleName;
    }

    public void setMiddleName(Object middleName) {
        this.middleName = middleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public Object getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Object timeZone) {
        this.timeZone = timeZone;
    }

    public Object getTimeZoneAuto() {
        return timeZoneAuto;
    }

    public void setTimeZoneAuto(Object timeZoneAuto) {
        this.timeZoneAuto = timeZoneAuto;
    }

    public Object getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Object updateTs) {
        this.updateTs = updateTs;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getWorkHoursForWeek() {
        return workHoursForWeek;
    }

    public void setWorkHoursForWeek(String workHoursForWeek) {
        this.workHoursForWeek = workHoursForWeek;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(active);
        dest.writeValue(changePasswordAtNextLogon);
        dest.writeValue(createTs);
        dest.writeValue(createdBy);
        dest.writeValue(deleteTs);
        dest.writeValue(deletedBy);
        dest.writeValue(email);
        dest.writeValue(firstName);
        dest.writeValue(ipMask);
        dest.writeValue(language);
        dest.writeValue(lastName);
        dest.writeValue(login);
        dest.writeValue(loginLowerCase);
        dest.writeValue(middleName);
        dest.writeValue(name);
        dest.writeValue(password);
        dest.writeValue(position);
        dest.writeValue(timeZone);
        dest.writeValue(timeZoneAuto);
        dest.writeValue(updateTs);
        dest.writeValue(updatedBy);
        dest.writeValue(workHoursForWeek);

    }

    public int describeContents() {
        return 0;
    }
}