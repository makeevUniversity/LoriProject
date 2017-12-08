package com.example.timy.loriproject.restApi;

import android.util.Log;

import com.example.timy.loriproject.restApi.domain.TimeEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {

    public JSONObject getJsonTimeEntryDelete(TimeEntry te) {
        JSONObject vo = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject timeEntry = new JSONObject();

        try {
            timeEntry.put("id", te.getId());
            array.put(timeEntry);
            vo.put("removeInstances", array);
            vo.put("softDeletion", "true");
        } catch (JSONException e) {
            Log.d("error", e.getMessage());
        }
        return vo;
    }

    public JSONObject getJsonTimeEntryAdd(String strDate, String taskId, String userId, String timeInHours, String timeInMinutes) {

        JSONObject object = new JSONObject();
        JSONArray commit = new JSONArray();
        JSONObject timeEntry = new JSONObject();

        try {
            timeEntry.put("id", "NEW-ts$TimeEntry");
            timeEntry.put("date", strDate);
            timeEntry.put("status", "new");

            JSONObject task = new JSONObject();
            task.put("id", taskId);
            timeEntry.put("task", task);

            JSONObject user = new JSONObject();
            user.put("id", userId);
            timeEntry.put("user", user);

            timeEntry.put("timeInHours", timeInHours);
            timeEntry.put("timeInMinutes", String.valueOf(timeInMinutes));

            commit.put(timeEntry);
            object.put("commitInstances", commit);
        } catch (JSONException e) {
            Log.d("error", e.getMessage());
        }

        return object;
    }


}
