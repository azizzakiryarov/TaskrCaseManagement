package http;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import model.WorkItem;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public final class HttpService {

    private static final String BASE_URL = "http://10.0.2.2:8080";
    private static final Gson gson = new GsonBuilder().create();
    List<WorkItem> getList = new ArrayList<>();
    WorkItem workItem = new WorkItem();


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

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<WorkItem> call = service.getWorkItemById(id);

        call.enqueue(new Callback<WorkItem>() {
            @Override
            public void onResponse(Response<WorkItem> response, Retrofit retrofit) {

                workItem = response.body();

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        return workItem;
    }

    //POST Method // https://www.youtube.com/watch?v=wg9nG07UvuU
}


