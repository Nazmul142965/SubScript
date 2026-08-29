package com.patu.subscripts.services;

import com.patu.subscripts.dto.UserRegistrationDTO;
import com.patu.subscripts.model.Address;
import com.patu.subscripts.model.User;
import com.patu.subscripts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(UserRegistrationDTO dto){
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new RuntimeException("User with this email already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        user.setRole("ROLE_USER");

        Address address = new Address();
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        user.setAddress(address);


        userRepository.save(user);
    }



}
