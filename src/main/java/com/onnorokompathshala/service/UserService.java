package com.onnorokompathshala.service;

import com.onnorokompathshala.Dto.JWTAuthResponse;
import com.onnorokompathshala.Dto.LoginDTO;
import com.onnorokompathshala.Dto.SignupDTO;
import com.onnorokompathshala.entities.Role;
import com.onnorokompathshala.entities.User;
import com.onnorokompathshala.jwt.JWTTokenProvider;
import com.onnorokompathshala.repo.RoleRepository;
import com.onnorokompathshala.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JWTTokenProvider jwtTokenProvider;

    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public ResponseEntity<JWTAuthResponse> signInUser(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token= jwtTokenProvider.generateToken(authentication);
        return  ResponseEntity.ok(new JWTAuthResponse(token));
    }

    public ResponseEntity<?> registerUser(SignupDTO signupDTO) {
        // check if email already exists in DB
        if (userRepository.existsByEmail(signupDTO.getEmail())) {
            return new ResponseEntity<>("Email Already Taken.", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setUserName(signupDTO.getName());
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));

        Role roles = roleRepository.findByRole("ROLE_USER").get();
        user.setRoles(Collections.singleton(roles));
        userRepository.save(user);
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.OK);
    }

    public Optional<User> findByUserId(Long usrId){
        return userRepository.findById(usrId);
    }
}
