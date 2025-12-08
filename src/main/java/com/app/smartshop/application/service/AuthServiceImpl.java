package com.app.smartshop.application.service;
import com.app.smartshop.application.util.LoginResult;
import com.app.smartshop.application.util.SessionManager;
import com.app.smartshop.domain.enums.UserRole;
import com.app.smartshop.domain.entity.User;
import com.app.smartshop.domain.repository.JpaUserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService{
    private final JpaUserRepository userRepository;

    @Transactional(readOnly = true)
    public LoginResult login(String userName, String password, boolean rememberMe){
        User user = userRepository.findByUserName(userName).orElseThrow(
                ()-> new EntityNotFoundException("there is no User with this data "+"username : "+userName+"/ password : "+password)
        );

        boolean matches = BCrypt.checkpw(password,user.getHashedPassword());

        if(!matches){
            return LoginResult.builder()
                    .failure(true)
                    .message("wrong password")
                    .build();
        }else {
            return LoginResult.builder()
                    .success(true)
                    .user(user)
                    .rememberMe(rememberMe)
                    .build();
        }

    }

    public String register(String userName, String password, UserRole role){
        User user = userRepository.findByUserName(userName).orElse(new User());

        if(user.getId() != null){
            throw new EntityExistsException("there is already an account with this userName : "+userName);
        }

        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt());

        user.setUserName(userName);
        user.setHashedPassword(hashedPassword);
        user.setRole(role);
        userRepository.save(user);

        return "user created successfully";
    }

    @Transactional(readOnly = true)
    public void logout(HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = SessionManager.destroySession(request,response);
        response.addCookie(cookie);
    }

}
