package adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import model.WorkItem;
import se.groupfish.azizzakiryarov.taskrcasemanagement.R;

public class WorkItemListAdapter extends RecyclerView.Adapter<WorkItemListAdapter.WorkItemViewHolder> {
    ArrayList<WorkItem> workItems;

    public WorkItemListAdapter(ArrayList<WorkItem> workItems) {
        this.workItems = workItems;
    }

    @Override
    public WorkItemListAdapter.WorkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new WorkItemListAdapter.WorkItemViewHolder(view, workItems);

    }

    @Override
    public void onBindViewHolder(WorkItemListAdapter.WorkItemViewHolder holder, int position) {
        WorkItem workItem = workItems.get(position);
        holder.bindView(workItem);
    }

    @Override
    public int getItemCount() {
        return workItems.size();
    }

    public void setFilter(ArrayList<WorkItem> newList) {
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