package com.example.timy.loriproject.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.timy.loriproject.R;
import com.example.timy.loriproject.adapters.AdapterListEvent;
import com.example.timy.loriproject.restApi.LoriApiClass;
import com.example.timy.loriproject.restApi.domain.Tag;
import com.example.timy.loriproject.restApi.domain.TimeEntry;
import com.example.timy.loriproject.restApi.domain.Token;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private AdapterListEvent adapterListEvent;
    private static SharedPreferences sharedPreferences;
    private static SimpleDateFormat sdf;
    private List<TimeEntry> list;
    private static Calendar calendarSelected;
    private Realm realm;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.event_list)
    RecyclerView eventList;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar_date)
    TextView toolbarDate;

    @BindView(R.id.plus_day)
    Button plusDay;

    @BindView(R.id.minus_day)
    Button minusDay;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        swipeRefreshLayout.setOnRefreshListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        eventList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapterListEvent = new AdapterListEvent(list, sharedPreferences, this);
        eventList.setAdapter(adapterListEvent);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        eventList.setItemAnimator(itemAnimator);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        singIn();
    }

    @OnClick(R.id.plus_day)
    void plusDayClick() {
        String tokken = sharedPreferences.getString("tokken", null);
        if (tokken != null) {
            String from = sharedPreferences.getString("from", null);
            if (from != null) {
                try {
                    long time = sdf.parse(from).getTime();
                    time += 86400000;
                    from = sdf.format(new Date(time));

                    toolbarDate.setText(from);
                    sharedPreferences.edit().putString("from", from).apply();
                    sharedPreferences.edit().putString("to", from).apply();

                    swipeRefreshLayout.setRefreshing(true);
                    downloadEntity();
                } catch (ParseException e) {
                    Log.d("error", e.getMessage());
                }
            }
        } else {
            Snackbar.make(fab, "Вход не выполнен!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @OnClick(R.id.minus_day)
    void minusDayClick() {
        String tokken = sharedPreferences.getString("tokken", null);
        if (tokken != null) {
            String from = sharedPreferences.getString("from", null);
            if (from != null) {
                try {
                    long time = sdf.parse(from).getTime();
                    time -= 86400000;
                    from = sdf.format(new Date(time));

                    toolbarDate.setText(from);
                    sharedPreferences.edit().putString("from", from).apply();
                    sharedPreferences.edit().putString("to", from).apply();

                    swipeRefreshLayout.setRefreshing(true);
                    downloadEntity();
                } catch (ParseException e) {
                    Log.d("error", e.getMessage());
                }
            }
        } else {
            Snackbar.make(fab, "Вход не выполнен!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }


    @OnClick(R.id.fab)
    void onClickFab() {
        String tokken = sharedPreferences.getString("tokken", null);
        if (tokken != null) {
            Intent intent = new Intent(this, AddActivity.class);
            startActivity(intent);
        } else {
            Snackbar.make(fab, "Вход не выполнен!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_btn) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_calendar) {
            if (calendarSelected == null) {
                calendarSelected = Calendar.getInstance();
            }
            DatePickerDialog dpd = DatePickerDialog.newInstance(new WeekPickerFragment(), calendarSelected.get(Calendar.YEAR), calendarSelected.get(Calendar.MONTH),
                    calendarSelected.get(Calendar.DAY_OF_MONTH));
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.show(getFragmentManager(), "Выберите дату");
            swipeRefreshLayout.setRefreshing(true);
            dpd.setOnDismissListener(dialogInterface -> downloadEntity());
            return true;
        }

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void singIn() {
        String login = sharedPreferences.getString("login", "");
        String pass = sharedPreferences.getString("pass", "");
        String port = sharedPreferences.getString("port", "");
        String host = sharedPreferences.getString("host", "");

        if (!login.isEmpty() && !pass.isEmpty() && !port.isEmpty() && !host.isEmpty()) {
            LoriApiClass.getApi().login(login, pass, "password").enqueue(new Callback<Token>() {
                @Override
                public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {

                    int code = response.code();

                    if (code == 200 && response.body() != null) {
                        String tokken = response.body().getToken();
                        sharedPreferences.edit().putString("tokken", tokken).apply();
                        Snackbar.make(fab, "Коннект с сервером установлен!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        onRefresh();
                    } else {
                        Snackbar.make(fab, "ошибка : " + code, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        downloadEntity();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {

                    downloadEntity();
                    Snackbar.make(fab, "Нет коннекта с сервером!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } else {
            downloadEntity();
            Snackbar.make(fab, "Проверь настройки!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void downloadEntity() {

        String from = sharedPreferences.getString("from", null);

        list.clear();

        realm.executeTransaction(realm -> {
            List<TimeEntry> timeEntries = realm.where(TimeEntry.class).equalTo("date", from).findAll();
            list.addAll(timeEntries);

        });


        adapterListEvent.notifyDataSetChanged();
        eventList.invalidate();

        toolbarDate.setText(sharedPreferences.getString("from", "Выберите дату!"));

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        String tokken = sharedPreferences.getString("tokken", null);

        if (tokken == null) {
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(fab, "Вход не выполнен!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }

        LoriApiClass.getApi().getTimeEntries("Bearer " + tokken).enqueue(new Callback<List<TimeEntry>>() {
            @Override
            public void onResponse(@NonNull Call<List<TimeEntry>> call, @NonNull Response<List<TimeEntry>> response) {
                int code = response.code();
                if (code == 200) {
                    List<TimeEntry> timeEntries = response.body();
                    if (timeEntries != null) {

                        Log.d("downloadEntity: ", String.valueOf(timeEntries.size()));

//                        for (TimeEntry vo:timeEntries) {
//                            List<Tag> tags = vo.getTags();
//                            if (tags != null && !tags.isEmpty()) {
//                                realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(tags));
//                                for (Tag tag:tags) {
//                                    vo.setTag(tag);
//                                    Log.d("Tag: ", tag.getName());
//                                }
//                            }
//                        }

                        realm.executeTransaction(realm -> realm.copyToRealmOrUpdate(timeEntries));
                    }

                }
                downloadEntity();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(@NonNull Call<List<TimeEntry>> call, @NonNull Throwable t) {
                Log.d("error", t.getMessage());
                Snackbar.make(fab, "Обновить данные не удалось: " + t.getLocalizedMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static class WeekPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            String formDate = sdf.format(calendar.getTime());
            sharedPreferences.edit().putString("from", formDate).apply();
            calendarSelected = Calendar.getInstance();
            calendarSelected.set(year, monthOfYear, dayOfMonth);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
