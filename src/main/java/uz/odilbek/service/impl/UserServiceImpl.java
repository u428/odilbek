package uz.odilbek.service.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uz.odilbek.constant.ApplicationStatus;
import uz.odilbek.constant.FacilityStatus;
import uz.odilbek.constant.FamilyStatus;
import uz.odilbek.constant.RoleContants;
import uz.odilbek.model.dto.ApplicationDto;
import uz.odilbek.model.dto.SingUp;
import uz.odilbek.model.entity.Users;
import uz.odilbek.model.entity.auth.Roles;
import uz.odilbek.model.entity.reg.RegApplication;
import uz.odilbek.repository.ApplicationRepository;
import uz.odilbek.repository.RoleRepository;
import uz.odilbek.repository.UserRepository;
import uz.odilbek.service.interf.UserService;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationRepository applicationRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ApplicationRepository applicationRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Map<String, Object> getCurrentUser(String login) {
        System.out.println(login);
        Users users = userRepository.findByLogin(login);
        Map<String, Object> result = new HashMap<>();
        if (users != null) {
            users.setId(null);
            result.put("user", users);
            result.put("role", users.getRoles());
        }
        return result;
    }

    @Override
    public Object checkLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public Object getRoles(String login) {
        return null;
    }

    @Override
    public Object signUp(SingUp singUp) {
        Users users = new Users();
        users.setFirstName(singUp.getFirstName());
        users.setLastName(singUp.getLastName());
        users.setMiddleName(singUp.getMiddleName());
        users.setTelNumber(singUp.getTelNumber());
        Roles roles = roleRepository.findByName(RoleContants.USER);
        users.setRolesId(roles.getId());
        users.setLogin(singUp.getLogin());
        users.setPassword(bCryptPasswordEncoder.encode(singUp.getPassword()));
        userRepository.save(users);
        return true;
    }

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        Users users=userRepository.findByLogin(s);
        if (users == null) throw new UsernameNotFoundException(s);
        return new User(String.valueOf(users.getLogin()), users.getPassword(), getAuthority(users));
    }
    private Set<SimpleGrantedAuthority> getAuthority(Users users) {
        Set<SimpleGrantedAuthority> authorities =new HashSet<>();
//        Set<SimpleGrantedAuthority> authorities = priviligesRepository.findAllByRoleId(customer.getRole().getId()).stream().map(priviliges ->
//                new SimpleGrantedAuthority(priviliges.getName())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+users.getRoles().getName()));
        return authorities;
    }

    @Override
    public Object getApplicationList(String login) {
        Users users = userRepository.findByLogin(login);
        List<RegApplication> list= applicationRepository.findByCreatedByAndDeletedIsFalse(users.getId());

        return list;
    }

    @Override
    public Object createApplication(ApplicationDto applicationDto, String login) {
        int persent = 0;
        Users users = userRepository.findByLogin(login);
        RegApplication regApplication = applicationRepository.findByCreatedByAndApplicationStatusIsNotAndDeletedIsFalse(users.getId(), ApplicationStatus.DONE);
        if (regApplication != null){
            return false;
        }
        regApplication = new RegApplication();
        regApplication.setFirstName(users.getFirstName());
        regApplication.setLastName(users.getLastName());
        regApplication.setMiddleName(users.getMiddleName());
        regApplication.setTelNumber(users.getTelNumber());

        regApplication.setFaculty(applicationDto.getFaculty());
        regApplication.setCourse(applicationDto.getCourse());
        regApplication.setGroupName(applicationDto.getGroupName());
        regApplication.setGrand(applicationDto.isGrant());
        if (applicationDto.getCourse() == 1){
            persent += 15;
            regApplication.setEducationImage(applicationDto.getEImage());
        }else {
            persent += 10;
        }
        if (applicationDto.getFaStatus() != null){
            regApplication.setFacilityStatus(FacilityStatus.findOrdinal(applicationDto.getFaStatus()));
            persent +=30;
        }
        if (applicationDto.getDisImages().size() != 0){
            regApplication.setDisabilityImage(applicationDto.getDisImages());
            persent +=30;
        }
        if (applicationDto.getFamStatus() != null){
            regApplication.setFamilyStatus(FamilyStatus.findOrdinal(applicationDto.getFamStatus()));
            switch (applicationDto.getFamStatus()){
                case 1:
                    persent +=10;
                case 2:
                case 3:
                    persent += 15;
                case 4:
                case 5:
                    persent +=25;
            }
        }
        regApplication.setCreatedBy(users.getId());
        regApplication.setPersentage(persent);
        regApplication.setApplicationStatus(ApplicationStatus.NEW);
        applicationRepository.save(regApplication);
        return true;
    }

    public void addRole() {
        Roles role1= new Roles();
        role1.setName(RoleContants.USER);
        role1.setLevel(1);
        roleRepository.save(role1);
        Roles role2= new Roles();
        role2.setName(RoleContants.MODERATOR);
        role2.setLevel(2);
        roleRepository.save(role2);
        Roles role3= new Roles();
        role3.setName(RoleContants.ADMIN);
        role3.setLevel(3);
        roleRepository.save(role3);
        Roles role4= new Roles();
        role4.setName(RoleContants.SUPER_ADMIN);
        role4.setLevel(5);
        roleRepository.save(role4);
        Roles role5= new Roles();
        role5.setName(RoleContants.ANONYMOUS_USER);
        role5.setLevel(0);
        roleRepository.save(role5);
    }
}
