package com.qorakol.ilm.ziyo.model.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class NewGroup {

    public String name;

    public Date begin;

    public Date finish;

    public Long teacherId;

    public double price;

    public Long subjectId;

    public Long languageId;

}
