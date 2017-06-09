package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dbhelper.DatabaseHelper;
import http.HttpService;
import model.WorkItem;

import static http.NetworkState.isOnline;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView searchView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ListAdapter adapter;
    ArrayList<WorkItem> getAllWorkItemsOnline;
    ArrayList<WorkItem> getAllWorkItemsOffline;

    HttpService httpService = new HttpService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("Search WorkItems");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA500")));
        }

        if (isOnline(SearchActivity.this)) {
            getAllWorkItemsOnline = (ArrayList<WorkItem>) httpService.getAllWorkItems();
            recyclerView = (RecyclerView) findViewById(R.id.searchRecycleview);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new ListAdapter(getAllWorkItemsOnline);
            recyclerView.setAdapter(adapter);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            }, 50);

        } else {

            getAllWorkItemsOffline = (ArrayList<WorkItem>) DatabaseHelper.getInstance(SearchActivity.this).getAllWorkItemsFromSQLite();
            recyclerView = (RecyclerView) findViewById(R.id.searchRecycleview);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new ListAdapter(getAllWorkItemsOffline);
            recyclerView.setAdapter(adapter);


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            }, 50);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_bar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchByWorkItems);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (isOnline(SearchActivity.this)) {
            newText = newText.toLowerCase();
            ArrayList<WorkItem> newListWithTitle = new ArrayList<>();
            for (WorkItem workItems : getAllWorkItemsOnline) {
                String title = workItems.getTitle().toLowerCase();
                if (title.contains(newText)) {
                    newListWithTitle.add(workItems);
                }
            }
            adapter.setFilter(newListWithTitle);
            return true;

        } else {

            newText = newText.toLowerCase();
            ArrayList<WorkItem> newListWithTitle = new ArrayList<>();
            for (WorkItem workItems : getAllWorkItemsOffline) {
                String title = workItems.getTitle().toLowerCase();
                if (title.contains(newText)) {
                    newListWithTitle.add(workItems);
                }
            }
            adapter.setFilter(newListWithTitle);
            return true;
        }
    }

    private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.WorkItemViewHolder> {
        ArrayList<WorkItem> workItems;

        private ListAdapter(ArrayList<WorkItem> workItems) {
            this.workItems = workItems;
        }

        @Override
        public ListAdapter.WorkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ListAdapter.WorkItemViewHolder(view, workItems);

        }

        @Override
        public void onBindViewHolder(ListAdapter.WorkItemViewHolder holder, int position) {
            WorkItem workItem = workItems.get(position);
            holder.bindView(workItem);
        }

        @Override
        public int getItemCount() {
            return workItems.size();
        }

        private void setFilter(ArrayList<WorkItem> newList) {
            workItems = new ArrayList<>();
            workItems.addAll(newList);
            notifyDataSetChanged();
        }

        static final class WorkItemViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvTitle;
            private final TextView tvDescription;
            private final TextView tvState;
            private final TextView tvAssignee;
            ArrayList<WorkItem> workItems = new ArrayList<>();


            WorkItemViewHolder(View itemView, ArrayList<WorkItem> workItems) {
                super(itemView);

                this.workItems = workItems;

                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
                tvState = (TextView) itemView.findViewById(R.id.tvState);
                if (tvState.getText().toString().contains("Unstarted")) {
                    tvState.setBackgroundColor(Color.parseColor("#979797"));
                } else if (tvState.getText().toString().contains("Started")) {
                    tvState.setBackgroundColor(Color.parseColor("#c67100"));
                } else if (tvState.getText().toString().contains("Done")) {
                    tvState.setBackgroundColor(Color.parseColor("#7ED321"));
                }
                tvAssignee = (TextView) itemView.findViewById(R.id.tvAssignee);
            }

            void bindView(final WorkItem workItem) {
                tvTitle.setText(workItem.getTitle());
                tvDescription.setText(workItem.getDescription());
                tvState.setText(workItem.getState());
                tvAssignee.setText("User: " + String.valueOf(workItem.getUserId()));
            }
        }
    }
}
