package com.security.auth2Project.service;

import com.security.auth2Project.enums.AuthProvider;
import com.security.auth2Project.model.User;
import com.security.auth2Project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUserLocal(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthProvider(AuthProvider.LOCAL);
        return userRepository.save(user);
    }

    public User loginUserLocal(User user){
        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null){
            if(!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())){
                throw new RuntimeException("User password does not match");
            }
            return existingUser;
        }
        throw new RuntimeException("User not Found");

    }

    public User loginRegisterWithGoogleAuth2(OAuth2AuthenticationToken auth2AuthenticationToken){
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
    }

}
