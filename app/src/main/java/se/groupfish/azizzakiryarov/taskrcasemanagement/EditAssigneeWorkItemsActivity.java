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

public class EditAssigneeWorkItemsActivity extends AppCompatActivity {

    private static final String TAG = "MyTags";

    HttpService httpService = new HttpService();

    Button btnPut;
    Button btnPost;
    EditText etURL;
    EditText etTitle;
    EditText etDescription;
    EditText etState;
    EditText etUser;
    EditText etIssue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignee_work_items);

        btnPut = (Button) findViewById(R.id.btn_edit_put);
        btnPost = (Button) findViewById(R.id.btn_edit_post);
        etURL = (EditText) findViewById(R.id.et_put_URL);
        etTitle = (EditText) findViewById(R.id.et_put_title);
        etDescription = (EditText) findViewById(R.id.et_put_description);
        etState = (EditText) findViewById(R.id.et_put_state);
        etUser = (EditText) findViewById(R.id.et_put_userId);
        etIssue = (EditText) findViewById(R.id.et_put_issueId);


        btnPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Long id = Long.valueOf(etURL.getText().toString());
                final String title = etTitle.getText().toString();
                final String description = etDescription.getText().toString();
                final String state = etState.getText().toString();
                final Long userId = Long.valueOf(etUser.getText().toString());


                //ONLINE
                if (!state.isEmpty()) {
                    httpService.updateWorkItemsState(id, state);
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "State is updated... ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "State not is updated... ", Toast.LENGTH_SHORT).show();
                }

                if (!etUser.getText().toString().isEmpty()) {
                    httpService.assigneeWorkItemToUser(id, userId);
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "User is assigneed... ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "User is assigneed... ", Toast.LENGTH_SHORT).show();
                }

                if (!title.isEmpty()) {
                    httpService.updateWorkItemsTitle(id, title);
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "Title is updated... ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "Title is not updated... ", Toast.LENGTH_SHORT).show();
                }

                if (!description.isEmpty()) {
                    httpService.updateWorkItemsDescription(id, description);
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "Description is updated... ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "Description is not updated... ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {

                final Long id = Long.valueOf(etURL.getText().toString());
                final String issue = etIssue.getText().toString();

                //ONLINE
                if (!issue.isEmpty()) {
                    httpService.addWorkItemToIssue(id, issue);
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "Issue is assigneed... ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditAssigneeWorkItemsActivity.this, "Issue is not assigneed... ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)

        {
            actionBar.setTitle("Edit | Assignee getAllWorkItems");
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
