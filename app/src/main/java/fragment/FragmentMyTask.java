package fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dbhelper.DatabaseHelper;
import http.HttpService;
import model.WorkItem;
import se.groupfish.azizzakiryarov.taskrcasemanagement.R;
import se.groupfish.azizzakiryarov.taskrcasemanagement.TaskDetailsActivity;

import static http.NetworkState.isOnline;

public class FragmentMyTask extends Fragment {

    HttpService httpService = new HttpService();
    ArrayList<WorkItem> workItemsOnline;
    ArrayList<WorkItem> workItemsOffline;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_task, container, false);

        if (isOnline(getContext())) {
            workItemsOnline = (ArrayList<WorkItem>) httpService.getAllWorkItemsByUserId(2L);
            final WorkItemListAdapter adapter = new WorkItemListAdapter(workItemsOnline, getContext());
            final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view_MYTASK);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();

                }
            }, 50);


        } else {

            workItemsOffline = (ArrayList<WorkItem>) DatabaseHelper.getInstance(getContext()).getAllMyTask();
            final WorkItemListAdapter adapter = new WorkItemListAdapter(workItemsOffline, getContext());
            final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view_MYTASK);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();

                }
            }, 50);

        }

        return view;
    }

    private static final class WorkItemListAdapter extends RecyclerView.Adapter<FragmentMyTask.WorkItemListAdapter.WorkItemViewHolder> {
        ArrayList<WorkItem> workItems;
        Context ctx;


        private WorkItemListAdapter(ArrayList<WorkItem> workItems, Context ctx) {

            this.workItems = workItems;
            this.ctx = ctx;
        }

        @Override
        public FragmentMyTask.WorkItemListAdapter.WorkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new WorkItemViewHolder(view, ctx, workItems);

        }

        @Override
        public void onBindViewHolder(FragmentMyTask.WorkItemListAdapter.WorkItemViewHolder holder, int position) {
            WorkItem workItem = workItems.get(position);
            holder.bindView(workItem);
        }

        @Override
        public int getItemCount() {
            return workItems.size();
        }

        static final class WorkItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private final TextView tvTitle;
            private final TextView tvDescription;
            private final TextView tvState;
            private final TextView tvAssignee;
            ArrayList<WorkItem> workItems = new ArrayList<>();
            Context ctx;


            WorkItemViewHolder(View itemView, Context ctx, ArrayList<WorkItem> workItems) {
                super(itemView);

                itemView.setOnClickListener(this);
                this.workItems = workItems;
                this.ctx = ctx;

                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
                tvState = (TextView) itemView.findViewById(R.id.tvState);
                tvAssignee = (TextView) itemView.findViewById(R.id.tvAssignee);
            }

            void bindView(final WorkItem workItem) {
                tvTitle.setText(workItem.getTitle());
                tvDescription.setText(workItem.getDescription());
                tvState.setText(workItem.getState());
                tvState.setTextColor(Color.parseColor("#FFFFFF"));
                if (tvState.getText().toString().contains("Unstarted")) {
                    tvState.setBackgroundColor(Color.parseColor("#979797"));
                } else if (tvState.getText().toString().contains("Started")) {
                    tvState.setBackgroundColor(Color.parseColor("#c67100"));
                } else if (tvState.getText().toString().contains("Done")) {
                    tvState.setBackgroundColor(Color.parseColor("#7ED321"));
                }
                tvAssignee.setText("User: " + String.valueOf(workItem.getUserId()));
            }

            @Override
            public void onClick(View v) {

                int position = getAdapterPosition();
                WorkItem workItem = this.workItems.get(position);
                Intent intent = new Intent(this.ctx, TaskDetailsActivity.class);
                intent.putExtra("title", workItem.getTitle());
                intent.putExtra("description", workItem.getDescription());
                intent.putExtra("state", workItem.getState());
                intent.putExtra("assignee", String.valueOf(workItem.getUserId()));
                this.ctx.startActivity(intent);

            }
        }
    }
}
