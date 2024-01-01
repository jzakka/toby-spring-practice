package com.example.service;

import com.example.dao.UserDao;
import com.example.domain.Level;
import com.example.domain.User;

import static com.example.service.UserServiceImpl.MIN_LOGCOUNT_FOR_SILVER;
import static com.example.service.UserServiceImpl.MIN_RECOMMEND_FOR_GOLD;

public class NormalUserLevelUpgradePolicy implements UserLevelUpgradePolicy {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        return switch (currentLevel) {
            case BASIC -> user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER;
            case SILVER -> user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD;
            case GOLD -> false;
            default -> throw new IllegalArgumentException("Unknown Level: " + currentLevel);
            // 새로운 레벨이 추가됐지만 로직이 추가되지 않으면 예외가 발생한다.
        };
    }

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
