package com.sixb.note.api.controller;

import com.sixb.note.api.service.UserService;
import com.sixb.note.dto.request.UserInfoRequestDto;
import com.sixb.note.dto.response.NicknameResponseDto;
import com.sixb.note.dto.response.UserInfoResponseDto;
import com.sixb.note.entity.User;
import com.sixb.note.exception.InvalidTokenException;
import com.sixb.note.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
		try {
			UserInfoResponseDto response = userService.getUserInfo(token);
			return ResponseEntity.ok(response);
		} catch (InvalidTokenException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
		}
    }

	@PostMapping
	public ResponseEntity<?> signup(@RequestHeader("Authorization") String token,
									@RequestBody UserInfoRequestDto request) {
		try {
			UserInfoResponseDto response = userService.signup(token, request);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (InvalidTokenException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

	@PatchMapping
	public ResponseEntity<?> updateUserInfo(@RequestHeader("Authorization") String token,
											@RequestBody UserInfoRequestDto request) {
		try {
			UserInfoResponseDto response = userService.updateUserInfo(token, request);
			return ResponseEntity.ok(response);
		} catch (InvalidTokenException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		} catch (UserNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{nickname}")
	public ResponseEntity<NicknameResponseDto> checkNickname(@PathVariable String nickname) {
		NicknameResponseDto response = userService.checkNickname(nickname);
		return ResponseEntity.ok(response);
	}


	@GetMapping("/test/{userId}")
	public ResponseEntity<User> getUserDetails(@PathVariable String userId) {
		User user = userService.getUserDetails(userId);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}

}
