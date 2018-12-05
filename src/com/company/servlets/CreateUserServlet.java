package com.company.servlets;

import com.company.managers.UserManager;
import com.company.objects.User;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CreateUserServlet extends HttpServlet {



    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username;
        String password;
        String firstName;
        String lastName;
        String email;
        String phoneNumber;
        String location;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        try {
            username = request.getParameter("userName");
            password = request.getParameter("password");
            firstName = request.getParameter("firstName");
            lastName = request.getParameter("lastName");
            email = request.getParameter("email");
            phoneNumber = request.getParameter("phone");
            location = request.getParameter("userLocation");
//            PrintWriter out = response.getWriter();
//            out.println("<HTML><HEAD><TITLE>Hello World!</TITLE>" +
////                    "<link rel=\"stylesheet\" type=\"text/css\" href=\"styles.css\"/>" +
////                    "</HEAD><BODY><h2 class = \"bio room\">User Created!" +
////                    username + "</h2></BODY></HTML>");
////
////            out.close();
            try{
                UserManager userManager = new UserManager();
                userManager.addUserToDB(username, password, firstName, lastName, email, phoneNumber, location);
                response.getWriter().print(userManager.getUser(username));
            }
            catch (Exception e){
                e.printStackTrace();
 //               out.println("Email cannot be null");
            }

  //      }
//        catch (IOException io) {
//            System.out.println("IO Exception --  " + io.getMessage());
//        }
    }

}


