package com.xatu.system.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.system.domain.Student;
import com.xatu.system.domain.vo.StudentVo;
import com.xatu.system.mapper.StudentMapper;
import com.xatu.system.service.StudentService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Wang Lei
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> getStudentList(StudentVo studentVo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        IPage page = new Page(studentVo.getCurrent(),studentVo.getSize());
        if(studentVo.getId() != 0) wrapper.eq(Student::getId, studentVo.getId());
        if(studentVo.getName() != null && !studentVo.getName().equalsIgnoreCase("")) wrapper.like(Student::getName, studentVo.getName());
        if(studentVo.getNumber() != null && !studentVo.getNumber().equalsIgnoreCase("")) wrapper.like(Student::getNumber, studentVo.getNumber());
        if(studentVo.getSchool() != 0) wrapper.eq(Student::getSchool, studentVo.getSchool());
        if(studentVo.getClassNumber() != null && !studentVo.getClassNumber().equalsIgnoreCase("")) wrapper.like(Student::getClassNumber, studentVo.getClassNumber());
        if(studentVo.getEnrollmentTime() != null) {
            long time = studentVo.getEnrollmentTime().getTime() + 1000*60*60*24;
            Date date = new Date();
            date.setTime(time);
            String enrollmentTime = sdf.format(date);
            wrapper.like(Student::getEnrollmentTime, enrollmentTime);
        }
        if(studentVo.getSex() != null && !studentVo.getSex().equalsIgnoreCase("")) wrapper.eq(Student::getSex, studentVo.getSex());
        if(studentVo.getBirth() != null) {
            long time = studentVo.getBirth().getTime() + 1000*60*60*24;
            Date date = new Date();
            date.setTime(time);
            String birth = sdf.format(date);
            wrapper.eq(Student::getBirth, birth);
        }
        if(studentVo.getNation() != null && !studentVo.getNation().equalsIgnoreCase("")) wrapper.eq(Student::getNation, studentVo.getNation());
        if(studentVo.getHousehold() != null && !studentVo.getHousehold().equalsIgnoreCase("")) wrapper.eq(Student::getHousehold, studentVo.getHousehold());
        studentMapper.selectPage(page,wrapper);
        return PageResult.success(page);
    }

    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notnull = false;
        List<Student> studentList = new ArrayList<>();
        if(!fileName.matches("^.+\\.(?i)(xlsx)$")){ //
            throw new Exception("上传文件格式不正确");
        }
        boolean isExcel2007 = true;
        System.out.println(fileName);
        if(fileName.matches("^.+\\.(?i)(xls)$")){
            isExcel2007 = false;
        }
        InputStream is = file.getInputStream();
        Workbook wb = null;
        if (isExcel2007) {
            wb = new XSSFWorkbook(is);
        }else{
            wb = new HSSFWorkbook(is);
        }
        Sheet sheet = wb.getSheetAt(0);
        if(sheet != null){
            notnull = true;
        }
        Student student = null;
        for(int r=1;r<=sheet.getLastRowNum();r++){ //r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到行对象
            if(row == null){
                continue;
            }
            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
            student = new Student();
            row.getCell(0).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String name =row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值
            if(name == null || name.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，学生姓名未填写)");
            }
            row.getCell(1).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String number =row.getCell(1).getStringCellValue();
            if(number == null || number.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，学生学号未填写)");
            }
            row.getCell(2).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String password =row.getCell(2).getStringCellValue();
            if(password == null || password.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，密码未填写)");
            }
            row.getCell(3).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String schoolValue =row.getCell(3).getStringCellValue();
            if(schoolValue == null || schoolValue.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，学院未填写)");
            }
            row.getCell(4).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String classNumber =row.getCell(4).getStringCellValue();
            if(classNumber == null || classNumber.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，班级未填写)");
            }
            row.getCell(5).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String sex =row.getCell(5).getStringCellValue();
            if(sex == null || sex.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，性别未填写)");
            }
            row.getCell(6).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String birth =row.getCell(6).getStringCellValue();
            if(birth == null || birth.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，出生日期未填写)");
            }
            row.getCell(7).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String nation =row.getCell(7).getStringCellValue();
            if(nation == null || nation.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，民族未填写)");
            }
            row.getCell(8).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String household =row.getCell(8).getStringCellValue();
            if(household == null || household.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，籍贯未填写)");
            }
            row.getCell(9).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String photoUrl =row.getCell(9).getStringCellValue();
            if(photoUrl == null || photoUrl.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，证件照路径未填写)");
            }
            int school = SchoolEnum.getByDesc(schoolValue);
            //完整的循环一次 就组成了一个对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            String enrollmentTime = sdf.format(new Date())+"-09";
            student.setName(name);
            student.setNumber(number);
            student.setPassword(password);
            student.setSchool(school);
            student.setClassNumber(classNumber);
            student.setSex(sex);
            student.setNation(nation);
            student.setHousehold(household);
            student.setEnrollmentTime(sdf1.parse(enrollmentTime));
            student.setBirth(sdf2.parse(birth));
            student.setPhotoUrl(photoUrl);
            student.setCreateTime(new Date());
            student.setUpdateTime(new Date());
            studentList.add(student);
        }
        for(Student newStudent : studentList){
            LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Student::getNumber, newStudent.getNumber());
            Student studentExist = studentMapper.selectOne(wrapper);
            if(studentExist == null){
                studentMapper.insert(newStudent);
                System.out.println("==>插入："+newStudent);
            }else{
                studentMapper.updateById(coverStudent(studentExist,newStudent));
                System.out.println("==>更新："+studentExist);
            }
        }
        return notnull;
    }

    public Student coverStudent(Student studentExist,Student newStudent){
        studentExist.setName(newStudent.getName());
        studentExist.setPassword(newStudent.getPassword());
        studentExist.setSchool(newStudent.getSchool());
        studentExist.setClassNumber(newStudent.getClassNumber());
        studentExist.setSex(newStudent.getSex());
        studentExist.setNation(newStudent.getNation());
        studentExist.setHousehold(newStudent.getHousehold());
        studentExist.setEnrollmentTime(newStudent.getEnrollmentTime());
        studentExist.setBirth(newStudent.getBirth());
        studentExist.setPhotoUrl(newStudent.getPhotoUrl());
        studentExist.setUpdateTime(new Date());
        return studentExist;
    }

    @Override
    public Result<Boolean> update(Student newStudent) {
//        if(!Regex.judgeClassNumber(newStudent.getClassNumber())){
//            return Result.error(CodeConstants.INPUT_FORMAT_ERROR,"班级输入格式错误，正确格式为：本2203/硕2203");
//        }
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getNumber, newStudent.getNumber());
        Student studentExist = studentMapper.selectOne(wrapper);
        studentMapper.updateById(coverStudent(studentExist,newStudent));
        return Result.success();
    }

    @Override
    public Result<Boolean> add(Student newStudent) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getNumber, newStudent.getNumber());
        Student studentExist = studentMapper.selectOne(wrapper);
        if(studentExist != null){
            return Result.error(CodeConstants.INSERT_DUPLICATE_ERROR,"该学号已存在");
        }

        long time = newStudent.getEnrollmentTime().getTime() + 1000*60*60*24;
        Date enrollmentTime = new Date();
        enrollmentTime.setTime(time);
        newStudent.setEnrollmentTime(enrollmentTime);

        long time1 = newStudent.getBirth().getTime() + 1000*60*60*24;
        Date birth = new Date();
        birth.setTime(time1);
        newStudent.setBirth(birth);

        newStudent.setPassword(newStudent.getNumber());
        newStudent.setCreateTime(new Date());
        newStudent.setUpdateTime(new Date());
        studentMapper.insert(newStudent);
        return Result.success();
    }

    @Override
    public Result<Boolean> delete(int id) {
        studentMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<Boolean> batchDelete(String sid) {
        String[] ids = sid.split(",");
        for(String id:ids){
            studentMapper.deleteById(Integer.parseInt(id));
        }
        return Result.success();
    }
}
