package com.example.apidemo.database;

import com.example.apidemo.models.User;
import com.example.apidemo.repositories.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  //contains bin methods to khoi tao database, variables enviroment
public class Database {
    //logger in ra man hinh thong qua nhan vao class giup biet data in ra cua class nao(thay sout)
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository){
        return new CommandLineRunner() {//tao ban ghi mau vao database
            @Override
            public void run(String... args) throws Exception {
//                User userA = new User("Vi Trung Kien", "backend", "0343579228", "vtkien2511@gmail.com","Mo Lao, Ha Dong");
//                User userB = new User("Ma Dinh Chien", "backend", "0866638820", "chienft@gmail.com", "Mo Lao, Ha Dong");
//                logger.info("insert data: " + userRepository.save(userA));
//                logger.info("insert data: " + userRepository.save(userB));
            }
        };
    }
}
