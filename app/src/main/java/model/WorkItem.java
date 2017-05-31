package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WorkItem {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("userId")
    @Expose
    private Long userId;
    @SerializedName("issueId")
    @Expose
    private Long issueId;

    public WorkItem() {
    }

    public WorkItem(String title, String description, String state) {
        this.title = title;
        this.description = description;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public WorkItem setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public WorkItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WorkItem setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getState() {
        return state;
    }

    public WorkItem setState(String state) {
        this.state = state;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public WorkItem setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getIssueId() {
        return issueId;
    }

    public WorkItem setIssueId(Long issueId) {
        this.issueId = issueId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkItem workItem = (WorkItem) o;

        if (getId() != null ? !getId().equals(workItem.getId()) : workItem.getId() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(workItem.getTitle()) : workItem.getTitle() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(workItem.getDescription()) : workItem.getDescription() != null)
            return false;
        if (getState() != null ? !getState().equals(workItem.getState()) : workItem.getState() != null)
            return false;
        if (getUserId() != null ? !getUserId().equals(workItem.getUserId()) : workItem.getUserId() != null)
            return false;
        return getIssueId() != null ? getIssueId().equals(workItem.getIssueId()) : workItem.getIssueId() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + (getIssueId() != null ? getIssueId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WorkItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}