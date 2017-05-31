package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    DatabaseHelper db;

    Button btnPost;
    Button btnPut;
    Button btnGetAllWorkItems;

    EditText etTitle;
    EditText etDescription;
    EditText etState;
    EditText etUserId;
    EditText etIssueId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_items);

        db = new DatabaseHelper(this);

        btnPost = (Button) findViewById(R.id.btn_post);
        btnPut = (Button) findViewById(R.id.btn_put);

        btnGetAllWorkItems = (Button) findViewById(R.id.btn_getAll);
        etTitle = (EditText) findViewById(R.id.et_title);
        etDescription = (EditText) findViewById(R.id.et_description);
        etState = (EditText) findViewById(R.id.et_state);
        etUserId = (EditText) findViewById(R.id.et_userId);
        etIssueId = (EditText) findViewById(R.id.et_issueId);

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
                Long issueId = Long.valueOf(etIssueId.getText().toString());

                httpService.addWorkItem(title, description, state);
                Toast.makeText(AddWorkItemsActivity.this, "WorkItem is added...", Toast.LENGTH_SHORT).show();


                db.addWorkItem(title, description, state, userId, issueId);
                Toast.makeText(AddWorkItemsActivity.this, "WorkItem is added...", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: WorkItem added");


            }
        });

        btnGetAllWorkItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get all workitems
            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Add workItems");
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

