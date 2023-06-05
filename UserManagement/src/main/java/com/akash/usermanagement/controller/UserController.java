package com.akash.usermanagement.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akash.usermanagement.dto.LoginRequest;
import com.akash.usermanagement.dto.UpdatePasswordRequest;
import com.akash.usermanagement.entities.User;
import com.akash.usermanagement.exception.InvalidLoginAttemptException;
import com.akash.usermanagement.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("api/auth/moviebooking")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Value("${application.jwt.secret-key}")
	private String secretKey;
	
	@Autowired
	private UserService userService; 
	
	@PostMapping("/register")
	public ResponseEntity<?> handleRegister(@RequestBody User user){
		try {
			userService.register(user);			
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok("Registration Successful");
	}
	
	public String generateToken(LoginRequest request) {
		return Jwts.builder()
				.setSubject(request.getUserName()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + (20 * 60 * 1000)))
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)), SignatureAlgorithm.HS256).compact();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> handleLogin(@RequestBody LoginRequest request){
		System.out.println("inside login");
		
		try {
			userService.login(request);			
		}catch(Exception ex) {
			System.out.println("inside exception");
			ex.printStackTrace();
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		final String token = generateToken(request);
//		System.out.println(token);
		Map<String,String> map = new HashMap<>();
		map.put("message", "User is Successfully logged in.");
		map.put("token", token);
		return ResponseEntity.ok(map);
	}
	
	@GetMapping("/getUser/{username}")
	public User getUser(@PathVariable("username") String username){
		return userService.getUser(username);
	}
	
	@GetMapping("/forgot/{userName}")
	public ResponseEntity<String> handleForgotPassword(@PathVariable("userName") String username){
		final String secretQuestion = userService.forgotPassword(username);
		if(secretQuestion==null)
			return new ResponseEntity<String>("Invalid Input", HttpStatus.BAD_REQUEST);
		return ResponseEntity.ok(secretQuestion);
	}
	
	@PutMapping("/forgot/{username}/updatepassword")
	public ResponseEntity<String> updatePassword(@PathVariable("username") String username, @RequestBody UpdatePasswordRequest updateRequest){
		final String newPwd = updateRequest.getNewPassword();
		final String secretAns = updateRequest.getSecretAnswer();
		final boolean isValidAnswer = userService.checkValidSecretAnswer(username,secretAns);
		if(isValidAnswer) {
			try {
				userService.updatePassword(username, newPwd);				
			}catch(Exception ex) {
				return new ResponseEntity<String>("Cannot update password, please try again!", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return ResponseEntity.ok("Successfully updated password");
		}
		return new ResponseEntity<String>("Invalid credentials", HttpStatus.UNAUTHORIZED);
	}
	
}
