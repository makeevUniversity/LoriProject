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
import com.example.timy.loriproject.restApi.LoriApiClass;
import com.example.timy.loriproject.restApi.domain.Project;
import com.example.timy.loriproject.restApi.domain.Tag;
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
import io.realm.RealmList;
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

    @BindView(R.id.spinner_tag)
    Spinner spinnerTag;


    private SharedPreferences sp;
    public static Calendar date;
    private static String time;
    private String taskId;
    private String strDate;
    private boolean update;
    private TimeEntry timeEntry;
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

        update = false;
        String tokken = sp.getString("tokken", null);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            String timeEntryId = bundle.getString("timeEntry");
            String timeEntryDate = bundle.getString("timeEntryDate");
            String timeEntryTimeInMinutes = bundle.getString("timeEntryTimeInMinutes");
            String timeEntryDesc = bundle.getString("timeEntryDesc");
            if (timeEntryId != null && !timeEntryId.isEmpty()) {

                timeEntry = new TimeEntry();
                timeEntry.setId(timeEntryId);


                update = true;
                description.setText(timeEntryDesc);

                int val = Integer.parseInt(timeEntryTimeInMinutes);
                int hours = val / 60;
                int minutes = val % 60;

                String s = String.format("%02d:%02d", hours, minutes);
                textTime.setText(s);

                textDate.setText(timeEntryDate);
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

        realm.beginTransaction();
        String idProject = realm.where(Project.class).equalTo("name", spinnerProject.getSelectedItem().toString()).findFirst().getId();
        realm.commitTransaction();

        
        if (!update) {
            timeEntry = new TimeEntry();
            timeEntry.setId("NEW-ts$TimeEntry");
            timeEntry.setTaskName(spinnerTask.getSelectedItem().toString());
            Task newTask = new Task();
            newTask.setId(taskId);
            Project newProject = new Project();
            newProject.setId(idProject);
            User newUser = new User();
            newUser.setId(userId);
            newTask.setProject(newProject);
            timeEntry.setTask(newTask);
            timeEntry.setDate(strDate);
            timeEntry.setUser(newUser);
            timeEntry.setTimeInMinutes(String.valueOf(timeInMinutes));
            timeEntry.setTimeInHours(timeInHours);
            timeEntry.setDescription(descr);
            RealmList<Tag> tags = new RealmList<>();
            String tagName = String.valueOf(spinnerTag.getSelectedItem());

            if (tagName != null && !tagName.isEmpty()) {
                String tagId = realm.where(Tag.class).equalTo("name", tagName).findFirst().getId();
                if (tagId != null) {
                    Tag tag=new Tag();
                    tag.setId(tagId);
                    tags.add(tag);
                    timeEntry.setTags(tags);
                }
            }
        } else {
            timeEntry.setTaskName(spinnerTask.getSelectedItem().toString());

            RealmList<Tag> tags = new RealmList<>();
            String tagName = String.valueOf(spinnerTag.getSelectedItem());

            if (tagName != null && !tagName.isEmpty()) {
                String tagId = realm.where(Tag.class).equalTo("name", tagName).findFirst().getId();
                if (tagId != null) {
                    Tag tag=new Tag();
                    tag.setId(tagId);
                    tags.add(tag);
                    timeEntry.setTags(tags);
                }
            }

            Task newTask = new Task();
            newTask.setId(taskId);
            Project newProject = new Project();
            newProject.setId(idProject);
            User newUser = new User();
            newUser.setId(userId);
            newTask.setProject(newProject);
            timeEntry.setTask(newTask);
            timeEntry.setDate(strDate);
            timeEntry.setUser(newUser);
            timeEntry.setTimeInMinutes(String.valueOf(timeInMinutes));
            timeEntry.setTimeInHours(timeInHours);
            timeEntry.setDescription(descr);
        }

        if (update) {
            update(tokken, timeEntry);
        } else {
            save(tokken, timeEntry);
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
            LoriApiClass.getApi().getUserEntity("Bearer " + tokken).enqueue(new Callback<User>() {
                @Override
                public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                    int code = response.code();
                    if (code == 200 && response.body() != null) {
                        sp.edit().putString("userId", response.body().getId()).apply();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                    Log.d("error", t.getMessage());
                }
            });
        }
    }


    private void update(String tokken, TimeEntry timeEntry) {


        LoriApiClass.getApi().updateTimeEntry(timeEntry.getId(), timeEntry, "Bearer " + tokken).enqueue(new Callback<TimeEntry>() {
            @Override
            public void onResponse(@NonNull Call<TimeEntry> call, @NonNull Response<TimeEntry> response) {
                Snackbar.make(saveButton, "готово!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<TimeEntry> call, @NonNull Throwable t) {
                Snackbar.make(saveButton, "Нет связи с сервером!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void save(String tokken, TimeEntry timeEntry) {

        LoriApiClass.getApi().commit(timeEntry, "Bearer " + tokken).enqueue(new Callback<String>() {
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
        LoriApiClass.getApi().getTasks("Bearer " + tokken).
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

                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(tasks);
                            realm.commitTransaction();

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

                                    List<String> list = taskNameMap.get(projectName);
                                    if (list != null && !list.isEmpty()) {
                                        spinnerTask.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, list));
                                        spinnerTask.setSelection(0);
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            spinnerTask.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    String taskName = (String) spinnerTask.getSelectedItem();
                                    for (Task vo : tasks) {
                                        if (vo.getName().equals(taskName)) {
                                            taskId = vo.getId();
                                            break;
                                        }
                                    }

                                    realm.executeTransaction(realm -> {
                                        List<Tag> tags = realm.where(Tag.class).findAll();
                                        if (tags != null && !tags.isEmpty()) {
                                            List<String> tagNames = new ArrayList<>();

                                            for (Tag tag : tags) {
                                                tagNames.add(tag.getName());
                                            }

                                            spinnerTag.setAdapter(new ArrayAdapter<>(getApplication(), R.layout.spinner_item, tagNames));
                                        }
                                    });
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
