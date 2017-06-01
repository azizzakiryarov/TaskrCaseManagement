package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


import fragment.EditWorkItemFragment;
import http.HttpService;
import model.WorkItem;

public class TaskDetailsActivity extends AppCompatActivity {


    private static final String EXTRA_WORKITEM_ID = "workitem_id";
    HttpService httpService = new HttpService();


    public static Intent createIntent(Context context, WorkItem workItem) {
        Intent intent = new Intent(context, TaskDetailsActivity.class);
        intent.putExtra(EXTRA_WORKITEM_ID, workItem.getId());

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("TaskName");
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA500")));
        }



        Intent intent = getIntent();
        final long id = intent.getLongExtra(EXTRA_WORKITEM_ID, -1);

    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWorkItemEdited(WorkItem workItem) {

        repository.addOrUpdateWorkItem(workItem);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public WorkItem getWorkItem(long id) {
        return repository.getWorkItem(id);
    }
    */
}


