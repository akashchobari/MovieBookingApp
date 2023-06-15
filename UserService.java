package com.akash.usermanagement.service;

import com.akash.usermanagement.dto.LoginRequest;
import com.akash.usermanagement.entities.User;

public interface UserService {
	public void register(User user);

	public void login(LoginRequest request);
	public User getUser(String username);

	public String forgotPassword(String username);

	public boolean checkValidSecretAnswer(String username,String secretAns);

	public void updatePassword(String username, String newPwd);
}
