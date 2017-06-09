package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import http.HttpService;

import static http.NetworkState.isOnline;

public class EditTeamActivity extends AppCompatActivity {

    HttpService httpService = new HttpService();
    Button btnEditTeam;
    EditText teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        btnEditTeam = (Button) findViewById(R.id.btn_edit_team);
        teamName = (EditText) findViewById(R.id.et_edit_teamName);

        btnEditTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String teamNameString = teamName.getText().toString();

                //ONLINE
                if (isOnline(EditTeamActivity.this)) {
                    if (!teamNameString.isEmpty()) {
                        httpService.updateTeamName(1L, teamNameString);
                        Toast.makeText(EditTeamActivity.this, "TeamName is updated... ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditTeamActivity.this, "TeamName is not updated... ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditTeamActivity.this, "You don't have a network...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Edit Team");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA500")));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
