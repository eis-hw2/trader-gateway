package com.example.trader.Service;

import com.example.trader.Domain.User;

public interface UserService {
    User login(User user);
    User register(User user);

}
