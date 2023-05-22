package com.xatu.user.service;

import com.xatu.user.domain.Teacher;

/**
 * @author Wang Lei
 */
public interface TeacherService {
    /**
     * 登录功能
     * @param number 老师工号
     * @param password 密码
     * @return 返回该老师对象
     */
    Teacher login (String number, String password);

    /**
     * 更改老师照片
     * @param id 老师id
     * @param photoUrl 该老师的照片url
     * @return 返回该老师实体对象
     */
    Teacher changePhoto(int id, String photoUrl);
}