package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issue {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("comment")
    @Expose
    private String comment;

    public Issue(String comment) {
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public Issue setId(Long id) {
        this.id = id;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Issue setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (getId() != null ? !getId().equals(issue.getId()) : issue.getId() != null) return false;
        return getComment() != null ? getComment().equals(issue.getComment()) : issue.getComment() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                '}';
    }
}
