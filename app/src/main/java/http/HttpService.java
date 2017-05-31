package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import model.Issue;
import model.WorkItem;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public final class HttpService {

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static final Gson gson = new GsonBuilder().create();
    private List<WorkItem> getList = new ArrayList<>();

    public List<WorkItem> getAllUnstarted() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiService service = retrofit.create(ApiService.class);

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
                    getList.add(workItem);

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return getList;
    }

    public List<WorkItem> getAllStarted() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiService service = retrofit.create(ApiService.class);

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
                    getList.add(workItem);

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return getList;
    }

    public List<WorkItem> getAllDone() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiService service = retrofit.create(ApiService.class);

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
                    getList.add(workItem);

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return getList;
    }

    public List<WorkItem> getAllMyTask() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiService service = retrofit.create(ApiService.class);

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
                    getList.add(workItem);

                }
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
        return getList;
    }

    public WorkItem getWorkItemById(long id) {

        final WorkItem getWorkItem = new WorkItem();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiService service = retrofit.create(ApiService.class);

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

        ApiService service = retrofit.create(ApiService.class);

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

        ApiService service = retrofit.create(ApiService.class);

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

        ApiService service = retrofit.create(ApiService.class);

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

        ApiService service = retrofit.create(ApiService.class);

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

        ApiService service = retrofit.create(ApiService.class);

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

        ApiService service = retrofit.create(ApiService.class);

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


    //POST Method // https://www.youtube.com/watch?v=wg9nG07UvuU
}


