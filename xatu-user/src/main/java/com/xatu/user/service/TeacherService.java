package com.xatu.user.service;

import com.xatu.user.domain.Student;
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

    /**
     * 修改密码
     * @param id 教师id
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return boolean
     */
    boolean changePassword(int id, String oldPwd, String newPwd);

    /**
     * 更新教师信息
     * @param teacher 教师
     * @return 更新后的教师信息
     */
    Teacher updateInfo(Teacher teacher);
}
