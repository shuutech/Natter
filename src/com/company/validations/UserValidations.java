package com.company.validations;

import com.company.exceptions.*;
import com.company.objects.User;

import java.util.function.Predicate;

public class UserValidations {

    /**
     * User name needs to be at least 2 characters
     * Can only contain lowercase characters and numbers
     *
     * @param userName
     * @return
     */
    public static boolean validUserName(String userName) throws NotValidUserName {
        boolean valid = false;
        if (userName.length() >= 2 && userName.length() <= 32 && userName.matches("^(?=.*[a-z][1-9])*.+$")) {
            return valid = true;
        } else {
            throw new NotValidUserName("User name must be at least 2 characters and can only contain lowercase characters and numbers");

        }

    }

    /**
     * First name needs to be at least 3 characters
     * Can only contain upper/lowercase characters and spaces*
     *
     * @param firstName
     * @return
     */
    public static boolean validFirstName(String firstName) throws NotValidFirstName {
        boolean valid = false;
        if (firstName.length() >= 3 && firstName.length() <= 32 && firstName.matches("^[A-Z][a-zA-Z ]*$")) {
            return valid = true;
        }
        else {
            throw new NotValidFirstName("First name must start with a capital letter, contain only upper/lowercase characters and spaces and " +
                    "be at least 3 characters");

        }
    }

    /**
     * Last name needs to be at least 3 characters
     * Can only contain upper/lowercase characters and spaces*
     * First character must be capitalised
     *
     * @param lastName
     * @return
     */
    public static boolean validLastName(String lastName) throws NotValidLastName {
        boolean valid = false;
        if (lastName.length() >= 3 && lastName.length() <= 32 && lastName.matches("^[A-Z][a-zA-Z ]*$")) {
            return valid = true;
        }
        else {
            throw new NotValidLastName("Last name must start with a capital letter, contain only upper/lowercase characters and spaces and " +
                    "be at least 3 characters");
        }
    }

    /**
     * Location needs to be at least 2 characters
     * Can only contain upper/lowercase characters and spaces*
     * First character must be capitalised
     *
     * @param location
     * @return
     */
    public static boolean validLocation(String location) throws NotValidLocation {
        boolean valid = false;
        if (location.length() >= 2 && location.length() <= 32 && location.matches("^[A-Z][a-zA-Z ]*$")) {
            return valid = true;
        }
        else {
            throw new NotValidLocation("Location must start with a capital letter, contain only upper/lowercase characters and spaces and " +
                    "be at least 3 characters");
        }
    }

    /**
     * Password needs to be at least 6 characters
     * Must contain at least one upper/lowercase character
     * Must contain at least one number
     * Must contain at least one special character
     *
     * @param password
     * @return
     */
    public static boolean validPassword(String password) throws NotValidPassword  {
        boolean valid = false;
        if (password.length() >= 6 && password.length() <= 32 && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[1-9])(?=.*[!(-)_~`@#$%^&+=]).*$")) {
            return valid = true;
        }
        else {
            throw new NotValidPassword("Password must be at least 6 characters, contain at least one upper/lowercase character," +
                    "at least one number and special character");
        }
    }

    /**
     * Must only contain digits from 0 to 9
     *
     * @param phone
     * @return
     */
    public static boolean validPhone(String phone) throws NotValidPhone {
        boolean valid = false;
        if (phone.matches("^[0-9]*")) {
            return valid = true;
        }
        else {
            throw new NotValidPhone("Phone number must be numeric");

        }
    }

    /**
     * This regex used to perform email validation is taken from {@link 'https://howtodoinjava.com/regex/java-regex-validate-email-address/'}
     *
     * @param email
     * @return
     */
    public static boolean validEmail(String email) throws NotValidEmail {
        boolean valid = false;
        if (email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
            return valid = true;
        }
        else {
            throw new NotValidEmail("Must be a valid email");

        }
    }

}
