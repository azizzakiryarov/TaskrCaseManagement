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


    public List<WorkItem> getAllUnstarted() {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Call<List<WorkItem>> call = service.getAllUnstarted("Done");

        call.enqueue(new Callback<List<WorkItem>>() {
            @Override
            public void onResponse(Response<List<WorkItem>> response, Retrofit retrofit) {

                List<WorkItem> workItems = response.body();

                //String details = "";


                for (int i = 0; i < workItems.size(); i++) {


                    WorkItem workItem = new WorkItem();
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
}


