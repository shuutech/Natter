package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AddFriendServlet extends HttpServlet {

    String currentUser;
    String friendUser;

    public String getCurrent(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        currentUser = (String) session.getAttribute("uname");

        return currentUser;
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        boolean continueAdding = true;

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            currentUser = getCurrent(request);


                friendUser = request.getParameter("friend");
                try {
                    new FriendManager().addFriendToDB(currentUser, friendUser, FriendStatus.REQUESTED.friendStatus);
                } catch (Exception e) {
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
