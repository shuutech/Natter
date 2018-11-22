package com.company;

import javax.persistence.*;

@Entity
public class Activity {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int activityId;
    private String activityName;
    @ManyToOne
    @JoinColumn(name="username", referencedColumnName = "username")
    private User username;
    private String status;

    public Activity() {

    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", username=" + username +
                ", status='" + status + '\'' +
                '}';
    }
}
