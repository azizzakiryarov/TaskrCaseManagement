package http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import model.Issue;
import model.Team;
import model.User;
import model.WorkItem;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public final class HttpService {

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static final Gson gson = new GsonBuilder().create();
    private List<WorkItem> getWorkItemsList = new ArrayList<>();
    private List<User> getUsersList = new ArrayList<>();

    public List<WorkItem> getAllUnstarted() {

        final List<WorkItem> getWorkItemsListUnstarted = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<List<WorkItem>> call = service.getAllUnstarted("Unstarted");

        call.enqueue(new Callback<List<WorkItem>>() {
            @Override
            public void onResponse(Response<List<WorkItem>> response, Retrofit retrofit) {

                List<WorkItem> workItems = response.body();

                for (int i = 0; i < workItems.size(); i++) {
                    WorkItem workItem = new WorkItem();
                    workItem.setId(workItems.get(i).getId());
                    workItem.setTitle(workItems.get(i).getTitle());
                    workItem.setDescription(workItems.get(i).getDescription());
                    workItem.setState(workItems.get(i).getState());
                    workItem.setUserId(workItems.get(i).getUserId());
                    getWorkItemsListUnstarted.add(workItem);

                }


/*

                listener.onResult(workItems);
                */
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("FAILURE", t.getMessage(), t);
            }
        });

        return getWorkItemsListUnstarted;
    }

    public List<WorkItem> getAllStarted() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<List<WorkItem>> call = service.getAllStarted("Started");

        call.enqueue(new Callback<List<WorkItem>>() {
            @Override
            public void onResponse(Response<List<WorkItem>> response, Retrofit retrofit) {

                List<WorkItem> workItems = response.body();

                for (int i = 0; i < workItems.size(); i++) {

                    WorkItem workItem = new WorkItem();
                    workItem.setId(workItems.get(i).getId());
                    workItem.setTitle(workItems.get(i).getTitle());
                    workItem.setDescription(workItems.get(i).getDescription());
                    workItem.setState(workItems.get(i).getState());
                    workItem.setUserId(workItems.get(i).getUserId());
                    getWorkItemsList.add(workItem);

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return getWorkItemsList;
    }

    public List<WorkItem> getAllDone() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<List<WorkItem>> call = service.getAllDone("Done");

        call.enqueue(new Callback<List<WorkItem>>() {
            @Override
            public void onResponse(Response<List<WorkItem>> response, Retrofit retrofit) {

                List<WorkItem> workItems = response.body();

                for (int i = 0; i < workItems.size(); i++) {

                    WorkItem workItem = new WorkItem();
                    workItem.setId(workItems.get(i).getId());
                    workItem.setTitle(workItems.get(i).getTitle());
                    workItem.setDescription(workItems.get(i).getDescription());
                    workItem.setState(workItems.get(i).getState());
                    workItem.setUserId(workItems.get(i).getUserId());
                    getWorkItemsList.add(workItem);

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return getWorkItemsList;
    }

    public List<WorkItem> getAllMyTask() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<List<WorkItem>> call = service.getAllMyTask("getAll");

        call.enqueue(new Callback<List<WorkItem>>() {
            @Override
            public void onResponse(Response<List<WorkItem>> response, Retrofit retrofit) {

                List<WorkItem> workItems = response.body();

                for (int i = 0; i < workItems.size(); i++) {

                    WorkItem workItem = new WorkItem();
                    workItem.setId(workItems.get(i).getId());
                    workItem.setTitle(workItems.get(i).getTitle());
                    workItem.setDescription(workItems.get(i).getDescription());
                    workItem.setState(workItems.get(i).getState());
                    workItem.setUserId(workItems.get(i).getUserId());
                    getWorkItemsList.add(workItem);

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return getWorkItemsList;
    }

    public List<WorkItem> getAllWorkItemsByTeamId(Long id) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<List<WorkItem>> call = service.getAllWorkItemsByTeamId(id);

        call.enqueue(new Callback<List<WorkItem>>() {
            @Override
            public void onResponse(Response<List<WorkItem>> response, Retrofit retrofit) {

                List<WorkItem> workItems = response.body();

                for (int i = 0; i < workItems.size(); i++) {

                    WorkItem workItem = new WorkItem();
                    workItem.setId(workItems.get(i).getId());
                    workItem.setTitle(workItems.get(i).getTitle());
                    workItem.setDescription(workItems.get(i).getDescription());
                    workItem.setState(workItems.get(i).getState());
                    workItem.setUserId(workItems.get(i).getUserId());
                    workItem.setIssueId(workItems.get(i).getIssueId());
                    getWorkItemsList.add(workItem);

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return getWorkItemsList;
    }

    public List<User> getAllUsersByTeamId(Long id) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<List<User>> call = service.getAllUsersByTeamId(id);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {

                List<User> users = response.body();

                for (int i = 0; i < users.size(); i++) {

                    User user = new User();
                    user.setId(users.get(i).getId());
                    user.setFirstName(users.get(i).getFirstName());
                    user.setLastName(users.get(i).getLastName());
                    user.setUserName(users.get(i).getUserName());
                    user.setUserNumber(users.get(i).getUserNumber());
                    user.setState(users.get(i).getState());
                    user.setTeamId(users.get(i).getTeamId());
                    getUsersList.add(user);

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return getUsersList;
    }

    public WorkItem getWorkItemById(long id) {

        final WorkItem getWorkItem = new WorkItem();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<WorkItem> call = service.getWorkItemById(id);

        call.enqueue(new Callback<WorkItem>() {
            @Override
            public void onResponse(Response<WorkItem> response, Retrofit retrofit) {

                WorkItem workItemFromBody = response.body();
                getWorkItem.setTitle(workItemFromBody.getTitle());
                getWorkItem.setDescription(workItemFromBody.getDescription());
                getWorkItem.setState(workItemFromBody.getState());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        return getWorkItem;
    }

    public void addWorkItem(final String title, final String description, final String state) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<WorkItem> call = service.addWorkItem(new WorkItem(title, description, state));

        call.enqueue(new Callback<WorkItem>() {
            @Override
            public void onResponse(Response<WorkItem> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void updateWorkItemsState(final Long id, final String state) {

        WorkItem workItemWithNewState = new WorkItem();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<WorkItem> call = service.updateWorkItemsState(id, workItemWithNewState.setState(state));

        call.enqueue(new Callback<WorkItem>() {
            @Override
            public void onResponse(Response<WorkItem> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void updateWorkItemsTitle(final Long id, final String title) {

        WorkItem workItemWithNewTilte = new WorkItem();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<WorkItem> call = service.updateWorkItemsTitle(id, workItemWithNewTilte.setTitle(title));

        call.enqueue(new Callback<WorkItem>() {
            @Override
            public void onResponse(Response<WorkItem> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void updateWorkItemsDescription(final Long id, final String description) {

        WorkItem workItemWithNewDescription = new WorkItem();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<WorkItem> call = service.updateWorkItemsDescription(id, workItemWithNewDescription.setDescription(description));

        call.enqueue(new Callback<WorkItem>() {
            @Override
            public void onResponse(Response<WorkItem> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void assigneeWorkItemToUser(final Long id, final Long userId) {

        WorkItem workItemWithNewUser = new WorkItem();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<WorkItem> call = service.assigneeWorkItemToUser(id, workItemWithNewUser.setUserId(userId));

        call.enqueue(new Callback<WorkItem>() {
            @Override
            public void onResponse(Response<WorkItem> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public void addWorkItemToIssue(final Long id, final String issue) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<WorkItem> call = service.addWorkItemToIssue(id, new Issue(issue));

        call.enqueue(new Callback<WorkItem>() {
            @Override
            public void onResponse(Response<WorkItem> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    public Team getTeamById(long id) {

        final Team getTeam = new Team();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiRepository service = retrofit.create(ApiRepository.class);

        Call<Team> call = service.getTeamById(id);

        call.enqueue(new Callback<Team>() {
            @Override
            public void onResponse(Response<Team> response, Retrofit retrofit) {

                Team team = response.body();
                getTeam.setTeamName(team.getTeamName());
                getTeam.setState(team.getState());
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        return getTeam;
    }


    //POST Method // https://www.youtube.com/watch?v=wg9nG07UvuU
}


