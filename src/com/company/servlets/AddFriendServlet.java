package com.company.servlets;

import com.company.interfaces.Login;
import com.company.managers.FriendManager;
import com.company.enums.FriendStatus;
import com.company.managers.Manager;
import com.company.objects.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AddFriendServlet extends HttpServlet implements Login {

    String currentUser;
    String friendUser;
    String password;


    public boolean isUserLoggedOn(String username, String password) {
        SessionFactory sessionFactory = Manager.getSessionFactory();
        Session session = sessionFactory.openSession();
        User user = session.get(User.class, username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String username)  {
        SessionFactory sessionFactory = Manager.getSessionFactory();
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

    public String getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        currentUser = (String) session.getAttribute("uname");

        return currentUser;
    }

    public String getCurrentPassword(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        password = (String) session.getAttribute("pword");

        return password;
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        boolean continueAdding = true;


        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            currentUser = getCurrentUser(request);
         //   getUser(currentUser);
            password = getCurrentPassword(request);

                friendUser = request.getParameter("friend");
                try {
                    if (isUserLoggedOn( currentUser, password)) {
                    //    new FriendManager().updateFriend(currentUser, password, friendUser, FriendStatus.REQUESTED.friendStatus);
                    }} catch (Exception e) {
                    out.println("Friend does not exist");
                    continueAdding = false;
                }
                if (continueAdding){
                out.println("<HTML><HEAD><TITLE>Hello World!</TITLE>" +
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\"/>" +
                        "</HEAD><BODY><h2 class = \"bio room\">Friend Added!" +
                        currentUser + "friend user" + friendUser + "</h2></BODY></HTML>");}
                out.close();


        } catch (
                IOException io) {
            System.out.println("IO Exception --  " + io.getMessage());
        }
    }

}
