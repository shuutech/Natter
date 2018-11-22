package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class FriendManager {

    UserFriend friend = new UserFriend();
    public void addFriendToDB(String currentUserName, String friendUserName, String status) throws Exception{
        User currentUser = new UserManager().getUser(currentUserName);
        User friendUser = new UserManager().getUser(friendUserName);
        friend.setCurrentUser(currentUser);
        friend.setFriendUser(friendUser);
        friend.setStatus(status);

        SessionFactory sessionFactory = DBManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        //Begins a transaction
        session.beginTransaction();
        //Saves the Customer789 object in the DB
        session.saveOrUpdate(friend);
        // session.save(user);
        //Commits the transaction in the DB
        session.getTransaction().commit();
        //  System.exit(0);

        session.close();
    }

    public String viewFriend(String currentUser) {
        ArrayList<String> buildString = new ArrayList<>();
        String start = "<table><thead>" +
                "<tr><th>First Name</th>" +
                "<th>Second Name</th>" +
                "<th>State</th></tr>" +
                "</thead><tbody>";
        String middle = "";

        String end =  "</tbody></table>";

        SessionFactory sessionFactory = DBManager.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserFriend> list = session
                .createQuery(
                        "from UserFriend " +
                                "where status like '%Friend Requested%' AND currentUser.userName =(:currentUser)")
                .setParameter("currentUser",currentUser)
                .getResultList();
        list.forEach((UserFriend userFriend) ->
        {buildString.add("<tr><td>" + userFriend.getFriendUser().getFirstName() + "</td>" +
                "<td>" + userFriend.getFriendUser().getLastName() + "</td>" +
                "<td>" + userFriend.getStatus() + "</td></tr>");
        });
        for (String s: buildString){
            middle = s + middle;
        }
        String output = start + middle + end;

        return output;
    }

    public static void main(String[] args) {
        new FriendManager().viewFriend("rickyaus");
    }
}
