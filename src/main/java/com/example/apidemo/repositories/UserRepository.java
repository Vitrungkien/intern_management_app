package com.example.apidemo.repositories;
//// Conrains method to get data from(a, b, c,..bla bla chu yeu tu database)
import com.example.apidemo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUserName(String userName);
    @Query("SELECT u  FROM User u WHERE u.userName LIKE %:keyword% OR u.email like %:keyword% OR u.position like %:keyword% OR u.mentor like %:keyword%")
    List<User> findByKeyword(@Param("keyword") String keyword);

}
