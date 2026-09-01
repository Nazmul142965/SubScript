package com.patu.subscripts;

import com.patu.subscripts.model.Address;
import com.patu.subscripts.model.User;
import com.patu.subscripts.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SubScriptsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubScriptsApplication.class, args);
    }

    @Bean
    public CommandLineRunner initAdmin(UserRepository repo, PasswordEncoder encoder){
        return args -> {
            if(repo.findByEmail("admin@gmail.com").isEmpty()){
                User admin = new User();
                admin.setUsername("SuperAdmin");
                admin.setEmail("admin@gmail.com");
                admin.setPassword(encoder.encode("admin123"));


                admin.setRole("ROLE_ADMIN");

                Address address = new Address();
                address.setCity("Dhaka");
                address.setCountry("Bangladesh");
                admin.setAddress(address);

                repo.save(admin);

                System.out.println("CommandLineRunner is Working");
            }
        };
    }

}
