package com.patu.subscripts.services;

import com.patu.subscripts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

}
