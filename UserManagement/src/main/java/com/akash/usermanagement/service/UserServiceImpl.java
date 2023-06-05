package com.akash.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.usermanagement.dto.LoginRequest;
import com.akash.usermanagement.entities.User;
import com.akash.usermanagement.exception.InvalidLoginAttemptException;
import com.akash.usermanagement.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public void register(User user) {
		userRepo.save(user);
	}

	@Override
	public void login(LoginRequest request) {
//		System.out.println(request.getUserName());
//		System.out.println(request.getPassword());
		User user = userRepo.findByUserName(request.getUserName());
//		System.out.println(user.getUserName());
//		System.out.println(user.getPassword());
		if(!user.getPassword().equals(request.getPassword())) {
			throw new InvalidLoginAttemptException();
		}
	}

	@Override
	public User getUser(String username) {
		return userRepo.findByUserName(username);
	}

	@Override
	public String forgotPassword(String username) {
		User user = userRepo.findByUserName(username);
		if(user==null)return null;
		return user.getSecretQuestion();
	}

	@Override
	public boolean checkValidSecretAnswer(String username, String secretAns) {
		User user = getUser(username);
		if(user!=null && user.getSecretAnswer().equals(secretAns))
			return true;
		return false;
	}

	@Override
	public void updatePassword(String username, String newPwd) {
		User user = getUser(username);
		user.setPassword(newPwd);
		userRepo.save(user);
	}

}
