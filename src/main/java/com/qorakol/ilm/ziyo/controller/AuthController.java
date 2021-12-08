package com.qorakol.ilm.ziyo.controller;

import com.qorakol.ilm.ziyo.constant.reg.Registers;
import com.qorakol.ilm.ziyo.model.dto.*;
import com.qorakol.ilm.ziyo.security.CurrentUser;
import com.qorakol.ilm.ziyo.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.MalformedURLException;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(value = "/check_login")
    public ResponseEntity<?> checkLogin(@RequestParam(name = "login") String login){
        return ResponseEntity.ok(authService.checkLogin(login));
    }

    @PostMapping(path = "/add_student_login")
    public ResponseEntity addStudentLogin(@Valid @RequestBody StudentLogin studentLogin){
        return ResponseEntity.ok(authService.addStudentLogin(studentLogin));
    }

    @PostMapping(value = "/student_group")
    public ResponseEntity studentGroup(@Valid @RequestBody SToGroup sToGroup){
        authService.studentAddGroup(sToGroup);
        return ResponseEntity.ok("SUCCESS");
    }

    @PostMapping(value = Registers.RegisterStudent)
    public ResponseEntity<?> regStudent(@Valid @RequestBody RegStudentDto regStudentDto){
        try {
            return ResponseEntity.ok(authService.createStudent(regStudentDto));
        }catch (Exception e){
            return ResponseEntity.ok("-1");
        }
    }



    @GetMapping(value = "/get_role")
    public ResponseEntity getRole(@CurrentUser String login){
        return ResponseEntity.ok(authService.getRoles(login));
    }

    @GetMapping(value = "/get_current_user")
    public ResponseEntity getCurrentUser(@CurrentUser String login){
        return ResponseEntity.ok(authService.getCurrentUser(login));
    }

    @PostMapping(value = "/add_admin")
    public ResponseEntity addAdmin(@RequestBody AdminDto adminDto){
        authService.addAdmin(adminDto);
        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping()
    public String addRole(){
        authService.addRole();
        return "sda";
    }

    @GetMapping(value = "/get_admin")
    public ResponseEntity getADmins(){
        return ResponseEntity.ok(authService.getAdmins());
    }
}
