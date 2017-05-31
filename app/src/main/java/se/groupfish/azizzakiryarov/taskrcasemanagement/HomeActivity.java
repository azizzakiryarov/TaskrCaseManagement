package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import adapter.PagerAdapter;
import dbhelper.DatabaseHelper;
import fragment.FragmentDone;
import fragment.FragmentMyTask;
import fragment.FragmentStarted;
import fragment.FragmentUnstarted;
import model.WorkItem;

public class HomeActivity extends AppCompatActivity implements FragmentUnstarted.Callbacks, View.OnClickListener {

    DatabaseHelper databaseHelper;

    ViewPager viewPager;
    SearchView searchView;

    ProgressBar pUnstarted;
    ProgressBar pStarted;
    ProgressBar pDone;
    ProgressBar pMytask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        databaseHelper = new DatabaseHelper(this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        pUnstarted = (ProgressBar) findViewById(R.id.UNSTARTED);
        pStarted = (ProgressBar) findViewById(R.id.STARTED);
        pDone = (ProgressBar) findViewById(R.id.DONE);
        pMytask = (ProgressBar) findViewById(R.id.MyTask);


        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Taskr");
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA500")));
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Do you want to exit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
                LogInActivity.logInActivity.finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    // This is not working...
    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.UNSTARTED:
                Intent intent1 = new Intent(HomeActivity.this, FragmentUnstarted.class);
                startActivity(intent1);
            case R.id.STARTED:
                Intent intent2 = new Intent(HomeActivity.this, FragmentStarted.class);
                startActivity(intent2);
            case R.id.DONE:
                Intent intent3 = new Intent(HomeActivity.this, FragmentDone.class);
                startActivity(intent3);
            case R.id.MyTask:
                Intent intent4 = new Intent(HomeActivity.this, FragmentMyTask.class);
                startActivity(intent4);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //actionSearchView(menu);        This function will work when vi will create DB
        // link för att forsätta         https://www.youtube.com/watch?v=Aa3m7jrtudI#t=106.187586

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void actionSearchView(Menu menu) {

        MenuItem menuItem = menu.findItem(R.id.search_bar);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, HomeActivity.class)));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item != null) {
            int id = item.getItemId();
            switch (id) {
                case R.id.team_details:
                    Intent intent = new Intent(this, TeamDetailsActivity.class);
                    startActivity(intent);
                case R.id.logout:
                    // logout
                case R.id.search_bar:
                    //search
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