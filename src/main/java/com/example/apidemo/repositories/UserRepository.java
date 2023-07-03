package com.example.apidemo.repositories;
//// Conrains method to get data from(a, b, c,..bla bla chu yeu tu database)
import com.example.apidemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserName(String userName);
    @Query("SELECT u  FROM User u WHERE u.userName LIKE %:keyword% OR u.address LIKE %:keyword% OR" +
            " u.email like %:keyword% OR u.phoneNumber like %:keyword% OR u.position like %:keyword%")
    List<User> findByKeyword(@Param("keyword") String keyword);

}
