package com.simbirsoft.services;

import com.simbirsoft.forms.UserForm;

public interface UserService {

    void save(UserForm form);

    boolean isUserExist(String login);
}