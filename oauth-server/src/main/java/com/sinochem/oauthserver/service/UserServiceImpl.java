package com.sinochem.oauthserver.service;

import com.sinochem.oauthserver.entity.Oauth2User;
import com.sinochem.oauthserver.mapper.Oauth2UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-17
 * <p>Version: 1.0
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Oauth2UserMapper userDao;
    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 创建用户
     * @param user
     */
    public Oauth2User createUser(Oauth2User user) {
        //加密密码
        passwordHelper.encryptPassword(user);
        userDao.insert(user);
        return user;
    }

    @Override
    public Oauth2User updateUser(Oauth2User user) {
        userDao.updateByPrimaryKey(user);
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        userDao.deleteByPrimaryKey(userId);
    }

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    public void changePassword(Long userId, String newPassword) {
        Oauth2User user =userDao.selectByPrimaryKey(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userDao.updateByPrimaryKey(user);
    }

    @Override
    public Oauth2User findOne(Long userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    public List<Oauth2User> findAll() {
        return userDao.selectAll();
    }

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public Oauth2User findByUsername(String username) {
        Oauth2User oauth2User = new Oauth2User();
        oauth2User.setUsername(username);
        return userDao.selectOne(oauth2User);
    }


}
