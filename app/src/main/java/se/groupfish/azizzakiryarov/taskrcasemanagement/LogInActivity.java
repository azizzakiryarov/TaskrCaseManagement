package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LogInActivity extends AppCompatActivity {

    static LogInActivity logInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInActivity = this;
        getInstance();

        ImageButton signInBtn = (ImageButton) findViewById(R.id.signIn_btn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.hide();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA500")));
        }
    }

    public static LogInActivity getInstance() {
        return logInActivity;
    }
}
