package com.example.shiro.mapper;

import com.example.shiro.entity.Perms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CatPermMapper extends JpaRepository<Perms,Long> {

    @Query(value ="  SELECT DISTINCT c.`permission` FROM  `sys_user_role` f\n" +
            "      LEFT JOIN `role_perms` p ON  f.role_id=p.role_id\n" +
            "      LEFT JOIN `cat_perms` c ON p.perms_id=c.id\n" +
            "      WHERE f.user_id=:user_id" ,nativeQuery = true)
    public Set<String> getPermsByUserId(Long user_id);

}
