package com.company.managers;

import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.interfaces.Login;
import com.company.objects.User;
import com.company.objects.UserFriend;
import com.company.validations.ActivityValidations;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

public class ActivityManager extends Manager implements Login {

    public boolean isUserLoggedOn(String username, String password) throws NotValidLoginException {
        return new UserManager().isUserLoggedOn(username, password);
    }

    public User getUser(String username) throws NotValidUserException {
        return new UserManager().getUser(username);
    }

        public void createActivity(String username, String activityname, String password) throws Exception {
        if (isUserLoggedOn(username,password)) {
            User user= getUser(username);
            boolean keepAdding= true;
            boolean keepAddingF = true;
            Session session = super.openSession();
            try{
            List<UserFriend> list = session
                    .createQuery(
                            "from UserFriend " +
                                    "where (status like'%Friend Added%' AND currentUser.userName =(:currentUser)) OR (status like '%Friend Added%' AND friendUser.userName =(:currentUser))")
                    .setParameter("currentUser", username)
                    .getResultList();

            for (UserFriend uf : list) {
                try {
                    uf.getCurrentUser().getActivityLists();
                    if (!(new ActivityValidations().checkIfActivityExists(uf.getCurrentUser(), activityname)) ) {
                        if (uf.getCurrentUser().equals(user) && keepAdding){
                            uf.getCurrentUser().addActivity(activityname);
                            keepAdding = false;
                        }else {
                            uf.getCurrentUser().addActivity(activityname);
                        }


                        super.update(session, uf);
                    }

                }
                catch (NullPointerException e) {
                    uf.getCurrentUser().addActivity(activityname);
                }
                try {
                    uf.getFriendUser().getActivityLists();
                    if (!(new ActivityValidations().checkIfActivityExists(uf.getFriendUser(), activityname))) {
                        uf.getFriendUser().addActivity(activityname);
                        super.update(session, uf);
                    }

                }
                catch (NullPointerException e) {
                    uf.getFriendUser().addActivity(activityname);
                }
            }}
            catch(Exception e){

                user.addActivity(activityname);
            }
            session.close();
        }
    }



    public ArrayList<String> viewAvailableActivity(String currentUser) {
        ArrayList<String> activityArray = new ArrayList<>();
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        Object object = session
                .createQuery(
                        "from User " +
                                "where userName =(:currentUser)")
                .setParameter("currentUser", currentUser)
                .getSingleResult();

        if (object instanceof User) {
            User u = (User) object;
            activityArray = ((User) object).getActivityLists();
        }

        session.close();
        return activityArray;

    }



}