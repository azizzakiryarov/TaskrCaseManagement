package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import http.HttpService;
import model.WorkItem;
import se.groupfish.azizzakiryarov.taskrcasemanagement.R;

public class FragmentUnstarted extends Fragment {

    HttpService httpService = new HttpService();
    ListView lvUnstarted;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_unstarted, container, false);

        lvUnstarted = (ListView) view.findViewById(R.id.list_view_UNSTARTED);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        List<WorkItem> allUnstarted = httpService.getAllUnstarted();

        ArrayAdapter<WorkItem> workItemsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, allUnstarted);
        lvUnstarted.setAdapter(workItemsAdapter);

        super.onCreate(savedInstanceState);
    }
}


