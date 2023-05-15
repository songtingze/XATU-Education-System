package com.xatu.user.service;

import com.xatu.user.domain.Student;

/**
 * @author Wang Lei
 */
public interface StudentService {
    /**
     * 登录功能
     * @param number 学生学号
     * @param password 密码
     * @return 返回该学生对象
     */
    Student login (String number, String password);

    /**
     * 更改学生照片
     * @param id 学生id
     * @param photoUrl 该学生的照片url
     * @return 返回该学生实体对象
     */
    Student changePhoto(int id, String photoUrl);
}
