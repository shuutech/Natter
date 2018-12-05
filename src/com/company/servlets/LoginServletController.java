package com.company.servlets;


import com.company.enums.FriendStatus;
import com.company.managers.UserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


@Controller
public class LoginServletController {


    @RequestMapping(value = "/home")
    public ModelAndView loginValidate(HttpServletRequest request){
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ModelAndView viewToReturn = null;

        try{
            if (new UserManager().isUserLoggedOn(username, password)==true){
                session.setAttribute("uname", username);
                session.setAttribute("pword", password);
                viewToReturn = new ModelAndView("loggedInView", "name", username);
                session.setMaxInactiveInterval(5000);

            }else{
                viewToReturn = new ModelAndView("notLoggedIn", "name", username);
            }

        } catch (Exception e) {
            viewToReturn = new ModelAndView("notLoggedIn", "name", username);
        }
        return viewToReturn;
    }

    @RequestMapping(value = "/viewfriend")
    public ModelAndView viewFriend(HttpServletRequest request){

        ModelAndView viewToReturn = null;

        try {

            HttpSession session=request.getSession(false);
            String currentUser = (String)session.getAttribute("uname");
            String password = (String) session.getAttribute("pword");
            com.company.controller.Controller controller = new com.company.controller.Controller();
            String friends = controller.displayFriendsOfCurrentUser(currentUser,password);
            viewToReturn = new ModelAndView("viewFriends", "friends", friends);


        }catch (Exception e) {
            viewToReturn = new ModelAndView("notLoggedIn");
        }

        return viewToReturn;


    }


}






