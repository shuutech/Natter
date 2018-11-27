package com.company.interfaces;

import com.company.objects.User;

public interface Login {

    boolean isUserLoggedOn(String username, String password);

    User getUser(String username);
}

