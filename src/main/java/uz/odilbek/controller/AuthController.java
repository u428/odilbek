package uz.odilbek.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.odilbek.model.dto.LogInDto;
import uz.odilbek.model.dto.SingUp;
import uz.odilbek.security.CurrentUser;
import uz.odilbek.security.JwtTokenProvider;
import uz.odilbek.service.interf.UserService;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LogInDto logInDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logInDto.getLogin(), logInDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Map<String, Object> maps = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(maps);
    }

    @PostMapping("/singUp")
    public HttpEntity<?> singUn(@RequestBody SingUp singUp) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.signUp(singUp));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ERROR");
        }
    }


    @GetMapping(value = "/check_login")
    public ResponseEntity<?> checkLogin(@RequestParam(name = "login") String login){
        return ResponseEntity.ok(userService.checkLogin(login));
    }


    @GetMapping(value = "/get_role")
    public ResponseEntity getRole(@CurrentUser String login){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getRoles(login));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ERROR");
        }
    }

    @GetMapping(value = "/get_current_user")
    public ResponseEntity getCurrentUser(@CurrentUser String login){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getCurrentUser(login));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("ERROR");
        }
    }
}
