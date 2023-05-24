package com.xatu.system.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.SchoolEnum;
import com.xatu.common.enums.TitleEnum;
import com.xatu.system.domain.Student;
import com.xatu.system.domain.Teacher;
import com.xatu.system.domain.vo.TeacherVo;
import com.xatu.system.mapper.TeacherMapper;
import com.xatu.system.service.TeacherService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private static final String qiNiuAddress = "http://cdn.wanglei99.xyz/tea/";

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public PageResult<Teacher> getTeacherList(TeacherVo teacherVo) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        IPage page = new Page(teacherVo.getCurrent(),teacherVo.getSize());
        if(teacherVo.getId() != 0) wrapper.eq(Teacher::getId, teacherVo.getId());
        if(teacherVo.getName() != null && !teacherVo.getName().equalsIgnoreCase("")) wrapper.like(Teacher::getName, teacherVo.getName());
        if(teacherVo.getNumber() != null && !teacherVo.getNumber().equalsIgnoreCase("")) wrapper.like(Teacher::getNumber, teacherVo.getNumber());
        if(teacherVo.getSchool() != 0) wrapper.eq(Teacher::getSchool, teacherVo.getSchool());
        if(teacherVo.getTitle() != 0) wrapper.like(Teacher::getTitle, teacherVo.getTitle());
        if(teacherVo.getEntryTime() != null) {
            long time = teacherVo.getEntryTime().getTime() + 1000*60*60*24;
            Date date = new Date();
            date.setTime(time);
            String enrollmentTime = sdf.format(date);
            wrapper.like(Teacher::getEntryTime, enrollmentTime);
        }
        if(teacherVo.getSex() != null && !teacherVo.getSex().equalsIgnoreCase(""))
            wrapper.eq(Teacher::getSex, teacherVo.getSex());
        if(teacherVo.getBirth() != null) {
            long time = teacherVo.getBirth().getTime() + 1000*60*60*24;
            Date date = new Date();
            date.setTime(time);
            String birth = sdf.format(date);
            wrapper.eq(Teacher::getBirth, birth);
        }
        if(teacherVo.getNation() != null && !teacherVo.getNation().equalsIgnoreCase("")) wrapper.eq(Teacher::getNation, teacherVo.getNation());
        teacherMapper.selectPage(page,wrapper);
        return PageResult.success(page);
    }

    public Teacher coverTeacher(Teacher teacherExist, Teacher newTeacher){
        teacherExist.setName(newTeacher.getName());
        teacherExist.setPassword(newTeacher.getPassword());
        teacherExist.setSchool(newTeacher.getSchool());
        teacherExist.setTitle(newTeacher.getTitle());
        teacherExist.setSex(newTeacher.getSex());
        teacherExist.setNation(newTeacher.getNation());
        teacherExist.setEntryTime(newTeacher.getEntryTime());
        teacherExist.setBirth(newTeacher.getBirth());
        teacherExist.setPhotoUrl(newTeacher.getPhotoUrl());
        teacherExist.setUpdateTime(new Date());
        return teacherExist;
    }

    @Override
    public Result<Boolean> update(Teacher newTeacher) {
//        if(!Regex.judgeClassNumber(newStudent.getClassNumber())){
//            return Result.error(CodeConstants.INPUT_FORMAT_ERROR,"班级输入格式错误，正确格式为：本2203/硕2203");
//        }
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getNumber, newTeacher.getNumber());
        Teacher teacherExist = teacherMapper.selectOne(wrapper);
        teacherMapper.updateById(coverTeacher(teacherExist,newTeacher));
        return Result.success();
    }

    @Override
    public Result<Boolean> add(Teacher newTeacher) throws ParseException {
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getNumber, newTeacher.getNumber());
        Teacher teacherExist = teacherMapper.selectOne(wrapper);
        if(teacherExist != null){
            return Result.error(CodeConstants.INSERT_DUPLICATE_ERROR,"该工号已存在");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        long time = newTeacher.getEntryTime().getTime() + 1000*60*60*24;
        Date entryTime = new Date();
        entryTime.setTime(time);
        String entry = sdf.format(entryTime)+"-09-01";
        newTeacher.setEntryTime(sdf1.parse(entry));

        long time1 = newTeacher.getBirth().getTime() + 1000*60*60*24;
        Date birth = new Date();
        birth.setTime(time1);
        newTeacher.setBirth(birth);

        newTeacher.setPassword(newTeacher.getNumber());
        newTeacher.setCreateTime(new Date());
        newTeacher.setUpdateTime(new Date());
        teacherMapper.insert(newTeacher);
        return Result.success();
    }

    @Override
    public Result<Boolean> delete(int id) {
        teacherMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<Boolean> batchDelete(String sid) {
        String[] ids = sid.split(",");
        for(String id:ids){
            teacherMapper.deleteById(Integer.parseInt(id));
        }
        return Result.success();
    }

    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notnull = false;
        List<Teacher> teacherList = new ArrayList<>();
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
        Teacher teacher = null;
        for(int r=1;r<=sheet.getLastRowNum();r++){ //r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到行对象
            if(row == null){
                continue;
            }
            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
            teacher = new Teacher();
            row.getCell(0).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String name =row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值
            System.out.println(name);
            if(name == null || name.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，教师姓名未填写)");
            }
            row.getCell(1).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String number =row.getCell(1).getStringCellValue();
            if(number == null || number.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，教师工号未填写)");
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
            String titleValue =row.getCell(4).getStringCellValue();
            if(titleValue == null || titleValue.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，职称未填写)");
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

            int school = SchoolEnum.getByDesc(schoolValue);
            int title = TitleEnum.getByDesc(titleValue);
            //完整的循环一次 就组成了一个对象
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
            String entryTime = sdf.format(new Date())+"-09";
            teacher.setName(name);
            teacher.setNumber(number);
            teacher.setPassword(password);
            teacher.setSchool(school);
            teacher.setTitle(title);
            teacher.setSex(sex);
            teacher.setNation(nation);
            teacher.setEntryTime(sdf1.parse(entryTime));
            teacher.setBirth(sdf2.parse(birth));
            teacher.setPhotoUrl(qiNiuAddress+school+"/"+number+".jpg");
            teacher.setCreateTime(new Date());
            teacher.setUpdateTime(new Date());
            teacherList.add(teacher);
        }
        for(Teacher newTeacher : teacherList){
            LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Teacher::getNumber, newTeacher.getNumber());
            Teacher teacherExist = teacherMapper.selectOne(wrapper);
            if(teacherExist == null){
                teacherMapper.insert(newTeacher);
                System.out.println("==>插入："+newTeacher);
            }else{
                teacherMapper.updateById(coverTeacher(teacherExist,newTeacher));
                System.out.println("==>更新："+teacherExist);
            }
        }
        return notnull;
    }
}
