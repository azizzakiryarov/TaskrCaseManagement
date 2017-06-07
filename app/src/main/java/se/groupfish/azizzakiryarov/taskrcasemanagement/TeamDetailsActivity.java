package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import http.HttpService;
import model.Team;
import model.User;
import model.WorkItem;

public class TeamDetailsActivity extends AppCompatActivity {

    HttpService httpService;
    Button btnOverview;
    Button btnEdit;
    TextView tvTeamName;
    TextView tvDescription;
    ArrayList<User> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        tvTeamName = (TextView) findViewById(R.id.team_name);
        tvDescription = (TextView) findViewById(R.id.team_description);

        httpService = new HttpService();

        Team team = httpService.getTeamById(1l);

        tvTeamName.setText(team.getTeamName());
        tvDescription.setText("GroupFish was created by Emil Lindblom");

        btnOverview = (Button) findViewById(R.id.btn_overview);
        btnEdit = (Button) findViewById(R.id.btn_edit_team);

        btnOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage("SERVER", getAllOverview(1l).toString());
            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("TeamName");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA500")));
        }

        final UsersListAdapter adapter = new UsersListAdapter((ArrayList<User>) httpService.getAllUsersByTeamId(1l));
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Members);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public StringBuffer getAllOverview(Long id) {

        List<WorkItem> getAllWorkItemsByTeamId = httpService.getAllWorkItemsByTeamId(id);
        StringBuffer stringBuffer = new StringBuffer();
        for (WorkItem workItems : getAllWorkItemsByTeamId) {
            stringBuffer.append("Title : " + workItems.getTitle() + "\n");
            stringBuffer.append("Description : " + workItems.getDescription() + "\n");
            stringBuffer.append("State : " + workItems.getState() + "\n");
            stringBuffer.append("UserId : " + workItems.getUserId() + "\n");
            stringBuffer.append("IssueId : " + workItems.getIssueId() + "\n\n");
        }
        return stringBuffer;
    }

    void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    private final class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersViewHolder> {
        ArrayList<User> users;

        private UsersListAdapter(ArrayList<User> users) {
            this.users = users;
        }

        @Override
        public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_members_list, parent, false);
            return new UsersViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UsersListAdapter.UsersViewHolder holder, int position) {
            User user = users.get(position);
            holder.bindView(user);
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        final class UsersViewHolder extends RecyclerView.ViewHolder {

            private final TextView tvFirstName;
            private final TextView tvLastName;
            private final TextView tvUserName;

            UsersViewHolder(View itemView) {
                super(itemView);
                this.tvFirstName = (TextView) itemView.findViewById(R.id.tvFirstName);
                this.tvLastName = (TextView) itemView.findViewById(R.id.tvLastName);
                this.tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            }

            void bindView(User user) {
                tvFirstName.setText(user.getFirstName());
                tvLastName.setText(user.getLastName());
                tvUserName.setText(user.getUserName());
            }
        }
    }
}
