package com.company;

import com.company.enums.FriendStatus;
import com.company.managers.ActivityManager;
import com.company.managers.FriendManager;

import java.util.Scanner;

import static com.company.ShuCommonUtils.check;


public class Main {
   static String username;
    static String password;
    static String firstName;
    static String lastName;
    static String email;
    static String phoneNumber;
    static String location;


    public static void main(String[] args) throws Exception {

        ShuCommonUtils shuCommonUtils = new ShuCommonUtils();
        Scanner scanner = new Scanner(System.in);
        Boolean run = true;

        while (run) {
            System.out.println("1. Login " +
                    "\n2. Create New User " +
                    "\n3. View Friends " +
                    "\n4. Add Friends " +
                    "\n5. View Activity  " +
                    "\n6. Start Activity "
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

                    System.out.println(new FriendManager().viewFriend(username,password));
                    run = check(run);
                    break;
                case "4"://Add Friend

                    System.out.println("Enter username of friend you want to add: ");
                    String friendname = scanner.nextLine();

                    new FriendManager().addFriendToDB(username,password,friendname, FriendStatus.REQUESTED.friendStatus);
                    run = check(run);
                   break;
              case "5":// View Activities
                  new ActivityManager().viewAvailableActivity(username);

                    run = check(run);
                    break;
             case "6"://Create New Activity
                    System.out.println("Enter Activity Name");
                    String activityName = scanner.nextLine();
                 new ActivityManager().joinActivity(username,activityName);
                    run = check(run);
                    break;
//                case "7"://Update the stock level of a given product
//                    System.out.println("Update stock levels for (product id)?");
//                    product_id = Integer.parseInt(scanner.nextLine());
//                    productManager.updateStockLevel(c, product_id);
//                    run = check(run);
//                    break;
//                case "8"://Delete a given produc
//                    productManager.deleteProduct(c);
//                    run = check(run);
//                    break;
//                case "9"://Print the name and price for all product records.
//                    productManager.retrieveNamePrice(c);
//
//                    run = check(run);
//                    break;
                case "N":
                    run = false;
            }
        }
    }






}
