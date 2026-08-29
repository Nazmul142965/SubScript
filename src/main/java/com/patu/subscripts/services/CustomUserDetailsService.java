package com.patu.subscripts.services;

import com.patu.subscripts.model.User;
import com.patu.subscripts.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // ১. ডাটাবেস থেকে ইমেইল দিয়ে ইউজার খোঁজা
        // আপনার রিকোয়ারমেন্ট অনুযায়ী ইমেইল দিয়েই লগইন হবে
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // ২. স্প্রিং সিকিউরিটির বোঝার মতো একটি UserDetails অবজেক্ট তৈরি করে রিটার্ন করা
        // এখানে আপনার ডাটাবেসের ইমেইল, পাসওয়ার্ড এবং রোল ব্যবহার করা হচ্ছে
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail()) // ব্রাউজার থেকে আসা ইমেইল এখানে চেক হবে
                .password(user.getPassword())  // ডাটাবেসের এনক্রিপ্টেড পাসওয়ার্ড
                .roles(user.getRole().replace("ROLE_", "")) // 'ROLE_USER' থাকলে সেটি শুধু 'USER' হিসেবে যাবে
                .build();
    }
}