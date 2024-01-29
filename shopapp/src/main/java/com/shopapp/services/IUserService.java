package com.shopapp.services;

import com.shopapp.dtos.UserDTO;
import com.shopapp.models.User;
import com.shopapp.exceptions.DataNotFoundException;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
