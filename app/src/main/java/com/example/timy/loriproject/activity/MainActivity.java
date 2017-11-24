package com.example.timy.loriproject.activity;

import android.os.Bundle;
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

    @OnClick(R.id.fab)
    void onClickFab(){
        Snackbar.make(fab, "Нажали", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
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
