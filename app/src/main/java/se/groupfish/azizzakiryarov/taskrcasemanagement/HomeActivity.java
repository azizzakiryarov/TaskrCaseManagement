package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import dbhelper.DatabaseHelper;
import fragment.FragmentDone;
import fragment.FragmentMyTask;
import fragment.FragmentStarted;
import fragment.FragmentUnstarted;
import fragment.ProgressBarFragment;

import static http.NetworkState.isOnline;

public class HomeActivity extends AppCompatActivity implements ProgressBarFragment.CallBacks {

    DatabaseHelper databaseHelper;
    ViewPager viewPager;
    FloatingActionButton floatingActionButton;
    FragmentManager fm;
    ProgressBarFragment progressBarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.workitem_list_container);

        if (fragment == null) {
            progressBarFragment = (ProgressBarFragment) ProgressBarFragment.newInstance();
            fm.beginTransaction()
                    .replace(R.id.chart_fragment, progressBarFragment)
                    .commit();
        }

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setColorFilter(Color.parseColor("#FFFFFF"));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline(HomeActivity.this)) {
                    Intent intent = new Intent(HomeActivity.this, AddWorkItemsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "You don't have Network...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewPager = (ViewPager) findViewById(R.id.vp_workitem_list);
        viewPager.setAdapter(new CustomAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                progressBarFragment.setIsActiveCharts(position);
                progressBarFragment.activeChart();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        databaseHelper = new DatabaseHelper(this);

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

    @Override
    public void onChartClicked(int position) {
        viewPager.setCurrentItem(position);
    }

    private class CustomAdapter extends FragmentPagerAdapter {
        CustomAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new FragmentUnstarted();
                case 1:
                    return new FragmentStarted();
                case 2:
                    return new FragmentDone();
                case 3:
                    return new FragmentMyTask();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
