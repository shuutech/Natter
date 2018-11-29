package com.company.interfaces;

import com.company.exceptions.NotValidLoginException;
import com.company.exceptions.NotValidUserException;
import com.company.objects.User;

public interface Login {

    boolean isUserLoggedOn(String username, String password) throws NotValidLoginException;

    User getUser(String username) throws NotValidUserException;
}

