package com.raveleen;

import com.raveleen.entities.CustomUser;
import com.raveleen.entities.enums.UserRole;
import com.raveleen.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Created by Святослав on 31.12.2016.
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner demo(final UserService userService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                userService.addUser(new CustomUser("admin", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.ADMIN));
                userService.addUser(new CustomUser("user1", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user2", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user3", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user4", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user5", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user6", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user7", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user8", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user9", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user10", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user11", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user12", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user13", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user14", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user15", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user16", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user17", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user18", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user19", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
                userService.addUser(new CustomUser("user20", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8", UserRole.USER));
            }
        };
    }
}
