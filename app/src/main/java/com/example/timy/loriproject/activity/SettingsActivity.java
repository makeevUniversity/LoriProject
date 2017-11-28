package com.example.timy.loriproject.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.example.timy.loriproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbarSettings)
    Toolbar toolbar;

    @BindView(R.id.editLogin)
    TextView editLoginTextView;

    @BindView(R.id.editPassword)
    TextView editPasswordTextView;

    @BindView(R.id.editHost)
    TextView editHostTextView;

    @BindView(R.id.editPort)
    TextView editPortTextView;

    @BindView(R.id.buttonSave)
    Button saveButton;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            toolbar.setNavigationOnClickListener(v -> finish());
        }

        editLoginTextView.setText(sp.getString("login", ""));
        editHostTextView.setText(sp.getString("host", ""));
        editPasswordTextView.setText(sp.getString("pass", ""));
        editPortTextView.setText(sp.getString("port", ""));
    }

    @OnClick(R.id.buttonSave)
    void saveButtonClick() {
        String login = editLoginTextView.getText().toString();
        String pass = editPasswordTextView.getText().toString();
        String host = editHostTextView.getText().toString();
        String port = editPortTextView.getText().toString();

        sp.edit().putString("login", login).apply();
        sp.edit().putString("pass", pass).apply();
        sp.edit().putString("host", host).apply();
        sp.edit().putString("port", port).apply();

        Snackbar.make(saveButton, "Настройки сохранены!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

}
