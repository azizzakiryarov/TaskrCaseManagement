package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import adapter.PagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    ViewPager viewPager;
    ProgressBar pUnstarted;
    ProgressBar pStarted;
    ProgressBar pDone;
    ProgressBar pMyTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = (ViewPager) findViewById(R.id.pager);
        pUnstarted = (ProgressBar) findViewById(R.id.progressBar_UNSTARTED);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Taskr");
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
}
