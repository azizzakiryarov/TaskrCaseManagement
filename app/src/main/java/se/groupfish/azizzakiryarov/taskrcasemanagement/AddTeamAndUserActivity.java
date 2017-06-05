package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dbhelper.DatabaseHelper;

public class AddTeamAndUserActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Cursor result = null;

    Button addTeam;
    Button addUser;
    Button getAllTeams;
    Button getAllUsers;

    EditText teamName;
    EditText teamState;
    EditText firstName;
    EditText lastName;
    EditText userName;
    EditText userNumber;
    EditText userState;
    EditText teamId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team_and_user);

        databaseHelper = new DatabaseHelper(this);

        teamName = (EditText) findViewById(R.id.et_teamName);
        teamState = (EditText) findViewById(R.id.et_team_state);

        firstName = (EditText) findViewById(R.id.et_firstName);
        lastName = (EditText) findViewById(R.id.et_lastName);
        userName = (EditText) findViewById(R.id.et_userName);
        userNumber = (EditText) findViewById(R.id.et_userNumber);
        userState = (EditText) findViewById(R.id.et_user_state);
        teamId = (EditText) findViewById(R.id.et_teamId);

        addTeam = (Button) findViewById(R.id.btn_add_team);
        addUser = (Button) findViewById(R.id.btn_add_user);
        getAllTeams = (Button) findViewById(R.id.btn_getAllTeam);
        getAllUsers = (Button) findViewById(R.id.btn_getAllUser);


        addTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String teamNameString = teamName.getText().toString();
                String teamStateString = teamState.getText().toString();

                databaseHelper.addTeam(teamNameString, teamStateString);
                Toast.makeText(AddTeamAndUserActivity.this, "Added Team", Toast.LENGTH_SHORT).show();
            }
        });

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameString = firstName.getText().toString();
                String lastNameString = lastName.getText().toString();
                String userNameString = userName.getText().toString();
                String userNumberString = userNumber.getText().toString();
                String userStateString = userState.getText().toString();
                Long teamidLong = Long.valueOf(teamId.getText().toString());

                databaseHelper.addUser(firstNameString, lastNameString, userNameString, userNumberString, userStateString, teamidLong);
                Toast.makeText(AddTeamAndUserActivity.this, "Added User", Toast.LENGTH_SHORT).show();
            }
        });

        getAllTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddTeamAndUserActivity.this, "Success", Toast.LENGTH_SHORT).show();
                result = databaseHelper.getAllTeams();
                if (result.moveToFirst()) {
                    do {
                        Toast.makeText(AddTeamAndUserActivity.this,
                                "id: " + result.getString(0) + "\n" +
                                        "teamName: " + result.getString(1) + "\n" +
                                        "state: " + result.getString(2) + "\n", Toast.LENGTH_SHORT).show();
                    } while (result.moveToNext());
                }
            }
        });

        getAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddTeamAndUserActivity.this, "Success", Toast.LENGTH_SHORT).show();
                result = databaseHelper.getAllUsers();
                if (result.moveToFirst()) {
                    do {
                        Toast.makeText(AddTeamAndUserActivity.this,
                                "id: " + result.getString(0) + "\n" +
                                        "firstName: " + result.getString(1) + "\n" +
                                        "lastName: " + result.getString(2) + "\n" +
                                        "userName: " + result.getString(3) + "\n" +
                                        "userNumber: " + result.getString(4) + "\n" +
                                        "state: " + result.getString(5) + "\n" +
                                        "teamId: " + result.getString(6) + "\n" + "\n", Toast.LENGTH_SHORT).show();
                    } while (result.moveToNext());
                }

            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Add Team | User");
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

    void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}
