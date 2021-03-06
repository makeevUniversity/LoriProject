package com.example.timy.loriproject.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.timy.loriproject.R;
import com.example.timy.loriproject.restApi.LoriApiClass;
import com.example.timy.loriproject.restApi.domain.Token;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        hideKeyboard();

        if(!login.isEmpty() && !pass.isEmpty() && !host.isEmpty() && !port.isEmpty()) {
            sp.edit().putString("login", login).apply();
            sp.edit().putString("pass", pass).apply();
            sp.edit().putString("host", host).apply();
            sp.edit().putString("port", port).apply();
            Snackbar.make(saveButton, "Настройки сохранены!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            singIn();
        }else {
            Snackbar.make(saveButton, "Заполните все поля!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }


    private void singIn() {
        String login = sp.getString("login", "");
        String pass = sp.getString("pass", "");
        String port = sp.getString("port", "");
        String host = sp.getString("host", "");

        if (!login.isEmpty() && !pass.isEmpty() && !port.isEmpty() && !host.isEmpty()) {
            
            LoriApiClass.rebuildRetrofit(this);

            LoriApiClass.getApi().login(login, pass,"password").enqueue(new Callback<Token>() {
                @Override
                public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {

                    int code = response.code();

                    if (code == 200 && response.body()!=null) {
                        String tokken = response.body().getToken();
                        sp.edit().putString("tokken", tokken).apply();
                        finish();
                    } else {
                        Snackbar.make(saveButton, code+" ошибка!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Token> call, @NonNull Throwable t) {
                    Snackbar.make(saveButton, "Нет коннекта с сервером!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        } else {
            Snackbar.make(saveButton, "Проверь настройки!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    private void hideKeyboard(){
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
