package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

import fragment.FragmentDetails;
import http.HttpService;
import model.WorkItem;

public class TaskDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_WORKITEM_ID = "contact_id";

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
        }

        Intent startingIntent = getIntent();
        long id = startingIntent.getLongExtra(EXTRA_WORKITEM_ID, 0);

        HttpService httpService = new HttpService();
        WorkItem workItem = httpService.getWorkItemById(id);
        List<WorkItem> workItems = httpService.getAllUnstarted();

        int positionOfSelectedItem = workItems.indexOf(workItem);

        //final ViewPager viewPager = (ViewPager) findViewById(R.id.detail_fragment_view_pager);
        //viewPager.setAdapter(new WorkItemPagerAdapter(getSupportFragmentManager(), workItems));
        //viewPager.setCurrentItem(positionOfSelectedItem);
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

    private static final class WorkItemPagerAdapter extends FragmentStatePagerAdapter {
        private final List<WorkItem> workItems;

        WorkItemPagerAdapter(FragmentManager fragmentManager, List<WorkItem> workItems) {
            super(fragmentManager);
            this.workItems = workItems;
        }

        @Override
        public Fragment getItem(int position) {
            long id = workItems.get(position).getId();
            return FragmentDetails.newInstance(id);
        }

        @Override
        public int getCount() {
            return workItems.size();
        }
    }
}


