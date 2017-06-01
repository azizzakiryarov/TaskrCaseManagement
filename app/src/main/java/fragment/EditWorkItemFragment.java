package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import model.WorkItem;
import se.groupfish.azizzakiryarov.taskrcasemanagement.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class EditWorkItemFragment extends Fragment implements TextWatcher {

    private static final String ARGS_WORKITEM_ID = "workItem_id";

    public interface Callbacks {
        void onWorkItemEdited(WorkItem workItem);

        WorkItem getWorkItem(long id);
    }

    public static EditWorkItemFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(ARGS_WORKITEM_ID, id);
        EditWorkItemFragment instance = new EditWorkItemFragment();
        instance.setArguments(args);

        return instance;
    }

    private Button editButton;
    private Callbacks callbacks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_task_details, container, false);

        Bundle args = getArguments();
        final long id = args.getLong(ARGS_WORKITEM_ID);
        final WorkItem workItem = callbacks.getWorkItem(id);

        final EditText titleEditText = (EditText) view.findViewById(R.id.edit_title);
        final EditText descriptionEditText = (EditText) view.findViewById(R.id.edit_description);
        final EditText stateEditText = (EditText) view.findViewById(R.id.edit_description);
        final EditText assigneeEditText = (EditText) view.findViewById(R.id.edit_assignee);
        editButton = (Button) view.findViewById(R.id.edit_workitem);

        titleEditText.addTextChangedListener(this);

        titleEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager methodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = titleEditText.getText().toString();
                final String description = descriptionEditText.getText().toString();
                final String state = stateEditText.getText().toString();

                final WorkItem newOrEditedWorkItem = workItem == null ? new WorkItem(title, description, state) : new WorkItem(workItem.getId(), title, description, state);

                callbacks.onWorkItemEdited(newOrEditedWorkItem);
            }
        });

        if (workItem != null) {
            titleEditText.setText(workItem.getTitle());
            descriptionEditText.setText(workItem.getDescription());
            stateEditText.setText(workItem.getState());
            editButton.setText(getString(R.string.btn_edit_workitem));
        } else {
            editButton.setText(getString(R.string.action_add_workitem));
            editButton.setEnabled(false);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Hosting activity has to implement interface: " + Callbacks.class.getCanonicalName());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        final String ageString = s.toString().trim();

        try {
            Integer.parseInt(ageString);
            editButton.setEnabled(true);
        } catch (NumberFormatException e) {
            editButton.setEnabled(false);
        }
    }
}
