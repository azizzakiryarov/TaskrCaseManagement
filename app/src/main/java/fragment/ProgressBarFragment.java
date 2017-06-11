package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dbhelper.DatabaseHelper;
import http.HttpService;
import model.ProgressB;
import se.groupfish.azizzakiryarov.taskrcasemanagement.R;

import static http.NetworkState.isOnline;

public class ProgressBarFragment extends Fragment {

    private final static String USER_ID = "userId";
    private android.widget.ProgressBar unstartedItems;
    private android.widget.ProgressBar startedItems;
    private android.widget.ProgressBar doneItems;
    private android.widget.ProgressBar myItems;
    private List<ProgressB> progressBs;
    private CallBacks callBacks;
    HttpService httpService;
    DatabaseHelper databaseHelper;


    public static Fragment newInstance(String id) {
        Fragment fragment = new ProgressBarFragment();
        Bundle bundle = new Bundle();
        bundle.putString(USER_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface CallBacks {
        void onChartClicked(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callBacks = (ProgressBarFragment.CallBacks) context;

        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Hosting activity needs callBacks impl");
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_bar_fragment, container, false);

        databaseHelper = DatabaseHelper.getInstance(getContext());
        httpService = new HttpService();

        unstartedItems = (android.widget.ProgressBar) view.findViewById(R.id.unstarted_items);
        startedItems = (android.widget.ProgressBar) view.findViewById(R.id.started_items);
        doneItems = (android.widget.ProgressBar) view.findViewById(R.id.done_items);
        myItems = (android.widget.ProgressBar) view.findViewById(R.id.my_items);

        final TextView unstartedNumber = (TextView) view.findViewById(R.id.tv_unstarted_number);
        final TextView startedNumber = (TextView) view.findViewById(R.id.tv_started_number);
        final TextView doneNumber = (TextView) view.findViewById(R.id.tv_done_number);
        final TextView myItemsNumber = (TextView) view.findViewById(R.id.tv_my_items_number);

        final TextView unstartedTitle = (TextView) view.findViewById(R.id.tv_unstarted);
        final TextView startedTitle = (TextView) view.findViewById(R.id.tv_started);
        final TextView doneTitle = (TextView) view.findViewById(R.id.tv_done);
        final TextView myItemsTitle = (TextView) view.findViewById(R.id.tv_my_items);

        if (isOnline(getContext())) {
            unstartedNumber.setText(String.valueOf(httpService.getAllUnstarted().size()));
            startedNumber.setText(String.valueOf(httpService.getAllStarted().size()));
            doneNumber.setText(String.valueOf(httpService.getAllDone().size()));
            myItemsNumber.setText(String.valueOf(httpService.getAllWorkItemsByUserId(2L).size()));
        } else {
            unstartedNumber.setText(String.valueOf(databaseHelper.getAllUnstarted().size()));
            startedNumber.setText(String.valueOf(databaseHelper.getAllStarted().size()));
            doneNumber.setText(String.valueOf(databaseHelper.getAllDone().size()));
            myItemsNumber.setText(String.valueOf(databaseHelper.getAllMyTask().size()));
        }

        setMaxProgress();
        setProgress();

        final ProgressB progressBMyitems = new ProgressB(myItems, myItemsTitle, myItemsNumber);
        final ProgressB progressBUnstartedItems = new ProgressB(unstartedItems, unstartedTitle, unstartedNumber);
        final ProgressB progressBDoneItems = new ProgressB(doneItems, doneTitle, doneNumber);
        final ProgressB progressBStartedItems = new ProgressB(startedItems, startedTitle, startedNumber);

        progressBs = new ArrayList<>();

        progressBs.add(progressBUnstartedItems);
        progressBs.add(progressBStartedItems);
        progressBs.add(progressBDoneItems);
        progressBs.add(progressBMyitems);

        setIsActiveCharts(0);
        activeChart();
        unstartedItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBacks.onChartClicked(0);
                setIsActiveCharts(0);
                activeChart();
            }
        });

        startedItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBacks.onChartClicked(1);
                setIsActiveCharts(1);
                activeChart();
            }
        });

        doneItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBacks.onChartClicked(2);
                setIsActiveCharts(2);
                activeChart();
            }
        });

        myItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBacks.onChartClicked(3);
                setIsActiveCharts(3);
                activeChart();
            }
        });

        return view;
    }

    private void setMaxProgress() {
        if (isOnline(getContext())) {
            unstartedItems.setMax(httpService.getAllUnstarted().size());
            startedItems.setMax(httpService.getAllStarted().size());
            doneItems.setMax(httpService.getAllDone().size());
            myItems.setMax(httpService.getAllWorkItems().size());
        } else {
            unstartedItems.setMax(databaseHelper.getAllUnstarted().size());
            startedItems.setMax(databaseHelper.getAllStarted().size());
            doneItems.setMax(databaseHelper.getAllDone().size());
            myItems.setMax(databaseHelper.getAllMyTask().size());
        }
    }

    private void setProgress() {
        if (isOnline(getContext())) {
            unstartedItems.setProgress(httpService.getAllUnstarted().size());
            startedItems.setProgress(httpService.getAllStarted().size());
            doneItems.setProgress(httpService.getAllDone().size());
            myItems.setProgress(httpService.getAllWorkItems().size());
        } else {
            unstartedItems.setProgress(databaseHelper.getAllUnstarted().size());
            startedItems.setProgress(databaseHelper.getAllStarted().size());
            doneItems.setProgress(databaseHelper.getAllDone().size());
            myItems.setProgress(databaseHelper.getAllMyTask().size());
        }
    }

    public void activeChart() {

        for (ProgressB progressB : progressBs) {
            if (progressB.getProgressBar().isSelected()) {
                progressB.getProgressBar().setScaleX(1.1F);
                progressB.getProgressBar().setScaleY(1.1F);
                progressB.getNumber().setScaleX(1.1F);
                progressB.getNumber().setScaleY(1.1F);
                progressB.getTitle().setTextSize(14);
                progressB.getTitle().setTextColor(getResources().getColor(R.color.primary_orange));
            } else {
                progressB.getProgressBar().setScaleX(1.0F);
                progressB.getProgressBar().setScaleY(1.0F);
                progressB.getNumber().setScaleX(1.0F);
                progressB.getNumber().setScaleY(1.0F);
                progressB.getTitle().setTextSize(12);
                progressB.getTitle().setTextColor(getResources().getColor(R.color.primary_gray));
            }

        }
    }

    public void setIsActiveCharts(int position) {
        ProgressB progressB = progressBs.get(position);
        for (ProgressB c : progressBs) {
            if (c.equals(progressB)) {
                c.getProgressBar().setSelected(true);
            } else {
                c.getProgressBar().setSelected(false);
            }
        }
    }
}
