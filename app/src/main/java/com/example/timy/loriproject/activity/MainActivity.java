package com.example.timy.loriproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.timy.loriproject.R;
import com.example.timy.loriproject.adapters.AdapterListEvent;
import com.example.timy.loriproject.adapters.vo.TestVo;
import com.example.timy.loriproject.restApi.LoriApiClass;
import com.example.timy.loriproject.restApi.QueriesAndTypes;
import com.example.timy.loriproject.restApi.domain.TimeEntry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private AdapterListEvent adapterListEvent;
    private SharedPreferences sharedPreferences;
    private List<TimeEntry> list;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.event_list)
    RecyclerView eventList;

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        eventList.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapterListEvent = new AdapterListEvent(list);
        eventList.setAdapter(adapterListEvent);

        RecyclerView.ItemAnimator itemAnimator=new DefaultItemAnimator();
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
        swipeRefreshLayout.setOnRefreshListener(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        singIn();

    }


    @OnClick(R.id.fab)
    void onClickFab() {
        list.add(new TimeEntry());
        adapterListEvent.notifyDataSetChanged();
        eventList.invalidate();
        Snackbar.make(fab, "Нажали", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnLongClick(R.id.fab)
    boolean onLongClickFab() {
        Snackbar.make(fab, "Задержали", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
                    } else {
                        Snackbar.make(fab, "Сервер не отвечает :(", Snackbar.LENGTH_LONG)
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
            singIn();
        }

        if (tokken != null) {
            String user = sharedPreferences.getString("login", "");
            //TODO: Допилить получение даты
            LoriApiClass.getApi().getTimeEntries(QueriesAndTypes.TYPE_TIME_ENTRIES,
                    QueriesAndTypes.QUERY_GET_TIME_ENTRIES,
                    tokken,
                    user,
                    "",
                    "").enqueue(new Callback<List<TimeEntry>>() {


                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(@NonNull Call<List<TimeEntry>> call, @NonNull Response<List<TimeEntry>> response) {
                    int code=response.code();

                    if(code==200){
                       list=response.body();
                        if (list != null) {
                            list.stream().sorted(Comparator.comparing(TimeEntry::describeContents));
                            adapterListEvent.notifyDataSetChanged();
                            eventList.invalidate();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<TimeEntry>> call, @NonNull Throwable t) {

                }
            });
        }

        swipeRefreshLayout.setRefreshing(false);
    }
}
