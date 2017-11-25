package com.example.timy.loriproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.timy.loriproject.R;
import com.example.timy.loriproject.adapters.AdapterListEvent;
import com.example.timy.loriproject.adapters.vo.TestVo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemSelected;
import butterknife.OnLongClick;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private AdapterListEvent adapterListEvent;

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

        singIn();

        setSupportActionBar(toolbar);

        eventList.setLayoutManager(new LinearLayoutManager(this));
        List<TestVo> list=new TestVo("").getTestVo();
        adapterListEvent=new AdapterListEvent(list);
        eventList.setAdapter(adapterListEvent);
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

    }

    private void singIn(){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);

        String login=sharedPreferences.getString("login","");
        String pass=sharedPreferences.getString("pass","");
        if(!login.isEmpty() && !pass.isEmpty()){
            //выполнить запрос
        }else {
            Snackbar.make(fab, "Проверь настройки!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    @OnClick(R.id.fab)
    void onClickFab(){
        Snackbar.make(fab, "Нажали", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @OnLongClick(R.id.fab)
    boolean onLongClickFab(){
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
            Intent intent=new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        Snackbar.make(fab, "Обновили", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        swipeRefreshLayout.setRefreshing(false);
    }
}
