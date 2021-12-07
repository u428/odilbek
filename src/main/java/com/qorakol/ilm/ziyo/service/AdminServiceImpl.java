package com.qorakol.ilm.ziyo.service;

import com.qorakol.ilm.ziyo.constant.RoleContants;
import com.qorakol.ilm.ziyo.model.dto.MainImageDto;
import com.qorakol.ilm.ziyo.model.dto.NewGroup;
import com.qorakol.ilm.ziyo.model.dto.PaymentDto;
import com.qorakol.ilm.ziyo.model.dto.RegTeacherDto;
import com.qorakol.ilm.ziyo.model.entity.*;
import com.qorakol.ilm.ziyo.repository.*;
import com.qorakol.ilm.ziyo.service.interfaces.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class AdminServiceImpl implements AdminService {


    private final GroupsRepository groupsRepository;
    private final LanguageRepository languageRepository;
    private final ImagesRepository imagesRepository;
    private final MainImagesRepository mainImagesRepository;
    private final PaymentRepository paymentRepository;
    private final AttendanceRepository attendanceRepository;
    private final ActivationRepository activationRepository;
    private final TeacherRepository teacherRepository;
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final SubjectsRepository subjectsRepository;

    private Path fileStoragePath;

    @Autowired
    public AdminServiceImpl(GroupsRepository groupsRepository, LanguageRepository languageRepository, ImagesRepository imagesRepository, MainImagesRepository mainImagesRepository, PaymentRepository paymentRepository, AttendanceRepository attendanceRepository, ActivationRepository activationRepository, TeacherRepository teacherRepository, AuthRepository authRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, SubjectsRepository subjectsRepository) {
        this.groupsRepository = groupsRepository;
        this.languageRepository = languageRepository;
        this.imagesRepository = imagesRepository;
        this.mainImagesRepository = mainImagesRepository;
        this.paymentRepository = paymentRepository;
        this.attendanceRepository = attendanceRepository;
        this.activationRepository = activationRepository;
        this.teacherRepository = teacherRepository;
        this.authRepository = authRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.subjectsRepository = subjectsRepository;
        fileStoragePath = Paths.get("/home/robotus/app/java/java_code").toAbsolutePath().normalize();
        if (!Files.exists(fileStoragePath)){
            try {
                Files.createDirectories(fileStoragePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
        List<Subjects> subjectsList = subjectsRepository.findAllByIdIn(regTeacherDto.getSubjectIds());
        List<Language> languageList = languageRepository.findAllByIdIn(regTeacherDto.getLangIds());
        teacher.setLanguages(languageList);
        teacher.setSubjects(subjectsList);
        try {
            MultipartFile multipartFile = regTeacherDto.getFiles();
            Images images = new Images();
            images.setContentType(multipartFile.getContentType());
            images.setName(multipartFile.getOriginalFilename());
            images.setFileSize(multipartFile.getSize());
            imagesRepository.save(images);
            String AA = multipartFile.getOriginalFilename();
            String fileName = String.valueOf(images.getId()) + AA.substring(AA.length() - 4, AA.length());
            images.setExtention(AA.substring(AA.length() - 4));
            System.out.println(fileStoragePath);
            Path filePath = Paths.get(fileStoragePath + "//" + fileName);
            images.setUploadPath(String.valueOf(filePath));
            Files.createDirectories(fileStoragePath);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            imagesRepository.save(images);
            teacher.setImagesId(images.getId());
            authRepository.save(authEntity);
            teacher.setAuthId(authEntity.getId());
            teacherRepository.save(teacher);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Object addMainImage(MainImageDto mainImageDto) {
        MainImage mainImage = new MainImage();
        mainImage.setDescryption(mainImageDto.getDescryption());
        mainImage.setName(mainImageDto.getName());
        try {
            MultipartFile multipartFile = mainImageDto.getFiles();
            Images images = new Images();
            images.setContentType(multipartFile.getContentType());
            images.setName(multipartFile.getOriginalFilename());
            images.setFileSize(multipartFile.getSize());
            imagesRepository.save(images);
            String AA = multipartFile.getOriginalFilename();
            String fileName = String.valueOf(images.getId()) + AA.substring(AA.length() - 4, AA.length());
            images.setExtention(AA.substring(AA.length() - 4));
            System.out.println(fileStoragePath);
            Path filePath = Paths.get(fileStoragePath + "//" + fileName);
            images.setUploadPath(String.valueOf(filePath));
            Files.createDirectories(fileStoragePath);
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            imagesRepository.save(images);
            mainImage.setImagesId(images.getId());
            mainImagesRepository.save(mainImage);
            return "SUCCESS";
        }catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public void save(NewGroup newGroup) {
        Groups groups = new Groups();
        BeanUtils.copyProperties(newGroup, groups);
        groups.setName(newGroup.getName());
        groups.setLanguageId(newGroup.getLanguageId());
        groups.setSubjectId(newGroup.getSubjectId());
        groups.setBegin(newGroup.getBegin());
        groups.setFinish(newGroup.getFinish());
        groups.setTeacherId(newGroup.getTeacherId());
        groups.setPrice(newGroup.getPrice());

        try {
            MultipartFile multipartFile = newGroup.getFiles();
            Images images = new Images();
            images.setContentType(multipartFile.getContentType());
            images.setName(multipartFile.getOriginalFilename());
            images.setFileSize(multipartFile.getSize());
            imagesRepository.save(images);
            String AA = multipartFile.getOriginalFilename();
            String fileName = String.valueOf(images.getId()) + AA.substring(AA.length() - 4, AA.length());
            images.setExtention(AA.substring(AA.length() - 4));
            System.out.println(fileStoragePath);
            Path filePath = Paths.get(fileStoragePath + "//" + fileName);
            images.setUploadPath(String.valueOf(filePath));
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            imagesRepository.save(images);
            groups.setImagesId(images.getId());
            groupsRepository.save(groups);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public Object putImage(MainImageDto mainImageDto) {
        if (mainImageDto.getId()!= null){
            MainImage mainImage = mainImagesRepository.findById(mainImageDto.getId()).get();
            BeanUtils.copyProperties(mainImageDto, mainImage);
            if (mainImageDto.getFiles()!=null){
                try {
                    addImage(mainImageDto.getFiles(), mainImage.getImagesId());

                }catch (Exception e){
                    return e.getMessage();
                }
            }
            mainImagesRepository.save(mainImage);
            return "SUCCESS";
        }
        return "ERROR";
    }

    @Override
    public Object paying(PaymentDto paymentDto) {
        Activation activation = activationRepository.findByStudentIdAndDeleteIsFalse(paymentDto.getStudentId());
        Payments payments = new Payments();
        Groups groups = groupsRepository.findById(paymentDto.getGroupId()).get();
        payments.setSumma(paymentDto.getSumma());
        payments.setDarsSoati(groups.getPrice()/paymentDto.getSumma());
        payments.setGroupId(paymentDto.getGroupId());
        payments.setStudentId(paymentDto.getStudentId());
        payments.setTime(new Date());
        paymentRepository.save(payments);

        double a = paymentRepository.findByIdAndOrderByDate(payments.getId()-1).getQolganDarsi();
        a+=payments.getDarsSoati();
        List<Attendances> attendancesLIst = attendanceRepository.findAllByCountedIsFalseOrderById();
        for (Attendances attendances: attendancesLIst){
            if (a==0){
                activation.setActive(false);
                break;
            }
            attendances.setCounted(true);
            a--;
            attendanceRepository.save(attendances);
            activation.setActive(false);
        }
        activationRepository.save(activation);
        payments.setQolganDarsi(a);
        paymentRepository.save(payments);
        return "SUCCESS";
    }

    @Override
    public Object changePayment(PaymentDto paymentDto) {
        Activation activation = activationRepository.findByStudentIdAndDeleteIsFalse(paymentDto.getStudentId());
        Payments payments = paymentRepository.findById(paymentDto.getId()).get();
        Groups groups = groupsRepository.findById(paymentDto.getGroupId()).get();
        payments.setSumma(paymentDto.getSumma());
        payments.setGroupId(paymentDto.getGroupId());
        payments.setStudentId(paymentDto.getStudentId());
        payments.setTime(new Date());
        paymentRepository.save(payments);
        double b = paymentDto.getSumma()/groups.getPrice();
        double a = paymentRepository.findById(payments.getId()-1).get().getQolganDarsi();
        double r = a+payments.getDarsSoati();
        r-=payments.getQolganDarsi();
        a+=b;
        r=a-r;
        if (r>0){

        }else{

        }

        return null;
    }

    @Override
    public Object deleteImage(Long id) {
        Images images = imagesRepository.findById(id).get();
        return null;
    }

    @Override
    public Object getTeachers() {
        List<Teacher> list = teacherRepository.findAll();
        List<Map> returns = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            Map<String, Object> map = new HashMap<>();
            List<Groups> groups = groupsRepository.findAllByTeacherIdAndDeleteIsFalse(list.get(i).getId());
            map.put("groups", groups.size());
            map.put("teachers", list.get(i));
            returns.add(map);
        }
        return returns;
    }

    private void addImage(MultipartFile multipartFile, Long id) throws IOException {
        String AA=multipartFile.getOriginalFilename();
        Images image = imagesRepository.findById(id).get();
        image.setFileSize(multipartFile.getSize());
        image.setName(multipartFile.getOriginalFilename());
        image.setContentType(multipartFile.getContentType());

        Path path=Paths.get(image.getUploadPath());
        String fileName = String.valueOf(image.getId())+AA.substring(AA.length()-4, AA.length());
        Path filePath = Paths.get(fileStoragePath + "//" + fileName);
        System.out.println(filePath);
        image.setUploadPath(String.valueOf(filePath));
        image.setExtention(AA.substring(AA.length()-4));
        imagesRepository.save(image);

        try {
            System.out.println(filePath);
            System.out.println(path);

            if(Files.exists(path)){
                Files.delete(path);
            }
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
