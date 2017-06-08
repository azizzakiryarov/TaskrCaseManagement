package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import adapter.PagerAdapter;
import dbhelper.DatabaseHelper;

public class HomeActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ViewPager viewPager;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item != null) {
            int id = item.getItemId();
            switch (id) {
                case R.id.searchBar:
                    Intent intent = new Intent(this, SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.team_details:
                    Intent intent2 = new Intent(this, TeamDetailsActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.logout:
                    onBackPressed();
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}