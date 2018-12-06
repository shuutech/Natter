package com.company.servlets;


import com.company.enums.FriendStatus;
import com.company.managers.ActivityManager;
import com.company.managers.FriendManager;
import com.company.managers.UserManager;
import com.company.objects.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ModelAndView loginValidate(HttpServletRequest request) {
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ModelAndView viewToReturn = null;

        try {
            if (new UserManager().isUserLoggedOn(username, password) == true) {
                session.setAttribute("uname", username);
                session.setAttribute("pword", password);
                viewToReturn = new ModelAndView("loggedInView", "name", username);
                session.setMaxInactiveInterval(5000);

            } else {
                viewToReturn = new ModelAndView("notLoggedIn", "name", username);
            }

        } catch (Exception e) {
            viewToReturn = new ModelAndView("notLoggedIn", "name", username);
        }
        return viewToReturn;
    }

//    @RequestMapping(value = "/createUser", produces ="application/json")
//    public ResponseEntity<User> addUser(HttpServletRequest request) {
//        User user = new User();
//        String username = request.getParameter("userName");
//        String password = request.getParameter("password");
//        String firstName = request.getParameter("firstName");
//        String lastName = request.getParameter("lastName");
//        String email = request.getParameter("email");
//        String phoneNumber = request.getParameter("phone");
//        String location = request.getParameter("userLocation");
//        try{
//            UserManager userManager = new UserManager();
//            userManager.addUserToDB(username, password, firstName, lastName, email, phoneNumber, location);
//            user = userManager.getUser(username);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
//    }


    @RequestMapping(value = "/viewfriend")
    public ModelAndView viewFriend(HttpServletRequest request) {

        ModelAndView viewToReturn = null;

        try {

            HttpSession session = request.getSession(false);
            String currentUser = (String) session.getAttribute("uname");
            String password = (String) session.getAttribute("pword");
            com.company.controller.Controller controller = new com.company.controller.Controller();
            String friends = controller.displayFriendsOfCurrentUser(currentUser, password);
            viewToReturn = new ModelAndView("viewFriends", "friends", friends);


        } catch (Exception e) {
            viewToReturn = new ModelAndView("notLoggedIn");
        }

        return viewToReturn;


    }

    @RequestMapping(value = "/viewfriendrequests")
    public ModelAndView viewFriendRequests(HttpServletRequest request) {

        ModelAndView viewToReturn = null;

        try {

            HttpSession session = request.getSession(false);
            String currentUser = (String) session.getAttribute("uname");
            String password = (String) session.getAttribute("pword");
            com.company.controller.Controller controller = new com.company.controller.Controller();
            String friends = controller.displayFriendRequests(currentUser, password);
            viewToReturn = new ModelAndView("viewFriendRequests", "friends", friends);


        } catch (Exception e) {
            viewToReturn = new ModelAndView("notLoggedIn");
        }

        return viewToReturn;


    }

    @RequestMapping(value = "/createUser")
    public ModelAndView addUser(HttpServletRequest request) {

        ModelAndView viewToReturn = null;

        String username = request.getParameter("userName");
        String password = request.getParameter("password");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");
        String location = request.getParameter("userLocation");
        try {
            UserManager userManager = new UserManager();
            userManager.addUserToDB(username, password, firstName, lastName, email, phoneNumber, location);
            User user = userManager.getUser(username);
            viewToReturn = new ModelAndView("createUser", "user", user);
        } catch (Exception e) {
            e.printStackTrace();
            viewToReturn = new ModelAndView("notLoggedIn");
        }

        return viewToReturn;
    }

    @RequestMapping(value = "/requestfriend")
    public ModelAndView requestFriend(HttpServletRequest request) {

        ModelAndView viewToReturn = null;
        String currentUser = null;
        String password = null;
        try {
            HttpSession session = request.getSession(false);
            currentUser = (String) session.getAttribute("uname");
            password = (String) session.getAttribute("pword");
        } catch (Exception e) {
            viewToReturn = new ModelAndView("notLoggedIn");

        }
        try {
            String friendUser = request.getParameter("friend");
            new FriendManager().addOrUpdateFriend(currentUser, password, friendUser);
            com.company.controller.Controller controller = new com.company.controller.Controller();
            String friends = controller.displayFriendRequests(currentUser, password);
            viewToReturn = new ModelAndView("viewFriendRequests", "friends", friends);
        } catch (
                Exception e) {
            viewToReturn = new ModelAndView("friendNotExist");

        }
        return viewToReturn;
    }

    @RequestMapping(value = "/addfriend")
    public ModelAndView addFriend(HttpServletRequest request) {

        ModelAndView viewToReturn = null;
        String currentUser = null;
        String password = null;
        try {
            HttpSession session = request.getSession(false);
            currentUser = (String) session.getAttribute("uname");
            password = (String) session.getAttribute("pword");
        } catch (Exception e) {
            viewToReturn = new ModelAndView("notLoggedIn");

        }
        try {
            String friendUser = request.getParameter("friend");
            new FriendManager().addOrUpdateFriend(currentUser, password, friendUser);
            com.company.controller.Controller controller = new com.company.controller.Controller();
            String friends = controller.displayFriendsOfCurrentUser(currentUser, password);
            viewToReturn = new ModelAndView("viewFriends", "friends", friends);
        } catch (
                Exception e) {
            viewToReturn = new ModelAndView("friendNotExist");

        }
        return viewToReturn;
    }

    @RequestMapping(value = "/addactivity")
    public ModelAndView addActivity(HttpServletRequest request) {
        ModelAndView viewToReturn = null;
        try {

            HttpSession session = request.getSession(false);
            String currentUser = (String) session.getAttribute("uname");
            String password = (String) session.getAttribute("pword");
            String activityName = request.getParameter("activity");
            ActivityManager activityManager = new ActivityManager();
            activityManager.createActivity(currentUser, activityName, password);
            User user = activityManager.getUser(currentUser);
            if (user.getActivityLists() == null) {
                viewToReturn = new ModelAndView("addFriendFirst", "User", user);
            } else {
                viewToReturn = new ModelAndView("viewActivity", "User", user);
            }
        } catch (Exception e) {
            viewToReturn = new ModelAndView("notLoggedIn");

        }
        return viewToReturn;
    }

    @RequestMapping(value = "/viewactivity")
    public ModelAndView viewActivity(HttpServletRequest request) {
        ModelAndView viewToReturn = null;
        try {

            HttpSession session = request.getSession(false);
            String currentUser = (String) session.getAttribute("uname");
            ActivityManager activityManager = new ActivityManager();
            User user = activityManager.getUser(currentUser);
            viewToReturn = new ModelAndView("viewActivity", "User", user);
        } catch (Exception e) {
            viewToReturn = new ModelAndView("notLoggedIn");

        }
        return viewToReturn;
    }
}






