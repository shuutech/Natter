package com.company;


import org.hibernate.Session;
import org.hibernate.SessionFactory;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



public class LoginServlet extends HttpServlet {


    String username;
    String password;
    public void doPost(HttpServletRequest request, HttpServletResponse response){

        try {
            username = request.getParameter("username");
            password = request.getParameter("password");

            if (new UserManager().isUserLoggedOn(username, password)==true){

                HttpSession session = request.getSession();
                session.setAttribute("uname", username);

                response.getWriter().print("Hello "+ username);

            }else{
                response.getWriter().print("Not matching");
            }

        } catch (IOException io) {
            System.out.println("IO Exception --  "+io.getMessage());
        }

    }


}


class OfflineLogin {
    public void checkLogin(String username, String password){
            if (new UserManager().isUserLoggedOn(username, password)==true){
                System.out.println(username + "is authenticated");
            }else{
                System.out.println(username +" or " + password + " does not match");
            }
    }
}



