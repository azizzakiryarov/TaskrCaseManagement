package http;

import java.util.List;

import model.WorkItem;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiService {

    @GET("/workitems/getByState")
    Call<List<WorkItem>> getAllUnstarted(@Query("state") String state);

    @GET("/workitems/getById/{id}")
    Call<WorkItem> getWorkItemById(@Path("id") long id);

    @GET("/workitems/getByState")
    Call<List<WorkItem>> getAllStarted(@Query("state") String state);

    @GET("/workitems/getByState")
    Call<List<WorkItem>> getAllDone(@Query("state") String state);

    @GET("/workitems/getAllWorkItemsWithIssues/{getAll}")
    Call<List<WorkItem>> getAllMyTask(@Query("getAll") String getAll);

    @POST("/workitems")
    Call<WorkItem> addWorkItem(@Body WorkItem workItem);
}
