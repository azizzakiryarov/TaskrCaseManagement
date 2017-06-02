package fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import http.HttpService;
import model.WorkItem;
import se.groupfish.azizzakiryarov.taskrcasemanagement.AddWorkItemsActivity;
import se.groupfish.azizzakiryarov.taskrcasemanagement.R;
import se.groupfish.azizzakiryarov.taskrcasemanagement.TaskDetailsActivity;

public class FragmentStarted extends Fragment {

    HttpService httpService = new HttpService();
    FloatingActionButton floatingActionButton;


    public static Fragment newInstance() {
        return new FragmentUnstarted();
    }

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

        View view = inflater.inflate(R.layout.fragment_started, container, false);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setColorFilter(Color.parseColor("#FFFFFF"));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddWorkItemsActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<WorkItem> workItems = (ArrayList<WorkItem>) httpService.getAllStarted();

        FragmentStarted.WorkItemListAdapter adapter = new FragmentStarted.WorkItemListAdapter(workItems, getContext());
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view_STARTED);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private static final class WorkItemListAdapter extends RecyclerView.Adapter<FragmentStarted.WorkItemListAdapter.WorkItemViewHolder> {
        ArrayList<WorkItem> workItems;
        Context ctx;


        private WorkItemListAdapter(ArrayList<WorkItem> workItems, Context ctx) {

            this.workItems = workItems;
            this.ctx = ctx;
        }

        @Override
        public FragmentStarted.WorkItemListAdapter.WorkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new WorkItemViewHolder(view, ctx, workItems);

        }

        @Override
        public void onBindViewHolder(FragmentStarted.WorkItemListAdapter.WorkItemViewHolder holder, int position) {
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
            }

            void bindView(final WorkItem workItem) {
                tvTitle.setText(workItem.getTitle());
                tvDescription.setText(workItem.getDescription());
                tvState.setText(workItem.getState());
            }

            @Override
            public void onClick(View v) {

                int position = getAdapterPosition();
                WorkItem workItem = this.workItems.get(position);
                Intent intent = new Intent(this.ctx, TaskDetailsActivity.class);
                intent.putExtra("title", workItem.getTitle());
                intent.putExtra("description", workItem.getDescription());
                intent.putExtra("state", workItem.getState());
                this.ctx.startActivity(intent);

            }
        }
    }
}
