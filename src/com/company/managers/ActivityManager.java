package com.company.managers;

import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.interfaces.Login;
import com.company.objects.User;
import com.company.objects.UserFriend;
import com.company.validations.ActivityValidations;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager extends Manager implements Login {

    public boolean isUserLoggedOn(String username, String password) throws NotValidLoginException {
        return new UserManager().isUserLoggedOn(username, password);
    }

    public User getUser(String username) throws NotValidUserException {
        return new UserManager().getUser(username);
    }

//    public void createActivitybackup(User username, String activityname) {
//
//        ArrayList<String> currentUserArray;
//        ArrayList<String> friendUserArray;
//        User currentUser;
//        User friendUser;
//        boolean addCurrent = true;
//        boolean addFriend = true;
//        Session session = super.openSession();
//        String user = username.getUserName();
//        List<UserFriend> list = session
//                .createQuery(
//                        "from UserFriend " +
//                                "where status like '%Friend Requested%' AND currentUser.userName =(:currentUser) OR friendUser.userName =(:currentUser)")
//                .setParameter("currentUser", user)
//                .getResultList();
//
//        for (UserFriend uf : list) {
//
//            currentUser = uf.getCurrentUser();
//            currentUserArray = currentUser.getActivityLists();
//            if (currentUserArray != null) {
//                for (String current : currentUserArray) {
//                    if (current.equals(activityname)) {
//                        addCurrent = false;
//                    }
//                }
//            }
//
//            if (addCurrent) {
//                currentUser.addActivity(activityname);
//                session.beginTransaction();
//                session.update(uf);
//                session.getTransaction().commit();
//            }
//
//            friendUser = uf.getFriendUser();
//            friendUserArray = friendUser.getActivityLists();
//            if (friendUserArray != null) {
//                for (String friend : friendUserArray) {
//                    if (friend.equals(activityname)) {
//                        addFriend = false;
//                    }
//                }
//            }
//
//            if (addFriend) {
//                friendUser.addActivity(activityname);
//                session.beginTransaction();
//                session.update(uf);
//                session.getTransaction().commit();
//            }
//        }
//        ;
//        session.close();
//
//
//    }


//    public void createActivity(String username, String activityname, String password) throws NotValidLoginException {
//
//        logActivityCurrentUser(username, activityname,password);
//        logActivityFriendUser(username, activityname,password);
//    }

        public void createActivity(String username, String activityname, String password) throws NotValidLoginException {
        if (isUserLoggedOn(username,password)) {
            Session session = super.openSession();
            List<UserFriend> list = session
                    .createQuery(
                            "from UserFriend " +
                                    "where (status like'%Friend Added%' AND currentUser.userName =(:currentUser)) OR (status like '%Friend Added%' AND friendUser.userName =(:currentUser))")
                    .setParameter("currentUser", username)
                    .getResultList();

            for (UserFriend uf : list) {
                addActivities(uf, activityname, session);
            }
            session.close();
        }
    }

//    public void logActivityFriendUser(String username, String activityname, String password) throws NotValidLoginException {
//        if (isUserLoggedOn(username,password)) {
//            Session session = super.openSession();
//            List<UserFriend> list = session
//                    .createQuery(
//                            "from UserFriend " +
//                                    "where status like '%Friend Added%' AND friendUser.userName =(:currentUser)")
//                    .setParameter("currentUser", username)
//                    .getResultList();
//
//            for (UserFriend uf : list) {
//                addActivities(uf, activityname, session);
//            }
//            session.close();
//        }
//    }


    public void addActivities(UserFriend uf, String activityname, Session session) {
        try {
            uf.getCurrentUser().getActivityLists();
            // boolean activityExists = new ActivityValidations().checkIfActivityExists(uf.getCurrentUser(), activityname);
            if (!(new ActivityValidations().checkIfActivityExists(uf.getCurrentUser(), activityname))) {
                uf.getCurrentUser().addActivity(activityname);
               // super.update(session, uf);
            }
            uf.getFriendUser().getActivityLists();
            if (!(new ActivityValidations().checkIfActivityExists(uf.getFriendUser(), activityname))) {
                uf.getFriendUser().addActivity(activityname);

            }
            super.update(session, uf);
        } catch (NullPointerException e) {
            uf.getCurrentUser().addActivity(activityname);
        }
    }

//    public void joinActivity(String username, String activityname) {
//      //  User currentUser = new UserManager().getUser(username);
//        createActivity(username, activityname);
//    }


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

//    public String viewAvailableActivitybackup(String currentUser) {
//        ArrayList<String> buildString = new ArrayList<>();
//        String start = "<table><thead>" +
//                "<tr><th>Activity</th>" +
//                "</thead><tbody>";
//        String middle = "";
//
//        String end = "</tbody></table>";
//
//        SessionFactory sessionFactory = super.getSessionFactory();
//        Session session = sessionFactory.openSession();
//        List<ArrayList> list = session
//                .createQuery(
//                        "select activityLists from User " +
//                                "where userName =(:currentUser)")
//                .setParameter("currentUser", currentUser)
//                .getResultList();
//
//        int i = 0;
//        for (ArrayList str : list) {
//            System.out.println("<tr><td>" + str.toString() + "</td></tr>");
//            i++;
//        }
//
//
//        for (String s : buildString) {
//            middle = s + middle;
//        }
//        String output = start + middle + end;
//
//        return output;
//    }


}