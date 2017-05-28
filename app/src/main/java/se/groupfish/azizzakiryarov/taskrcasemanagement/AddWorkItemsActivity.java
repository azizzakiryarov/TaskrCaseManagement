package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import http.HttpService;

public class AddWorkItemsActivity extends AppCompatActivity {

    HttpService httpService = new HttpService();

    Button btnPost;
    Button btnPut;
    Button btnGetAllWorkItems;

    EditText etTitle;
    EditText etDescription;
    EditText etState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_items);

        btnPost = (Button) findViewById(R.id.btn_post);
        btnPut = (Button) findViewById(R.id.btn_put);

        btnGetAllWorkItems = (Button) findViewById(R.id.btn_getAll);
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

                String title = etTitle.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String state = etState.getText().toString().trim();

                httpService.addWorkItem(title, description, state);

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

