package com.company;
import com.company.managers.ActivityManager;
import com.company.managers.FriendManager;
import com.company.offline.OfflineMethods;

import java.util.Scanner;
import static com.company.ShuCommonUtils.check;


public class Main {

    public static void main(String[] args) throws Exception {
         String username=null;
         String password=null;
         String firstName=null;
         String lastName=null;
         String email=null;
         String phoneNumber=null;
         String location=null;
         String friendname=null;

        Scanner scanner = new Scanner(System.in);
        Boolean run = true;

        while (run) {
            System.out.println("Enter number 1 & Press Enter to - Login " +
                    "\nEnter number 2 & Press Enter to - Create New User " +
                    "\nEnter number 3 & Press Enter to - View Friends " +
                    "\nEnter number 4 & Press Enter to - Friend Request " +
                    "\nEnter number 5 & Press Enter to - Add a Friend " +
                    "\nEnter number 6 & Press Enter to - View Activity  " +
                    "\nEnter number 7 & Press Enter to - Start Activity "
            );
            switch (scanner.nextLine()) {
                case "1"://Login
                    System.out.println("Enter username: ");
                    username = scanner.nextLine();
                    System.out.println("Enter password: ");
                    password = scanner.nextLine();
                    new OfflineLogin().checkLogin(username, password);
                    run = check(run);
                    break;
               case "2"://Create New User
                   System.out.println("Enter username: ");
                   username = scanner.nextLine();
                   System.out.println("Enter password: ");
                   password = scanner.nextLine();
                   System.out.println("Enter firstName: ");
                   firstName = scanner.nextLine();
                   System.out.println("Enter lastName: ");
                   lastName = scanner.nextLine();
                   System.out.println("Enter email: ");
                   email = scanner.nextLine();
                   System.out.println("Enter phoneNumber: ");
                   phoneNumber = scanner.nextLine();
                   System.out.println("Enter location: ");
                   location = scanner.nextLine();
                   new AddUser().addUserToDB(username,password,firstName,lastName,email,phoneNumber,location);
                   run = check(run);
                    break;
                case "3"://Select all friends
                   new OfflineMethods().displayFriends( username,  password);
                    run = check(run);
                    break;
                case "4"://Friend Request

                    System.out.println("Enter username of friend to request as a friend: ");
                     friendname = scanner.nextLine();

                 new FriendManager().addOrUpdateFriend(username,password,friendname);
                    run = check(run);
                   break;
                case "5"://Add Friend

                    System.out.println(new FriendManager().viewFriendRequests(username,password));

                    System.out.println("Enter username of friend you want to add: ");
                    friendname = scanner.nextLine();

                  new FriendManager().addOrUpdateFriend(username,password,friendname);
                    run = check(run);
                    break;
              case "6":// View Activities
                  new ActivityManager().viewAvailableActivity(username);

                    run = check(run);
                    break;
             case "7"://Create New Activity
                    System.out.println("Enter Activity Name");
                    String activityName = scanner.nextLine();
                 new ActivityManager().joinActivity(username,activityName);
                    run = check(run);
                    break;
                case "N":
                    run = false;
            }
        }
        System.exit(0);
    }






}
