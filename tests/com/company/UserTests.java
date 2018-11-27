package com.company;

public class UserTests {

    @org.junit.jupiter.api.Test
    void userNameValid() {
      /*
      1. Length is between 6-10 characters
      2. Must not contain special characters
      3. Must be alphanumeric
      4. Cannot be null
       */
    }
    @org.junit.jupiter.api.Test
    void userNameUnique() {
      /*
      1. Two users must not have the same username
      2. Cannot be null
       */
    }
    @org.junit.jupiter.api.Test
    void validPassword() {
          /*
      1. Length is at least 10 characters
      2. Must contain at least one special character
      3. Must contain at least one uppercase letter
      4. Must contain at least one lowercase letter
      5. Must contain at least one number
      6. Cannot be null
       */
    }
    @org.junit.jupiter.api.Test
    void validFirstName() {
          /*
      1. Must be alphanumeric
      2. Cannot be null
      */
    }
    @org.junit.jupiter.api.Test
    void validLastName() {
          /*
      1. Must be alphanumeric
      2. Cannot be null
      */
    }
    @org.junit.jupiter.api.Test
    void validEmail() {
          /*
      1. Must be in email format
      2. Cannot be null
      */
    }

    @org.junit.jupiter.api.Test
    void validPhone() {
          /*
      1. Must only contain numbers
      */
    }

    @org.junit.jupiter.api.Test
    void validLocation() {
          /*
       1. Length is not longer than 10 characters
      */
    }




}
