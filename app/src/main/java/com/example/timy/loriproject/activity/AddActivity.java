package com.example.timy.loriproject.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timy.loriproject.R;
import com.example.timy.loriproject.restApi.JsonHelper;
import com.example.timy.loriproject.restApi.LoriApiClass;
import com.example.timy.loriproject.restApi.domain.Task;
import com.example.timy.loriproject.restApi.domain.User;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    @BindView(R.id.toolbarAdd)
    Toolbar toolbar;

    @BindView(R.id.spinner_project)
    Spinner spinnerProject;

    @BindView(R.id.spinner_task)
    Spinner spinnerTask;

    @BindView(R.id.text_date)
    TextView textDate;

    @BindView(R.id.date_button)
    Button dateBtn;

    @BindView(R.id.text_time)
    TextView textTime;

    @BindView(R.id.time_button)
    Button timeBtn;

    @BindView(R.id.fab_add_entry_time)
    FloatingActionButton saveButton;


    private SharedPreferences sp;
    public static Calendar date;
    private static String time;
    private String taskId;
    private String strDate;
    private JsonHelper jsonHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        jsonHelper=new JsonHelper();

        getUserId();

        String tokken = sp.getString("tokken", null);

        LoriApiClass.getApi().getTasks(tokken).
                enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> response) {

                        Log.d("error", String.valueOf(response.code()));
                        Log.d("error", String.valueOf(response.body()));

                        if (response.body() != null) {
                            List<Task> tasks = response.body();

                            List<String> projectsName = new ArrayList<>();
                            List<String> tasksName = new ArrayList<>();

                            if (tasks != null) {
                                for (Task vo : tasks) {
                                    projectsName.add(vo.getProject().getName());
                                    tasksName.add(vo.getName());
                                }
                                spinnerProject.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, projectsName));
                                spinnerProject.setSelection(0);

                                spinnerTask.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, tasksName));
                                spinnerTask.setSelection(0);

                                spinnerTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String taskName = (String) spinnerTask.getSelectedItem();
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                            taskId = tasks.stream().filter(p -> p.getName().equals(taskName)).findFirst().get().getId();
                                        }else {
                                            for (Task vo:tasks) {
                                                if(vo.getName().equals(taskName)){
                                                    taskId=vo.getId();
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

//                                spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//
//                                    }
//                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable t) {

                    }
                });
    }

    @SuppressLint("SimpleDateFormat")
    @OnClick(R.id.fab_add_entry_time)
    void save() {
        String tokken = sp.getString("tokken", null);
        String userId = sp.getString("userId", null);

        String[] hoursAndMinutes = time.split(":");
        int hours = Integer.parseInt(hoursAndMinutes[0]);
        int minutes = Integer.parseInt(hoursAndMinutes[1]);
        int timeInMinutes = hours * 60 + minutes;
        String timeInHours = String.valueOf(timeInMinutes/60);

        String body=jsonHelper.getJsonTimeEntryAdd(strDate,taskId,userId,timeInHours, String.valueOf(timeInMinutes)).toString();

        LoriApiClass.getApi().commit(tokken, body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Snackbar.make(saveButton, "Не удалось сохранить!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    @OnClick(R.id.date_button)
    void setDate() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerFragment(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.setVersion(DatePickerDialog.Version.VERSION_1);
        dpd.show(getFragmentManager(), "Выберите дату");
        dpd.setOnDismissListener(dialogInterface -> {
            if (date != null) {
                strDate = new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
                textDate.setText(strDate);
            }
        });
    }

    @OnClick(R.id.time_button)
    void setTime() {
        TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerFragment(), 0, 0, true);
        tpd.setVersion(TimePickerDialog.Version.VERSION_2);
        tpd.show(getFragmentManager(), "Выберите время");
        tpd.setOnDismissListener(dialogInterface -> textTime.setText(time));
    }

    private void getUserId() {
        String login = sp.getString("login", null);
        String tokken = sp.getString("tokken", null);

        if (login != null && tokken != null) {
            LoriApiClass.getApi().getUserEntity(tokken, login).enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                    int code = response.code();

                    Log.d("error", String.valueOf(response.code()));
                    Log.d("error", String.valueOf(response.body()));

                    if (code == 200 && response.body() != null) {
                        sp.edit().putString("userId", response.body().get(0).getId()).apply();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                    Log.d("error", t.getMessage());
                }
            });
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            date = calendar;
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
            String strHour = String.valueOf(hourOfDay);
            String strMinute = String.valueOf(minute);
            if (hourOfDay < 10) {
                strHour = "0" + strHour;
            }
            if (minute < 10) {
                strMinute = "0" + strMinute;
            }
            time = strHour + ":" + strMinute;
        }
    }
}
