package com.qorakol.ilm.ziyo.service.interfaces;

import com.qorakol.ilm.ziyo.model.dto.CheckStudents;

public interface TeacherService {
//    Object getGroups(String login);

    Object getGroupStudents(String login, Long id);

    Object checkStudent(String login, CheckStudents checkStudents) throws Exception;
}
