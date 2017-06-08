package http;

import java.util.List;

import model.Issue;
import model.Team;
import model.User;
import model.WorkItem;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public interface ApiRepository {

    @GET("/workitems/getByState")
    Call<List<WorkItem>> getAllUnstarted(@Query("state") String state);

    @GET("/workitems/getByState")
    Call<List<WorkItem>> getAllStarted(@Query("state") String state);

    @GET("/workitems/getByState")
    Call<List<WorkItem>> getAllDone(@Query("state") String state);

    @GET("/workitems/getAllWorkItemsWithIssues/{getAll}")
    Call<List<WorkItem>> getAllMyTask(@Path("getAll") String getAll);

    @GET("/workitems/getById/{id}")
    Call<WorkItem> getWorkItemById(@Path("id") Long id);


    @POST("/workitems")
    Call<WorkItem> addWorkItem(@Body WorkItem workItem);

    @PUT("/workitems/{id}")
    Call<WorkItem> updateWorkItemsState(@Path("id") Long id, @Body WorkItem workItem);

    @PUT("/workitems/{id}")
    Call<WorkItem> updateWorkItemsTitle(@Path("id") Long id, @Body WorkItem workItem);

    @PUT("/workitems/{id}")
    Call<WorkItem> updateWorkItemsDescription(@Path("id") Long id, @Body WorkItem workItem);

    @PUT("/workitems/{id}")
    Call<WorkItem> assigneeWorkItemToUser(@Path("id") Long id, @Body WorkItem workItem);

    @POST("/workitems/{id}")
    Call<WorkItem> addWorkItemToIssue(@Path("id") Long id, @Body Issue issue);

    @GET("/workitems")
    Call<List<WorkItem>> getAllWorkItemsByTeamId(@Query("teamId") Long id);

    @GET("/users/{id}")
    Call<List<User>> getAllUsersByTeamId(@Path("id") Long id);

    @GET("/teams/{id}")
    Call<Team> getTeamById(@Path("id") Long id);

}
