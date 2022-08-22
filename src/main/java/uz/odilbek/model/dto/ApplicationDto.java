package uz.odilbek.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ApplicationDto {

    public String faculty;
    public String groupName;
    public int course;
    public boolean grant;
    public Long eImage;
    public Integer faStatus;
    public Long faImages;
    public List<Long> disImages;
    public int member;
    public Integer famStatus;
    public Long famImages;

}
