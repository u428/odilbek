package com.qorakol.ilm.ziyo.service.interfaces;

import com.qorakol.ilm.ziyo.model.dto.MainImageDto;
import com.qorakol.ilm.ziyo.model.dto.NewGroup;
import com.qorakol.ilm.ziyo.model.dto.PaymentDto;
import com.qorakol.ilm.ziyo.model.dto.RegTeacherDto;

public interface AdminService {
    Object addMainImage(MainImageDto mainImageDto);
    void save(NewGroup newGroup);

    Object putImage(MainImageDto mainImageDto);

    Object paying(PaymentDto paymentDto);

    Object changePayment(PaymentDto paymentDto);

    Object deleteImage(Long id);

    void createTeacher(RegTeacherDto regTeacherDto);

    void changeGroup(NewGroup newGroup, Long id);
}
