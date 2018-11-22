package com.company;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ViewFriendServlet extends HttpServlet {

    String currentUser;

    public String getCurrent(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        currentUser=(String)session.getAttribute("uname");

        return currentUser;
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            currentUser= getCurrent(request);


            out.println("<HTML><HEAD><TITLE>Hello World!</TITLE>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\"/>" +
                    "</HEAD><BODY><h2 class = \"bio room\">View Friends</h2>" +
                    new FriendManager().viewFriend(currentUser)  + "</BODY></HTML>");
            out.close();


        } catch (
                IOException io) {
            System.out.println("IO Exception --  " + io.getMessage());
        }
    }

}

