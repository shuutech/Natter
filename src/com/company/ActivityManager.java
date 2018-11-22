package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

    public void createActivity(User username, String activityname) {

        SessionFactory sessionFactory = DBManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        String user = username.getUserName();
        List<Integer> list = session
                .createQuery(
                        "select id from UserFriend " +
                                "where status like '%Friend Requested%' AND currentUser.userName =(:currentUser) OR friendUser.userName =(:currentUser)")
                .setParameter("currentUser", user)
                .getResultList();

        for (int i=0; i<list.size();i++){
            UserFriend userFriend = session.get(UserFriend.class,list.get(i));
            userFriend.addActivity(activityname);
            session.beginTransaction();
            session.update(userFriend);
            session.getTransaction().commit();


        }
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

        SessionFactory sessionFactory = DBManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<ArrayList> list = session
                .createQuery(
                        "select activityLists from UserFriend " +
                                "where currentUser.userName =(:currentUser)")
                .setParameter("currentUser",currentUser)
                .getResultList();

            for(int i=0; i<list.size();i++) {
                ArrayList row = list.get(i);
                System.out.println("<tr>");
                for (int j=0;j<row.size();j++){
                System.out.print("<td>" + row.get(j)+ "</td>");
                }
                System.out.println("</tr>");
            };

        for (String s: buildString){
            middle = s + middle;
        }
        String output = start + middle + end;

        return output;
    }




}