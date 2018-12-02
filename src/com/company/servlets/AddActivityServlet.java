package com.company.servlets;

import com.company.managers.ActivityManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class AddActivityServlet extends HttpServlet {

    String currentUser;
    String activityName;
    String password;
    Boolean exists;
    ActivityManager activityManager = new ActivityManager();

    public String getCurrent(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        currentUser = (String) session.getAttribute("uname");

        return currentUser;
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)  {



        try {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            currentUser = getCurrent(request);
            activityName = request.getParameter("activity");
            try{
            activityManager.createActivity(currentUser, activityName, password);}
            catch (Exception e){
                out.println("User does not exist, please enter another user");

            }


            out.println("<HTML><HEAD><TITLE>Hello World!</TITLE>" +
                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\"/>" +
                    "</HEAD><BODY><h2 class = \"bio room\">Activity Added!" +
                    activityName + "</h2></BODY></HTML>");
            out.close();


        } catch (
                IOException io) {
            System.out.println("IO Exception --  " + io.getMessage());
        }
    }

}

