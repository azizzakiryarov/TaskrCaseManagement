package http;

import java.util.List;

import model.WorkItem;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {

    @GET("/workitems")
    Call<List<WorkItem>> getAllUnstarted(@Query("state") String state);
}
