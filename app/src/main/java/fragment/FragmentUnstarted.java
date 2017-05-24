package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import http.HttpService;
import model.WorkItem;
import se.groupfish.azizzakiryarov.taskrcasemanagement.R;

public class FragmentUnstarted extends Fragment {

    HttpService httpService = new HttpService();
    private Callbacks callBacks;

    public interface Callbacks {
        void onListItemClicked(WorkItem workItem);
    }

    public static Fragment newInstance() {
        return new FragmentUnstarted();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callBacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Hosting activity must implement callbacks");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //???
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_unstarted, container, false);

        WorkItemListAdapter adapter = new WorkItemListAdapter(httpService.getAllUnstarted(),
                new WorkItemListAdapter.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(WorkItem workItem) {

                        if (callBacks != null) {
                            callBacks.onListItemClicked(workItem);
                        }

                    }
                });

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view_UNSTARTED);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private static final class WorkItemListAdapter extends RecyclerView.Adapter<WorkItemListAdapter.WorkItemViewHolder> {
        private final List<WorkItem> workItems;
        private final OnItemClickedListener onItemClickedListener;

        private WorkItemListAdapter(List<WorkItem> workItems, OnItemClickedListener onItemClickedListener) {
            this.workItems = workItems;
            this.onItemClickedListener = onItemClickedListener;
        }

        @Override
        public WorkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.row, parent, false);
            return new WorkItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(WorkItemViewHolder holder, int position) {
            WorkItem workItem = workItems.get(position);
            holder.bindView(workItem, onItemClickedListener);
        }

        @Override
        public int getItemCount() {
            return workItems.size();
        }

        public interface OnItemClickedListener {
            void onItemClicked(WorkItem workItem);
        }

        static final class WorkItemViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvTitle;
            private final TextView tvDescription;
            private final TextView tvState;


            WorkItemViewHolder(View itemView) {
                super(itemView);
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
                tvState = (TextView) itemView.findViewById(R.id.tvState);
            }

            void bindView(final WorkItem workItem, final OnItemClickedListener onItemClickedListener) {
                tvTitle.setText(workItem.getTitle());
                tvDescription.setText(workItem.getDescription());
                tvState.setText(workItem.getState());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickedListener != null) {
                            onItemClickedListener.onItemClicked(workItem);
                        }

                    }
                });
            }
        }
    }
}
