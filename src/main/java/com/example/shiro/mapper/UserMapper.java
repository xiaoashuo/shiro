package com.example.shiro.mapper;

import com.example.shiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;


public interface UserMapper extends JpaRepository<User,Long> {

    @Query(value = " SELECT r.id FROM sys_user u\n" +
            "      LEFT JOIN sys_user_role f ON u.id=f.user_id\n" +
            "      LEFT JOIN sys_role r ON f.role_id=r.id WHERE u.id=:id ",nativeQuery = true )
    public Set<String> getRIdByUid(Long id);

    @Query(value = "SELECT * FROM sys_user where last_name=:username ",nativeQuery = true)
    public User getByName(String username);
}
