package com.example.shiro.service;

import java.util.Set;

public interface CatPermService {

    public Set<String> getPermsByUserId(Long user_id);
}
