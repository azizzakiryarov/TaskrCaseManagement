package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import http.HttpService;
import model.WorkItem;
import se.groupfish.azizzakiryarov.taskrcasemanagement.R;

public class FragmentDetails extends Fragment {

    private static final String BUNDLE_ARGS_ID = "args_id";
    private WorkItem workItem;

    public static Fragment newInstance(long id) {
        Fragment fragment = new FragmentDetails();
        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_ARGS_ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HttpService httpService = new HttpService();
        long id = getArguments().getLong(BUNDLE_ARGS_ID);
        workItem = httpService.getWorkItemById(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_task_details, container, false);

        EditText etTitle = (EditText) view.findViewById(R.id.task_title);
        EditText etDescription = (EditText) view.findViewById(R.id.task_description);
        EditText etStatus = (EditText) view.findViewById(R.id.task_status);
       // EditText etAssignee = (EditText) view.findViewById(R.id.task_assignee);

        if (workItem != null) {
            etTitle.setText(workItem.getTitle());
            etDescription.setText(workItem.getDescription());
            etStatus.setText(workItem.getState());
            //assignee setText
        }
        return view;
    }
}
