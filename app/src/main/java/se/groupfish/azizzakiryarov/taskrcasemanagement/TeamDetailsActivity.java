package se.groupfish.azizzakiryarov.taskrcasemanagement;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import dbhelper.DBContract;
import dbhelper.DatabaseHelper;
import http.ApiRepository;
import http.HttpService;
import model.Team;
import model.User;
import model.WorkItem;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static http.NetworkState.isOnline;

public class TeamDetailsActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static final Gson gson = new GsonBuilder().create();

    SQLiteDatabase db = null;
    DatabaseHelper databaseHelper;
    HttpService httpService;
    Button btnOverview;
    Button btnSaveAllInMemory;
    Button btnEdit;
    TextView tvTeamName;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        tvTeamName = (TextView) findViewById(R.id.team_name);
        tvDescription = (TextView) findViewById(R.id.team_description);

        httpService = new HttpService();
        databaseHelper = new DatabaseHelper(TeamDetailsActivity.this);

        setTeamNameOnline();

        tvDescription.setText("GroupFish is a best");

        btnOverview = (Button) findViewById(R.id.btn_overview);
        btnEdit = (Button) findViewById(R.id.btn_edit_team);
        btnSaveAllInMemory = (Button) findViewById(R.id.btn_save_all_in_SQLite);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline(TeamDetailsActivity.this)) {
                    Intent intent = new Intent(TeamDetailsActivity.this, EditTeamActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(TeamDetailsActivity.this, "You don't have Network...", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline(TeamDetailsActivity.this)) {
                    showMessage("SERVER", getAllOverview(1L).toString());
                    showMessage("SQLite", databaseHelper.getAllWorkItemsFromSQLite().toString());
                } else {
                    showMessage("SQLite", databaseHelper.getAllWorkItemsFromSQLite().toString());
                }
            }
        });

        btnSaveAllInMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline(TeamDetailsActivity.this)) {
                    databaseHelper.saveAllWorkItemsInSQLite();
                    Toast.makeText(TeamDetailsActivity.this, "Your WorkItems is saved successfully...", Toast.LENGTH_LONG).show();
                    databaseHelper.saveAllUsersByTeamIdInSQLite();
                    Toast.makeText(TeamDetailsActivity.this, "Your Users is saved successfully...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TeamDetailsActivity.this, "You don't have Network...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle("TeamName");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFA500")));
        }

        if (isOnline(TeamDetailsActivity.this)) {
            final UsersListAdapter adapter = new UsersListAdapter((ArrayList<User>) httpService.getAllUsersByTeamId(1L));
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Members);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            }, 50);
        } else {

            final UsersListAdapter adapter = new UsersListAdapter((ArrayList<User>) DatabaseHelper.getInstance(this).getAllUsersByTeamId());
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

    void setTeamNameOnline() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<Team> call = service.getTeamById(1L);

        call.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Response<Team> response, Retrofit retrofit) {

                if (isOnline(TeamDetailsActivity.this)) {
                    Team team = response.body();
                    tvTeamName.setText(team.getTeamName());

                    db = DatabaseHelper.getInstance(TeamDetailsActivity.this).getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("teamName", team.getTeamName());
                    db.insert(DBContract.TeamsEntry.TABLE_NAME, null, contentValues);
                    Toast.makeText(TeamDetailsActivity.this, "Saved teamName in SQLite", Toast.LENGTH_SHORT).show();


                } else {
                    tvTeamName.setText(DatabaseHelper.getInstance(TeamDetailsActivity.this).getTeamName());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("FAILURE", t.getMessage(), t);
            }
        });
    }

    public StringBuffer getAllOverview(Long id) {

        List<WorkItem> getAllWorkItemsByTeamId = httpService.getAllWorkItemsByTeamId(id);
        StringBuffer stringBuffer = new StringBuffer();
        for (WorkItem workItems : getAllWorkItemsByTeamId) {
            stringBuffer.append("Title : ").append(workItems.getTitle()).append("\n");
            stringBuffer.append("Description : ").append(workItems.getDescription()).append("\n");
            stringBuffer.append("State : ").append(workItems.getState()).append("\n");
            stringBuffer.append("UserId : ").append(workItems.getUserId()).append("\n");
            stringBuffer.append("IssueId : ").append(workItems.getIssueId()).append("\n\n");
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
