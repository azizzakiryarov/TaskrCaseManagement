package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;

import adapter.PagerAdapter;
import fragment.FragmentUnstarted;
import http.HttpService;
import model.WorkItem;

public class HomeActivity extends AppCompatActivity implements FragmentUnstarted.Callbacks {

    private static final String TAG = HomeActivity.class.getSimpleName();

    ViewPager viewPager;
    ProgressBar pUnstarted;
    ProgressBar pStarted;
    ProgressBar pDone;
    ProgressBar pMyTask;
    ListView lvUnstarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = (ViewPager) findViewById(R.id.pager);

        pUnstarted = (ProgressBar) findViewById(R.id.UNSTARTED);
        pStarted = (ProgressBar) findViewById(R.id.STARTED);
        pDone = (ProgressBar) findViewById(R.id.DONE);
        pMyTask = (ProgressBar) findViewById(R.id.MyTask);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Taskr");
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item != null) {
            int id = item.getItemId();
            if (id == R.id.action_refresh) {

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClicked(WorkItem workItem) {
        Intent intent = TaskDetailsActivity.createIntent(this, workItem);
        startActivity(intent);
    }

}
