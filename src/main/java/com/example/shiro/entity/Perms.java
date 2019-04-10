package com.example.shiro.entity;

import javax.persistence.*;

@Table(name = "cat_perms")
@Entity
public class Perms {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String uri;

    /**
     * 权限表示
     */
    @Column
    private String permission;

    @Column(columnDefinition = "enum('1','2','3')")
    private String resourceType;
}
