package com.company;

import com.company.exceptions.NotValidLoginException;
import com.company.managers.ActivityManager;
import com.company.managers.UserManager;
import com.company.objects.User;
import com.company.offline.OfflineMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.NotActiveException;
import java.util.ArrayList;

class ActivityManagerTests {
    @org.junit.jupiter.api.Test
    void addActivitiesWithoutLogOn() {
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new ActivityManager().createActivity("shu", "a", "wrong");
        });

        Assertions.assertThrows(NotValidLoginException.class, () -> {
            new ActivityManager().createActivity(null, "a", "wrong");
        });
    }

    @org.junit.jupiter.api.Test
    void viewActivitiesWithoutLogOn() {
        Assertions.assertThrows(NotValidLoginException.class, () -> {
            ActivityManager activityManager = new ActivityManager();

                if (activityManager.isUserLoggedOn(null, "shu"))
                {
                    for (String viewActivity: activityManager.viewAvailableActivity(null)){
                        System.out.println(viewActivity);
                    }
                }
        });
    }

    @Test
    void addActivityToFriendList() throws NotValidLoginException {
        ActivityManager activityManager = new ActivityManager();
       activityManager.createActivity("torinado", "succcess", "torinado");
        ArrayList<String> activity = new ArrayList<>();
        activity.add("succcess");

       Assertions.assertEquals(activity, activityManager.viewAvailableActivity("torinado"));

        ActivityManager activityManager1 = new ActivityManager();
        activityManager1.createActivity("shu", "yay", "shu");
        ArrayList<String> activity1 = new ArrayList<>();
        activity1.add("succcess");
        activity1.add("yay");

        Assertions.assertEquals(activity1, activityManager1.viewAvailableActivity("shu"));
    }
    @Test
    void showAllActivities() {
        new OfflineMethods().viewActivities("ricky", "ricky");
        ArrayList<String> activity = new ArrayList<>();

        activity.add("succcess");
        activity.add("yay");

        Assertions.assertEquals(activity, new ActivityManager().viewAvailableActivity("ricky"));
    }




}