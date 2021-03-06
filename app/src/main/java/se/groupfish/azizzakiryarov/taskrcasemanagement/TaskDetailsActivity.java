package se.groupfish.azizzakiryarov.taskrcasemanagement;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static http.NetworkState.isOnline;

public class TaskDetailsActivity extends AppCompatActivity {

    TextView title;
    TextView description;
    TextView state;
    TextView assignee;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        btnEdit = (Button) findViewById(R.id.btn_edit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline(TaskDetailsActivity.this)) {
                    Intent intent = new Intent(TaskDetailsActivity.this, EditAssigneeWorkItemsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TaskDetailsActivity.this, "You don't have Network...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        title = (TextView) findViewById(R.id.tv_title);
        description = (TextView) findViewById(R.id.tv_description);
        state = (TextView) findViewById(R.id.tv_state);
        assignee = (TextView) findViewById(R.id.tv_assignee);

        title.setText(getIntent().getStringExtra("title"));
        description.setText(getIntent().getStringExtra("description"));
        state.setText(getIntent().getStringExtra("state"));
        assignee.setText("User: " + getIntent().getStringExtra("assignee"));

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("TaskName");
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


