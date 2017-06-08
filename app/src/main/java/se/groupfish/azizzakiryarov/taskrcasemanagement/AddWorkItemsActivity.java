package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.Intent;
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
import http.HttpService;

public class AddWorkItemsActivity extends AppCompatActivity {

    private final String TAG = AddWorkItemsActivity.class.getSimpleName();
    HttpService httpService = new HttpService();
    DatabaseHelper databaseHelper;

    Button btnPost;
    Button btnPut;

    EditText etTitle;
    EditText etDescription;
    EditText etState;
    EditText etUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_items);

        databaseHelper = new DatabaseHelper(this);

        btnPost = (Button) findViewById(R.id.btn_post);
        btnPut = (Button) findViewById(R.id.btn_put);

        etTitle = (EditText) findViewById(R.id.et_title);
        etDescription = (EditText) findViewById(R.id.et_description);
        etState = (EditText) findViewById(R.id.et_state);

        btnPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddWorkItemsActivity.this, EditAssigneeWorkItemsActivity.class);
                startActivity(intent);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = etTitle.getText().toString();
                String description = etDescription.getText().toString();
                String state = etState.getText().toString();
                Long userId = Long.valueOf(etUserId.getText().toString());


                //ONLINE
                httpService.addWorkItem(title, description, state);
                Toast.makeText(AddWorkItemsActivity.this, "WorkItem is added to Server...", Toast.LENGTH_SHORT).show();

            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Add getAllWorkItems");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA500")));
        }
    }

    void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

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

