package com.samboy.dmcc.auth.listeners;

import com.samboy.dmcc.auth.model.User;

public interface OnLoginListeners {
    void onLoginStart(String msg);
    void onLoginSuccess(User user);
    void onLoginFail(String msg);
}
