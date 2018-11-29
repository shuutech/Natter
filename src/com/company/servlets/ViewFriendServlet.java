package com.company.servlets;

import com.company.enums.FriendStatus;
import com.company.interfaces.Login;
import com.company.managers.FriendManager;
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

public class ViewFriendServlet extends HttpServlet implements Login {

    String currentUser;
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

    public User getUser(String username){
        User user = null;
        return user;
    }

    public String getCurrent(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        currentUser=(String)session.getAttribute("uname");

        return currentUser;
    }

    public String getCurrentPassword(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        password = (String) session.getAttribute("pword");

        return password;
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            currentUser= getCurrent(request);

            try {
                if (isUserLoggedOn( currentUser, password)) {
                    new FriendManager().viewFriend(currentUser,password);
                }} catch (Exception e) {
                out.println("User Not logged on");

            }


            out.println("<HTML><HEAD><TITLE>Hello World!</TITLE>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\"/>" +
                    "</HEAD><BODY><h2 class = \"bio room\">View Friends</h2>" +
                    new FriendManager().viewFriend(currentUser,password)  + "</BODY></HTML>");
            out.close();


        } catch (
                Exception e) {
            System.out.println("IO Exception --  " + e.getMessage());
        }
    }

}

