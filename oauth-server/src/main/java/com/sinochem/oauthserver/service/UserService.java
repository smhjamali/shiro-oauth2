package com.sinochem.oauthserver.service;

import com.sinochem.oauthserver.entity.Oauth2User;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface UserService {
    /**
     * 创建用户
     * @param user
     */
    Oauth2User createUser(Oauth2User user);

    Oauth2User updateUser(Oauth2User user);

    void deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    void changePassword(Long userId, String newPassword);

    Oauth2User findOne(Long userId);

    List<Oauth2User> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    Oauth2User findByUsername(String username);

}
