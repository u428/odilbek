package com.qorakol.ilm.ziyo.service;

import com.qorakol.ilm.ziyo.constant.RoleContants;
import com.qorakol.ilm.ziyo.constant.StudentStatus;
import com.qorakol.ilm.ziyo.model.dto.AdminDto;
import com.qorakol.ilm.ziyo.model.dto.RegStudentDto;
import com.qorakol.ilm.ziyo.model.dto.RegTeacherDto;
import com.qorakol.ilm.ziyo.model.dto.SToGroup;
import com.qorakol.ilm.ziyo.model.entity.*;
import com.qorakol.ilm.ziyo.repository.*;
import com.qorakol.ilm.ziyo.service.interfaces.AuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final ImagesRepository imagesRepository;
    private final RoleRepository roleRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GroupsRepository groupsRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LanguageRepository languageRepository;


    @Autowired
    public AuthServiceImpl(AuthRepository authRepository, ImagesRepository imagesRepository, RoleRepository roleRepository, TeacherRepository teacherRepository, StudentRepository studentRepository, GroupsRepository groupsRepository, BCryptPasswordEncoder bCryptPasswordEncoder, LanguageRepository languageRepository) {
        this.authRepository = authRepository;
        this.imagesRepository = imagesRepository;
        this.roleRepository = roleRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.groupsRepository = groupsRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.languageRepository = languageRepository;
    }

    @Override
    public boolean checkLogin(String login) {
        return authRepository.existsByLogin(login);
    }

    @Override
    public void createTeacher(RegTeacherDto regTeacherDto) {
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(regTeacherDto, teacher);
        AuthEntity authEntity=new AuthEntity();
        authEntity.setLogin(regTeacherDto.getLogin());
        authEntity.setPassword(bCryptPasswordEncoder.encode(regTeacherDto.getPassword()));
        Roles role = roleRepository.findByName(RoleContants.TEACHER);
        authEntity.setRolesId(role.getId());
        authRepository.save(authEntity);
        teacher.setAuthEntity(authEntity);
        teacherRepository.save(teacher);
    }
    @Override
    public Long createStudent(RegStudentDto regStudentDto) {
        Student student =  new Student();
        BeanUtils.copyProperties(regStudentDto, student);
        student.setStatus(StudentStatus.YANGI);
        Long result = studentRepository.save(student).getId();
        return result;
    }

    @Override
    public Map<String, Object> getCurrentUser(String login) {
        AuthEntity authEntity = authRepository.findByLogin(login);
        Teacher teacher = teacherRepository.findByAuthEntity(authEntity);
        Student student = null;
        Map<String, Object> result = new HashMap<>();
        if (teacher == null){
            student = studentRepository.findByAuthEntity(authEntity);
            result.put("user", student);
        }else{
            result.put("user", teacher);
        }
        result.put("role", authEntity.getRoles());
        return result;
    }

    @Override
    public void studentAddGroup(SToGroup sToGroup) {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setLogin(sToGroup.getLogin());
        authEntity.setPassword(bCryptPasswordEncoder.encode(sToGroup.getPassword()));
        Roles roles = roleRepository.findByName(RoleContants.STUDENT);
        authEntity.setRoles(roles);
        authRepository.save(authEntity);
        Student student = studentRepository.findById(sToGroup.getStudentId()).get();
        Groups groups = groupsRepository.findById(sToGroup.getGroupId()).get();
        student.setAuthEntity(authEntity);
        Set<Groups> groupsSet= student.getGroupsSet();
        groupsSet.add(groups);
        student.setGroupsSet(groupsSet);
        studentRepository.save(student);
    }

    @Override
    public void addAdmin(AdminDto adminDto) {
        Student student = new Student();
        student.setFirstName(adminDto.getFirstName());
        student.setLastName(adminDto.getLastName());
        AuthEntity authEntity=new AuthEntity();
        authEntity.setLogin(adminDto.getLogin());
        authEntity.setPassword(bCryptPasswordEncoder.encode(adminDto.getPassword()));
        Roles roles = roleRepository.findByName(RoleContants.ADMIN);
        authEntity.setRolesId(roles.getId());
        student.setAuthEntity(authEntity);

        studentRepository.save(student);
    }

    @Override
    public ResponseEntity images(Long id) throws MalformedURLException {
        Images images=imagesRepository.findById(id).get();
        Path path= Paths.get(images.getUploadPath());
        Resource resource= new UrlResource(path.toUri());
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(images.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName="+resource.getFilename())
                .body(resource);
    }

    @Override
    public Roles getRoles(String login) {
        return authRepository.findByLogin(login).getRoles();
    }




    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        AuthEntity user=authRepository.findByLogin(s);
        if (user == null) throw new UsernameNotFoundException(s);
        return new User(String.valueOf(user.getLogin()), user.getPassword(), getAuthority(user));
    }
    private Set<SimpleGrantedAuthority> getAuthority(AuthEntity authEntity) {
        Set<SimpleGrantedAuthority> authorities =new HashSet<>();
//        Set<SimpleGrantedAuthority> authorities = priviligesRepository.findAllByRoleId(customer.getRole().getId()).stream().map(priviliges ->
//                new SimpleGrantedAuthority(priviliges.getName())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+authEntity.getRoles().getName()));
        return authorities;
    }

    public void addRole(Roles roles){
        roleRepository.save(roles);
    }

    @Override
    public void addRole(){
//        Roles roles = new Roles();
//        roles.setLevel(1);
//        roles.setName(RoleContants.SUPER_ADMIN);
//        roleRepository.save(roles);
//        Roles roles2 = new Roles();
//        roles2.setLevel(2);
//        roles2.setName(RoleContants.ADMIN);
//        roleRepository.save(roles2);
//        Roles roles3 = new Roles();
//        roles3.setLevel(3);
//        roles3.setName(RoleContants.TEACHER);
//        roleRepository.save(roles3);
//        Roles roles4 = new Roles();
//        roles4.setLevel(4);
//        roles4.setName(RoleContants.STUDENT);
//        roleRepository.save(roles4);
        Language language = new Language();
        language.setName("English");
        Language language2 = new Language();
        language2.setName("Русский");
        Language language3 = new Language();
        language3.setName("O'zbek");
        languageRepository.save(language3);
        languageRepository.save(language2);
        languageRepository.save(language);

    }

}
