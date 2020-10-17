package com.samboy.dmcc.auth.listeners;

import com.samboy.dmcc.auth.model.User;

public interface OnResisterListeners {
    void onRegisterStart(String msg);
    void onRegisterSuccess(User user);
    void onRegisterFail(String msg);
}
