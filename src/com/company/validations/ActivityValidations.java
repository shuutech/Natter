package com.company.validations;

import com.company.managers.Manager;
import com.company.objects.User;
import com.company.objects.UserFriend;
import org.hibernate.Session;

import java.util.ArrayList;

public class ActivityValidations {
    public boolean checkIfActivityExists(User user, String activityname) {
        if (user.getActivityLists() == null) {
            return false;
        } else {
            for (String activityList : user.getActivityLists()) {
                if (activityList.equals(activityname)) {
                    return true;
                }
            }
        }
        return false;

    }
}


