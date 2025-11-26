package com.app.smartshop.application.service;
import com.app.smartshop.application.util.LoginResult;
import com.app.smartshop.domain.enums.UserRole;
import com.app.smartshop.domain.model.User;
import com.app.smartshop.domain.repository.IUserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final IUserRepository userRepository;

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

}
