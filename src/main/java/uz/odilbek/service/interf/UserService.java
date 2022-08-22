package uz.odilbek.service.interf;

import org.springframework.security.core.userdetails.UserDetailsService;
import uz.odilbek.model.dto.ApplicationDto;
import uz.odilbek.model.dto.SingUp;

import java.util.Map;

public interface UserService extends UserDetailsService {
    Map<String, Object> getCurrentUser(String userName);

    Object checkLogin(String login);

    Object getRoles(String login);

    Object signUp(SingUp singUp);

    Object getApplicationList(String current);

    Object createApplication(ApplicationDto applicationDto, String login);
}
