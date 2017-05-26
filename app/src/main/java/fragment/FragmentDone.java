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

import java.util.List;

import http.HttpService;
import model.WorkItem;
import se.groupfish.azizzakiryarov.taskrcasemanagement.AddWorkItemsActivity;
import se.groupfish.azizzakiryarov.taskrcasemanagement.R;

public class FragmentDone extends Fragment {

    HttpService httpService = new HttpService();
    private FragmentDone.Callbacks callBacks;
    FloatingActionButton floatingActionButton;

    public interface Callbacks {
        void onListItemClicked(WorkItem workItem);
    }

    public static Fragment newInstance() {
        return new FragmentDone();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (callBacks != null) {
                callBacks = (FragmentDone.Callbacks) context;
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Hosting activity must implement callbacks");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_done, container, false);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setColorFilter(Color.parseColor("#FFFFFF"));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddWorkItemsActivity.class);
                startActivity(intent);
            }
        });


        FragmentDone.WorkItemListAdapter adapter = new FragmentDone.WorkItemListAdapter(httpService.getAllDone(),
                new FragmentDone.WorkItemListAdapter.OnItemClickedListener() {
                    @Override
                    public void onItemClicked(WorkItem workItem) {

                        if (callBacks != null) {
                            callBacks.onListItemClicked(workItem);
                        }

                    }
                });

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_view_DONE);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private static final class WorkItemListAdapter extends RecyclerView.Adapter<FragmentDone.WorkItemListAdapter.WorkItemViewHolder> {
        private final List<WorkItem> workItems;
        private final FragmentDone.WorkItemListAdapter.OnItemClickedListener onItemClickedListener;

        private WorkItemListAdapter(List<WorkItem> workItems, FragmentDone.WorkItemListAdapter.OnItemClickedListener onItemClickedListener) {
            this.workItems = workItems;
            this.onItemClickedListener = onItemClickedListener;
        }

        @Override
        public FragmentDone.WorkItemListAdapter.WorkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.row, parent, false);
            return new FragmentDone.WorkItemListAdapter.WorkItemViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FragmentDone.WorkItemListAdapter.WorkItemViewHolder holder, int position) {
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

            void bindView(final WorkItem workItem, final FragmentDone.WorkItemListAdapter.OnItemClickedListener onItemClickedListener) {
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
