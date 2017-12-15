package com.example.timy.loriproject.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timy.loriproject.R;
import com.example.timy.loriproject.activity.invariants.TypeRadioBtnSelected;
import com.example.timy.loriproject.adapters.AdapterListEvent;
import com.example.timy.loriproject.restApi.LoriApiClass;
import com.example.timy.loriproject.restApi.domain.Project;
import com.example.timy.loriproject.restApi.domain.Tag;
import com.example.timy.loriproject.restApi.domain.TimeEntry;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private Realm realm;
    private static SharedPreferences sp;
    private static SimpleDateFormat sdf;
    private static Calendar calendarSelectedFrom;
    private static Calendar calendarSelectedTo;
    private List<TimeEntry> list;
    private AdapterListEvent adapterListEvent;
    private TypeRadioBtnSelected typeRadioBtnSelected;

    @BindView(R.id.toolbarSearch)
    Toolbar toolbar;

    @BindView(R.id.recyclerViewSearch)
    RecyclerView recyclerView;

    @BindView(R.id.search_fab)
    FloatingActionButton fab;

    @BindView(R.id.spinner_search)
    Spinner spinner;

    @BindView(R.id.description_search)
    EditText editTextDescription;

    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    @BindView(R.id.radioButton1)
    RadioButton descriptionRadioBtn;

    @BindView(R.id.radioButton2)
    RadioButton projectNameRadioBtn;

    @BindView(R.id.radioButton3)
    RadioButton dateRadioBtn;

    @BindView(R.id.search_btn_from)
    Button searchBrnFrom;

    @BindView(R.id.search_btn_to)
    Button searchBrnTo;

    @BindView(R.id.search_text_from)
    TextView searchTextFrom;

    @BindView(R.id.search_text_to)
    TextView searchTextTo;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }

        sdf = new SimpleDateFormat("yyyy-MM-dd");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapterListEvent = new AdapterListEvent(list, sp, this);
        recyclerView.setAdapter(adapterListEvent);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        getProjects();

        projectNameRadioBtn.setChecked(true);
        typeRadioBtnSelected = TypeRadioBtnSelected.PROJECT;

        downloadEntity("1999-01-01", "2025-01-01");


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case -1:
                    break;
                case R.id.radioButton1:
                    typeRadioBtnSelected = TypeRadioBtnSelected.DESCRIPTION;
                    break;
                case R.id.radioButton2:
                    typeRadioBtnSelected = TypeRadioBtnSelected.PROJECT;
                    break;
                case R.id.radioButton3:
                    typeRadioBtnSelected = TypeRadioBtnSelected.DATE;
                    break;
            }
        });
    }

    @OnClick(R.id.search_btn_from)
    void selectDateFrom() {
        if (calendarSelectedFrom == null) {
            calendarSelectedFrom = Calendar.getInstance();
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(new WeekPickerFragmentFrom(), calendarSelectedFrom.get(Calendar.YEAR), calendarSelectedFrom.get(Calendar.MONTH),
                calendarSelectedFrom.get(Calendar.DAY_OF_MONTH));
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.show(getFragmentManager(), "Выберите дату");
        dpd.setOnDismissListener(dialogInterface ->
                searchTextFrom.setText(sp.getString("fromSearch", null)));
    }

    @OnClick(R.id.search_btn_to)
    void selectDateTo() {
        if (calendarSelectedTo == null) {
            calendarSelectedTo = Calendar.getInstance();
        }
        DatePickerDialog dpd = DatePickerDialog.newInstance(new WeekPickerFragmentTo(), calendarSelectedTo.get(Calendar.YEAR), calendarSelectedTo.get(Calendar.MONTH),
                calendarSelectedTo.get(Calendar.DAY_OF_MONTH));
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.show(getFragmentManager(), "Выберите дату");
        dpd.setOnDismissListener(dialogInterface ->
                searchTextTo.setText(sp.getString("toSearch", null)));
    }

    @OnClick(R.id.search_fab)
    void searchClick() {

        hideKeyboard();

        if (typeRadioBtnSelected.equals(TypeRadioBtnSelected.DATE)) {
            String from = searchTextFrom.getText().toString();
            String to = searchTextTo.getText().toString();
            if (from.equals("От") || to.equals("До")) {
                Snackbar.make(fab, "Выберите временной промежуток!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            downloadEntity(searchTextFrom.getText().toString(), searchTextTo.getText().toString());
        }
        if (typeRadioBtnSelected.equals(TypeRadioBtnSelected.DESCRIPTION)) {
            list.clear();
            list.addAll(realm.where(TimeEntry.class).like("description", "*" + editTextDescription.getText().toString() + "*").findAll());
            recyclerView.invalidate();
            adapterListEvent.notifyDataSetChanged();
        }
        if (typeRadioBtnSelected.equals(TypeRadioBtnSelected.PROJECT)) {
            if(spinner.getSelectedItem()==null){
                Snackbar.make(fab, "Выберите проект!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;
            }
            String projectName = spinner.getSelectedItem().toString();
            list.clear();
            List<TimeEntry> timeEntries = realm.where(TimeEntry.class).equalTo("task.project.name", projectName).findAll();
            list.addAll(timeEntries);
            recyclerView.invalidate();
            adapterListEvent.notifyDataSetChanged();
        }

    }

    private void downloadEntity(String dateFrom, String dateTo) {
        String tokken = sp.getString("tokken", null);
        String user = sp.getString("login", null);

        LoriApiClass.getApi().getTimeEntries(tokken, user, dateFrom, dateTo).enqueue(new Callback<List<TimeEntry>>() {
            @Override
            public void onResponse(@NonNull Call<List<TimeEntry>> call, @NonNull Response<List<TimeEntry>> response) {
                List<TimeEntry> timeEntries = response.body();
                if (timeEntries != null) {

                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(timeEntries);
                    realm.commitTransaction();

                    list.clear();
                    list.addAll(timeEntries);
                    recyclerView.invalidate();
                    adapterListEvent.notifyDataSetChanged();

                    Log.d("CountTE", String.valueOf(timeEntries.size()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<TimeEntry>> call, @NonNull Throwable t) {
                Snackbar.make(fab, "Нет коннекта с сервером!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                list.clear();
                list.addAll(realm.where(TimeEntry.class).findAll());
                recyclerView.invalidate();
                adapterListEvent.notifyDataSetChanged();
            }
        });

    }

    private void getProjects() {

        String tokken = sp.getString("tokken", null);

        if (tokken == null) {
            Snackbar.make(fab, "Нет коннекта с сервером/токкен не получен!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        LoriApiClass.getApi().getProjects(tokken).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(@NonNull Call<List<Project>> call, @NonNull Response<List<Project>> response) {
                List<String> projectsName = new ArrayList<>();

                if (response.code() == 200) {
                    if (response.body() != null) {
                        List<Project> projects = response.body();

                        for (Project vo : projects) {
                            projectsName.add(vo.getName());
                        }

                        spinner.setAdapter(new ArrayAdapter<>(getApplication(), R.layout.spinner_item, projectsName));
                        spinner.setSelection(0);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Project>> call, @NonNull Throwable t) {
                List<String> projectsName = new ArrayList<>();

                List<Project> projects = realm.where(Project.class).findAll();
                if (projects != null && !projects.isEmpty()) {
                    for (Project vo : projects) {
                        projectsName.add(vo.getName());
                    }
                } else {
                    projectsName.add("Couch DB");
                    projectsName.add("Cassandra");
                }
                spinner.setAdapter(new ArrayAdapter<>(getApplication(), R.layout.spinner_item, projectsName));
                spinner.setSelection(0);
                Snackbar.make(fab, "Нет коннекта с сервером!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public static class WeekPickerFragmentFrom extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);

            String formDate = sdf.format(calendar.getTime());

            sp.edit().putString("fromSearch", formDate).apply();

            calendarSelectedFrom = Calendar.getInstance();
            calendarSelectedFrom.set(year, monthOfYear, dayOfMonth);

        }
    }

    public static class WeekPickerFragmentTo extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);

            String toDate = sdf.format(calendar.getTime());

            sp.edit().putString("toSearch", toDate).apply();

            calendarSelectedTo = Calendar.getInstance();
            calendarSelectedTo.set(year, monthOfYear, dayOfMonth);

        }
    }

    private void hideKeyboard(){
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
