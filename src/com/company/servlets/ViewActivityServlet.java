package com.company.servlets;

import com.company.enums.FriendStatus;
import com.company.exceptions.NotValidLoginException;
import com.company.managers.FriendManager;
import com.company.offline.OfflineMethods;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ViewActivityServlet extends HttpServlet {

    String currentUser;
    String password;

    public String getCurrent(HttpServletRequest request) {
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

        ArrayList friends;


        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();

            currentUser = getCurrent(request);
            password = getCurrentPassword(request);
           friends = null;
                   //new OfflineMethods().displayFriendsOfCurrentUser( currentUser,password , FriendStatus.REQUESTED.friendStatus)


            out.println("<HTML><HEAD><TITLE>Hello World!</TITLE>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\"/>" +
                    "</HEAD><BODY><h2 class = \"bio room\">View Friends</h2>" +

                    friends + "</BODY></HTML>");
            out.close();


        } catch (Exception e) {
            System.out.println("IO Exception --  " + e.getMessage());
        }
    }

}

