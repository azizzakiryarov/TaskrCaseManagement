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

import http.HttpService;
import model.WorkItem;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView searchView;
    ArrayList<WorkItem> workItems = new ArrayList<>();
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

        final SearchActivity.WorkItemListAdapter adapter = new SearchActivity.WorkItemListAdapter((ArrayList<WorkItem>) httpService.getAllMyTask());
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.searchRecycleview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 50);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    private static final class WorkItemListAdapter extends RecyclerView.Adapter<SearchActivity.WorkItemListAdapter.WorkItemViewHolder> {
        ArrayList<WorkItem> workItems;


        private WorkItemListAdapter(ArrayList<WorkItem> workItems) {
            this.workItems = workItems;
        }

        @Override
        public SearchActivity.WorkItemListAdapter.WorkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new SearchActivity.WorkItemListAdapter.WorkItemViewHolder(view, workItems);

        }

        @Override
        public void onBindViewHolder(SearchActivity.WorkItemListAdapter.WorkItemViewHolder holder, int position) {
            WorkItem workItem = workItems.get(position);
            holder.bindView(workItem);
        }

        @Override
        public int getItemCount() {
            return workItems.size();
        }

        static final class WorkItemViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvTitle;
            private final TextView tvDescription;
            private final TextView tvState;
            ArrayList<WorkItem> workItems = new ArrayList<>();


            WorkItemViewHolder(View itemView, ArrayList<WorkItem> workItems) {
                super(itemView);

                this.workItems = workItems;

                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
                tvState = (TextView) itemView.findViewById(R.id.tvState);
            }

            void bindView(final WorkItem workItem) {
                tvTitle.setText(workItem.getTitle());
                tvDescription.setText(workItem.getDescription());
                tvState.setText(workItem.getState());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.searchBar);
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


    public void setFilter(ArrayList<WorkItem> newList) {

        workItems = new ArrayList<WorkItem>();
        workItems.addAll(newList);

    }


}
