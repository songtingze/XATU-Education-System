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

    /**
     * 修改密码
     * @param id 学生id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return boolean
     */
    boolean changePassword(int id, String oldPwd, String newPwd);

    /**
     * 更新学生个人信息
     * @param student 学生
     * @return 更新后的学生信息
     */
    Student updateInfo(Student student);
}
