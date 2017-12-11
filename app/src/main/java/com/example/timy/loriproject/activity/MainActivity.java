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
import android.view.View;

import com.example.timy.loriproject.R;
import com.example.timy.loriproject.adapters.AdapterListEvent;
import com.example.timy.loriproject.restApi.LoriApiClass;
import com.example.timy.loriproject.restApi.domain.TimeEntry;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

        eventList.addOnItemTouchListener(new AdapterListEvent.AdapterClickListener(getApplicationContext(),
                eventList,
                new AdapterListEvent.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Snackbar.make(fab, "Клик", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Snackbar.make(fab, "Долгий клик", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }));

        sdf = new SimpleDateFormat("yyyy-MM-dd");

        singIn();

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

        if (id == R.id.action_calendar) {
            if (calendarSelected == null) {
                calendarSelected = Calendar.getInstance();
            }
            DatePickerDialog dpd = DatePickerDialog.newInstance(new WeekPickerFragment(), calendarSelected.get(Calendar.YEAR), calendarSelected.get(Calendar.MONTH),
                    calendarSelected.get(Calendar.DAY_OF_MONTH));
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.show(getFragmentManager(), "Выберите дату");
            swipeRefreshLayout.setRefreshing(true);
            dpd.setOnDismissListener(dialogInterface -> onRefresh());
            return true;
        }

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_close_session) {

            String tokken = sharedPreferences.getString("tokken", null);

            if (tokken != null) {

                LoriApiClass.getApi().logout(tokken).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        Log.d("error", String.valueOf(response.code()));

                        sharedPreferences.edit().remove("tokken").apply();

                        Snackbar.make(fab, "Сессия закрыта", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Log.d("error", t.getMessage());
                        Snackbar.make(fab, "Что-то пошло не так :(", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });

            }
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
            LoriApiClass.getApi().login(login, pass).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                    int code = response.code();

                    if (code == 200) {
                        String tokken = response.body();
                        sharedPreferences.edit().putString("tokken", tokken).apply();
                        Snackbar.make(fab, "Коннект с сервером установлен!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();


                        LoriApiClass.getApi().getTags(tokken).enqueue(new Callback<List<Object>>() {
                            @Override
                            public void onResponse(@NonNull Call<List<Object>> call, @NonNull Response<List<Object>> response) {
                                if(response.code()==200) {
                                    if(response.body()!=null) {
                                        Log.d("tagsBody", response.body().toString());
                                    }
                                    else {
                                        Log.d("tags", null);
                                    }
                                }
                                else {
                                    Log.d("tags", String.valueOf(response.code()));
                                    Log.d("tags",response.message());
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<List<Object>> call, @NonNull Throwable t) {
                                Log.d("tags", t.getMessage());
                                Log.d("tags", t.getLocalizedMessage());
                            }
                        });

                    } else {
                        Snackbar.make(fab, "ошибка : " + code, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {

                    Snackbar.make(fab, "Нет коннекта с сервером!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } else {
            Snackbar.make(fab, "Проверь настройки!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
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

        String user = sharedPreferences.getString("login", null);
        String from = sharedPreferences.getString("from", null);
        String to = sharedPreferences.getString("to", null);

        if (user != null && from != null && to != null) {

            LoriApiClass.getApi().getTimeEntries(
                    tokken,
                    user,
                    from,
                    to
            ).enqueue(new Callback<List<TimeEntry>>() {

                @Override
                public void onResponse(@NonNull Call<List<TimeEntry>> call, @NonNull Response<List<TimeEntry>> response) {
                    int code = response.code();

                    if (code == 200) {

                        List<TimeEntry> timeEntries = response.body();

                        if (timeEntries != null) {

                            realm.beginTransaction();
                            for (TimeEntry vo : timeEntries) {
                                realm.copyToRealmOrUpdate(vo);
                            }
                            realm.commitTransaction();

                            list.clear();
                            list.addAll(timeEntries);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                list.stream().sorted(Comparator.comparing(TimeEntry::describeContents));
                            } else {
                                Collections.sort(list, (o1, o2) -> Integer.compare(o1.describeContents(), o2.describeContents()));
                            }
                            adapterListEvent.notifyDataSetChanged();
                            eventList.invalidate();
                        }
                    } else {
                        list.clear();
                        list.addAll(realm.where(TimeEntry.class).equalTo("date",from ).findAllAsync());
                        adapterListEvent.notifyDataSetChanged();
                        eventList.invalidate();

                        Snackbar.make(fab, "ошибка : " + code, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(@NonNull Call<List<TimeEntry>> call, @NonNull Throwable t) {
                    list.clear();
                    list.addAll(realm.where(TimeEntry.class).equalTo("date",from ).findAllAsync());
                    adapterListEvent.notifyDataSetChanged();
                    eventList.invalidate();

                    Log.d("error", t.getMessage());

                    Snackbar.make(fab, "Обновить данные не удалось: "+ t.getLocalizedMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
        }
    }

    public static class WeekPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);

            String formDate=sdf.format(calendar.getTime());

            sharedPreferences.edit().putString("from", formDate).apply();

            //calendar.add(Calendar.DATE, 1);

            formDate=sdf.format(calendar.getTime());

            sharedPreferences.edit().putString("to", formDate).apply();

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
