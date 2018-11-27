package com.company.managers;

import com.company.interfaces.Login;
import com.company.objects.User;
import com.company.objects.UserFriend;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager extends Manager implements Login {

    public boolean isUserLoggedOn(String username, String password) {
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        com.company.objects.User user = session.get(com.company.objects.User.class, username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String username)  {
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user= null;

        try{
            user = session.get(User.class, username);
            if (user.equals(null)){
                throw new Exception();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return user;
    }

    public void createActivity(User username, String activityname) {

        ArrayList<String> currentUserArray ;
        ArrayList<String> friendUserArray;
        User currentUser;
        User friendUser;
        boolean addCurrent=true;
        boolean addFriend=true;
        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        String user = username.getUserName();
        List<com.company.objects.UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where status like '%Friend Requested%' AND currentUser.userName =(:currentUser) OR friendUser.userName =(:currentUser)")
                .setParameter("currentUser", user)
                .getResultList();

        for (UserFriend uf: list){

            currentUser = uf.getCurrentUser();
            currentUserArray = currentUser.getActivityLists();
            if (currentUserArray!=null){
            for (String current: currentUserArray){
                if (current.equals(activityname)){
                    addCurrent=false;}
            }}

            if (addCurrent){
                currentUser.addActivity(activityname);
                session.beginTransaction();
                session.update(uf);
                session.getTransaction().commit();
            }

            friendUser = uf.getFriendUser();
            friendUserArray = friendUser.getActivityLists();
            if (friendUserArray!=null){
                for (String friend: friendUserArray){
                    if (friend.equals(activityname)){
                        addFriend=false;}
                }}

            if (addFriend){
                friendUser.addActivity(activityname);
                session.beginTransaction();
                session.update(uf);
                session.getTransaction().commit();
            }
       };
        session.close();


    }

    public void joinActivity(String username, String activityname) throws Exception {
        User currentUser = new UserManager().getUser(username);
        createActivity(currentUser, activityname);
    }


    public String viewAvailableActivity(String currentUser) {
        ArrayList<String> buildString = new ArrayList<>();
        String start = "<table><thead>" +
                "<tr><th>Activity</th>" +
                "</thead><tbody>";
        String middle = "";

        String end =  "</tbody></table>";

        SessionFactory sessionFactory = super.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ArrayList> list = session
                .createQuery(
                        "select activityLists from User " +
                                "where userName =(:currentUser)")
                .setParameter("currentUser",currentUser)
                .getResultList();

        int i=0;
        for (ArrayList str: list){
            System.out.println("<tr><td>" + str.toString() + "</td></tr>");
            i++;
        }


        for (String s: buildString){
            middle = s + middle;
        }
        String output = start + middle + end;

        return output;
    }




}