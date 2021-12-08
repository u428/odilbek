package com.qorakol.ilm.ziyo.controller;

import com.qorakol.ilm.ziyo.constant.reg.Registers;
import com.qorakol.ilm.ziyo.model.dto.*;
import com.qorakol.ilm.ziyo.service.interfaces.AdminService;
import com.qorakol.ilm.ziyo.service.interfaces.GroupService;
import com.qorakol.ilm.ziyo.service.interfaces.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/a23d_m23_i23n")
public class AdminController {

    private final GroupService groupService;
    private final StaticService service;
    private final AdminService adminService;

    @Autowired
    public AdminController(GroupService groupService, StaticService service, AdminService adminService) {
        this.groupService = groupService;
        this.service = service;
        this.adminService = adminService;
    }

    @PostMapping(value = Registers.RegisterTeacher,
            consumes = {"multipart/form-data", "application/json"})
    public ResponseEntity<?> regTeacher(@Valid @ModelAttribute RegTeacherDto regTeacherDto){
        try {
            adminService.createTeacher(regTeacherDto);
            return ResponseEntity.ok("SUCCESS");
        }catch (Exception e){
            return ResponseEntity.ok("ERROR");
        }
    }

    @PostMapping(value = "/add_subject")
    public ResponseEntity addSubject(@RequestBody SubjectDto subjectDto){
        service.addSubject(subjectDto);
        return ResponseEntity.ok("SUCCESS");
    }

    @PutMapping(value = "/put_subject/{id}")
    public ResponseEntity putSubject(@PathVariable(name = "id") Long id, @RequestBody SubjectDto subjectDto){
        return ResponseEntity.ok(service.putSubject(id, subjectDto));
    }

    @DeleteMapping(value = "/delete_subject/{id}")
    public ResponseEntity deleteSubject(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(service.deleteSubject(id));
    }

    @PostMapping(value = "/add_group",
            consumes = {"multipart/form-data", "application/json"})
    public ResponseEntity addGroup(@Valid @ModelAttribute NewGroup newGroup){
        adminService.save(newGroup);
        return ResponseEntity.ok("SUCCESS");
    }

    @PutMapping(value = "/put_group/{id}",
            consumes = {"multipart/form-data", "application/json"})
    public ResponseEntity putGroup(@Valid @ModelAttribute NewGroup newGroup, @PathVariable Long id){
        adminService.changeGroup(newGroup, id);
        return ResponseEntity.ok("SUCCESS");
    }

    @PostMapping(value = "/main_image")
    public ResponseEntity mainImage(@ModelAttribute MainImageDto mainImageDto){
        return ResponseEntity.ok( adminService.addMainImage(mainImageDto));
    }

    @PutMapping(value = "/put_main_image")
    public ResponseEntity putMainImage(@ModelAttribute MainImageDto mainImageDto){
        return ResponseEntity.ok(adminService.putImage(mainImageDto));
    }


//    @DeleteMapping(value = "/delete_image")
//    public ResponseEntity deleteImage(@RequestParam Long id){
//        return ResponseEntity.ok(adminService.deleteImage(id));
//    }

    @PostMapping(value = "/payment")
    public ResponseEntity payment(@RequestBody PaymentDto paymentDto){
        return ResponseEntity.ok(adminService.paying(paymentDto));
    }

    @PutMapping(value = "/change_payment")
    public ResponseEntity changePayment(@RequestBody PaymentDto paymentDto){
        return ResponseEntity.ok(adminService.changePayment(paymentDto));
    }


}
