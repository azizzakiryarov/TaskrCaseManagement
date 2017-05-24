package http;

import java.util.List;

import model.WorkItem;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

import static android.R.attr.id;

public interface ApiService {

    @GET("/workitems")
    Call<List<WorkItem>> getAllUnstarted(@Query("state") String state);

    @GET("/workitems/{id}")
    Call<WorkItem> getWorkItemById(@Path("id") long id);
}
