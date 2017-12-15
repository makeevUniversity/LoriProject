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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timy.loriproject.R;
import com.example.timy.loriproject.restApi.JsonHelper;
import com.example.timy.loriproject.restApi.LoriApiClass;
import com.example.timy.loriproject.restApi.domain.Task;
import com.example.timy.loriproject.restApi.domain.TimeEntry;
import com.example.timy.loriproject.restApi.domain.User;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
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

    @BindView(R.id.text_description)
    TextView description;


    private SharedPreferences sp;
    public static Calendar date;
    private static String time;
    private String taskId;
    private String strDate;
    private JsonHelper jsonHelper;
    private boolean update;
    private String id;
    private Realm realm;
    private Map<String, ArrayList<String>> taskNameMap;


    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();


        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        jsonHelper = new JsonHelper();
        update = false;
        String tokken = sp.getString("tokken", null);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            TimeEntry timeEntry = (TimeEntry) bundle.getSerializable("timeEntry");
            if (timeEntry != null) {
                update = true;
                description.setText(timeEntry.getDescription());
                id = timeEntry.getId();

                int val = Integer.parseInt(timeEntry.getTimeInMinutes());
                int hours = val / 60;
                int minutes = val % 60;

                String s = String.format("%02d:%02d",hours,minutes);

//                long msc = val * 60000;
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
//                String time = simpleDateFormat.format(msc);
                textTime.setText(s);

                textDate.setText(timeEntry.getDate());
            }
        }

        getUserId();
        updateSpinners(tokken);

    }

    @OnClick(R.id.fab_add_entry_time)
    void saveOrUpdate() {
        String tokken = sp.getString("tokken", null);
        String userId = sp.getString("userId", null);


        if (time == null || time.isEmpty()) {
            time = textTime.getText().toString();
            if (time.isEmpty()) {
                Snackbar.make(saveButton, "Заполните все поля!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
        }

        if (strDate == null || strDate.isEmpty()) {
            strDate = textDate.getText().toString();
            if (strDate.isEmpty()) {
                Snackbar.make(saveButton, "Заполните все поля!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
        }

        String[] hoursAndMinutes = time.split(":");
        int hours = Integer.parseInt(hoursAndMinutes[0]);
        int minutes = Integer.parseInt(hoursAndMinutes[1]);
        int timeInMinutes = hours * 60 + minutes;
        String timeInHours = String.valueOf(timeInMinutes / 60);
        String descr = description.getText().toString();

        if (tokken == null || userId == null || strDate == null || taskId == null) {
            Snackbar.make(saveButton, "Заполните все поля!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        if (update) {
            update(tokken, userId, id, strDate, taskId, timeInHours, String.valueOf(timeInMinutes), descr);
        } else {
            save(tokken, userId, timeInHours, String.valueOf(timeInMinutes), descr);
        }
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


    private void update(String tokken, String userId, String id, String date, String idTask, String hours, String minutes, String description) {
        String body = jsonHelper.getJsonTimeEntryUpdate(id, date, idTask, userId, hours, minutes, description).toString();
        LoriApiClass.getApi().commit(tokken, body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                Snackbar.make(saveButton, "готово!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Snackbar.make(saveButton, "Нет связи с сервером!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void save(String tokken, String userId, String timeInHours, String timeInMinutes, String descr) {


        String body = jsonHelper.getJsonTimeEntryAdd(strDate, taskId, userId, timeInHours, timeInMinutes, descr).toString();

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

    private void updateSpinners(String tokken) {
        LoriApiClass.getApi().getTasks(tokken).
                enqueue(new Callback<List<Task>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Task>> call, @NonNull Response<List<Task>> response) {
                        if (response.body() != null) {
                            List<Task> tasks = response.body();

                            if (tasks == null) {
                                return;
                            }

                            List<String> projectNameList = new ArrayList<>();
                            taskNameMap = new HashMap<>();

                            for (Task vo : tasks) {
                                if (!projectNameList.contains(vo.getProject().getName())) {
                                    projectNameList.add(vo.getProject().getName());
                                }

                                if (taskNameMap.get(vo.getProject().getName()) == null) {
                                    ArrayList<String> arrayList = new ArrayList<>();
                                    arrayList.add(vo.getName());
                                    taskNameMap.put(vo.getProject().getName(), arrayList);
                                } else {
                                    List<String> list = taskNameMap.get(vo.getProject().getName());

                                    if (!list.contains(vo.getName())) {
                                        list.add(vo.getName());
                                    }
                                }
                            }

                            spinnerProject.setAdapter(new ArrayAdapter<>(getApplication(), R.layout.spinner_item, projectNameList));
                            spinnerProject.setSelection(0);

                            spinnerProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String projectName = (String) spinnerProject.getSelectedItem();

                                    spinnerTask.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, taskNameMap.get(projectName)));
                                    spinnerTask.setSelection(0);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            spinnerTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String taskName = (String) spinnerTask.getSelectedItem();
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        Task task = tasks.stream().filter(p -> p.getName().equals(taskName)).findFirst().get();
                                        taskId = task.getId();
                                    } else {
                                        for (Task vo : tasks) {
                                            if (vo.getName().equals(taskName)) {
                                                taskId = vo.getId();
                                                break;
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Task>> call, @NonNull Throwable t) {

                    }
                });
    }

    private void hideKeyboard(){
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
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
