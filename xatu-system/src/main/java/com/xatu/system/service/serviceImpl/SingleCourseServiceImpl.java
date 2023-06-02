package com.xatu.system.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.*;
import com.xatu.system.domain.Course;
import com.xatu.system.domain.SingleCourse;
import com.xatu.system.domain.Sys;
import com.xatu.system.domain.Teacher;
import com.xatu.system.domain.vo.SingleCourseVo;
import com.xatu.system.mapper.CourseMapper;
import com.xatu.system.mapper.SingleCourseMapper;
import com.xatu.system.mapper.TeacherMapper;
import com.xatu.system.service.SingleCourseService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SingleCourseServiceImpl implements SingleCourseService {
    @Resource
    private SingleCourseMapper singleCourseMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public PageResult<SingleCourse> getSingleCourseList(SingleCourseVo singleCourseVo) {
        LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
        IPage page = new Page(singleCourseVo.getCurrent(),singleCourseVo.getSize());

        if(singleCourseVo.getCourseNum() != null && !singleCourseVo.getCourseNum().equalsIgnoreCase("")) wrapper.like(SingleCourse::getCourseNum, singleCourseVo.getCourseNum());
        if(singleCourseVo.getCourseIndex() != 0) wrapper.eq(SingleCourse::getCourseIndex, singleCourseVo.getCourseIndex());
        if(singleCourseVo.getTeacher() != null && !singleCourseVo.getTeacher().equalsIgnoreCase("")) wrapper.like(SingleCourse::getTeacher, singleCourseVo.getTeacher());
        if(singleCourseVo.getSchool() != 0) {
            // TODO:后续实现多表连接
//            LambdaQueryWrapper<Course> wrapper1 = new LambdaQueryWrapper<>();
//            wrapper1.eq(Course::getSchool, singleCourseVo.getSchool());
//            courseMapper.selectList(wrapper1);
        }
        if(singleCourseVo.getCourseName() != null && !singleCourseVo.getCourseName().equalsIgnoreCase("")){
            // TODO:后续实现多表连接
        }
        if(singleCourseVo.getDayTime() != 0) wrapper.eq(SingleCourse::getDayTime, singleCourseVo.getDayTime());
        if(singleCourseVo.getHourPeriod() != 0) wrapper.eq(SingleCourse::getHourPeriod, singleCourseVo.getHourPeriod());
        if(singleCourseVo.getLocation() != null && !singleCourseVo.getLocation().equalsIgnoreCase(""))wrapper.like(SingleCourse::getLocation, singleCourseVo.getLocation());
        if(singleCourseVo.getStatusValue() != null && !singleCourseVo.getStatusValue().equalsIgnoreCase("")) {
            singleCourseVo.setStatus(CourseStateEnum.getByDesc(singleCourseVo.getStatusValue()));
            wrapper.eq(SingleCourse::getStatus, singleCourseVo.getStatus());
        }
        singleCourseMapper.selectPage(page,wrapper);
        return PageResult.success(page);
    }

    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notnull = false;
        List<SingleCourse> singleCourseList = new ArrayList<>();
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
        SingleCourse singleCourse = null;
        for(int r=1;r<=sheet.getLastRowNum();r++){ //r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到行对象
            if(row == null){
                continue;
            }
            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
            singleCourse = new SingleCourse();
            row.getCell(0).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String courseNum =row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值
            if(courseNum == null || courseNum.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，课程编号未填写)");
            }
            row.getCell(1).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String teacher =row.getCell(1).getStringCellValue();//得到每一行第一个单元格的值
            if(teacher == null || teacher.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，教师工号未填写)");
            }
            row.getCell(2).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String dayValue =row.getCell(2).getStringCellValue();//得到每一行第一个单元格的值
            if(dayValue == null || dayValue.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，上课时间未填写)");
            }
            row.getCell(3).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String hourPeriodValue =row.getCell(3).getStringCellValue();
            if(hourPeriodValue == null || hourPeriodValue.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，上课时段未填写)");
            }
            row.getCell(4).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String location =row.getCell(4).getStringCellValue();
            if(location == null || location.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，上课地点未填写)");
            }
            row.getCell(5).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String capacity =row.getCell(5).getStringCellValue();
            if(capacity == null || capacity.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，课容量未填写)");
            }
            row.getCell(6).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String info =row.getCell(6).getStringCellValue();
            if(info == null || info.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，选课说明未填写)");
            }
//            row.getCell(9).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
//            String photoUrl =row.getCell(9).getStringCellValue();
//            if(photoUrl == null || photoUrl.isEmpty()){
//                throw new Exception("导入失败(第\"+(r+1)+\"行，证件照路径未填写)");
//            }
            int dayTime = WeekEnum.getByDesc(dayValue);
            int hourPeriod = HourPeriodEnum.getByDesc(hourPeriodValue);
            if(info==null || info.equalsIgnoreCase("")) info="略";
            //完整的循环一次 就组成了一个对象
            singleCourse.setCourseNum(courseNum);
            singleCourse.setTeacher(teacher);
            singleCourse.setDayTime(dayTime);
            singleCourse.setHourPeriod(hourPeriod);
            singleCourse.setLocation(location);
            singleCourse.setCapacity(Integer.parseInt(capacity));
            singleCourse.setRemain(0);
            singleCourse.setInfo(info);
            singleCourse.setStatus(0);
            singleCourse.setCreateTime(new Date());
            singleCourse.setUpdateTime(new Date());
            singleCourseList.add(singleCourse);
        }
        for(SingleCourse newSingleCourse : singleCourseList){
            LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SingleCourse::getCourseNum, newSingleCourse.getCourseNum());
            wrapper.eq(SingleCourse::getTeacher,newSingleCourse.getTeacher());
            SingleCourse singleCourseExist = singleCourseMapper.selectOne(wrapper);
            if(singleCourseExist == null){
                newSingleCourse.setCourseIndex(setSingleCourseIndex(newSingleCourse));
                singleCourseMapper.insert(newSingleCourse);
                System.out.println("==>插入："+newSingleCourse);
            }else{
                singleCourseMapper.updateById(coverSingleCourse(singleCourseExist,newSingleCourse));
                System.out.println("==>更新："+singleCourseExist);
            }
        }
        return notnull;
    }

    public boolean judgeTimeSpace(SingleCourse newSingleCourse){
        LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SingleCourse::getDayTime,newSingleCourse.getDayTime());
        wrapper.eq(SingleCourse::getHourPeriod,newSingleCourse.getHourPeriod());
        wrapper.eq(SingleCourse::getLocation,newSingleCourse.getLocation());
        List<SingleCourse> singleCourseList = singleCourseMapper.selectList(wrapper);
        if(singleCourseList.size() == 0){
            return true;
        }else{
            LambdaQueryWrapper<Course> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(Course::getNumber,newSingleCourse.getCourseNum());
            Course newCourse = courseMapper.selectOne(wrapper1);
            for(SingleCourse singleCourse:singleCourseList){
                wrapper1 = new LambdaQueryWrapper<>();
                wrapper1.eq(Course::getNumber,singleCourse.getCourseNum());
                Course course = courseMapper.selectOne(wrapper1);
                if(!newCourse.getNumber().equalsIgnoreCase(course.getNumber())){
                    if(newCourse.getPeriod() == course.getPeriod() || newCourse.getPeriod() == 3 || course.getPeriod() == 3){
                        return false;
                    }
                }

            }
            return true;
        }

    }

    @Override
    public Result<Boolean> update(SingleCourse newSingleCourse) {
        if(judgeTimeSpace(newSingleCourse)){
            LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SingleCourse::getCourseIndex, newSingleCourse.getCourseIndex());
            wrapper.eq(SingleCourse::getCourseNum, newSingleCourse.getCourseNum());
            SingleCourse singleCourseExist = singleCourseMapper.selectOne(wrapper);
            if(newSingleCourse.getInfo()==null || newSingleCourse.getInfo().equalsIgnoreCase("")) newSingleCourse.setInfo("略");
            singleCourseMapper.updateById(coverSingleCourse(singleCourseExist,newSingleCourse));
            return Result.success();
        }else {
            return Result.error(CodeConstants.INSERT_DUPLICATE_ERROR,"该时间地点已有其他课堂");
        }

    }

    @Override
    public Result<Boolean> add(SingleCourse newSingleCourse) throws ParseException {
        if(judgeTimeSpace(newSingleCourse)){
            LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SingleCourse::getTeacher, newSingleCourse.getTeacher());
            wrapper.eq(SingleCourse::getCourseNum, newSingleCourse.getCourseNum());
            SingleCourse singleCourseExist = singleCourseMapper.selectOne(wrapper);
            if(singleCourseExist != null){
                return Result.error(CodeConstants.INSERT_DUPLICATE_ERROR,"该课程已存在");
            }
            if(newSingleCourse.getInfo()==null || newSingleCourse.getInfo().equalsIgnoreCase("")) newSingleCourse.setInfo("略");
            newSingleCourse.setCourseIndex(setSingleCourseIndex(newSingleCourse));
            newSingleCourse.setRemain(0);
            newSingleCourse.setStatus(0);
            newSingleCourse.setCreateTime(new Date());
            newSingleCourse.setUpdateTime(new Date());
            singleCourseMapper.insert(newSingleCourse);
            return Result.success();
        }else {
            return Result.error(CodeConstants.INSERT_DUPLICATE_ERROR,"该时间地点已有其他课堂");
        }
    }

    @Override
    public Result<Boolean> delete(int id) {
        singleCourseMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<Boolean> batchDelete(String sid) {
        String[] ids = sid.split(",");
        for(String id:ids){
            singleCourseMapper.deleteById(Integer.parseInt(id));
        }
        return Result.success();
    }

    @Override
    public Result<List<Course>> getAllCourse() {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        return Result.success(courseMapper.selectList(wrapper));
    }

    @Override
    public Result<List<Teacher>> getAllTeacherBySchool(String courseNum) {
        LambdaQueryWrapper<Course> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Course::getNumber,courseNum);
        LambdaQueryWrapper<Teacher> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teacher::getSchool,courseMapper.selectOne(wrapper1).getSchool());
        return Result.success(teacherMapper.selectList(wrapper));
    }

    public SingleCourse coverSingleCourse(SingleCourse singleCourseExist,SingleCourse newSingleCourse){
        singleCourseExist.setCourseNum(newSingleCourse.getCourseNum());
        singleCourseExist.setTeacher(newSingleCourse.getTeacher());
        singleCourseExist.setDayTime(newSingleCourse.getDayTime());
        singleCourseExist.setHourPeriod(newSingleCourse.getHourPeriod());
        singleCourseExist.setLocation(newSingleCourse.getLocation());
        singleCourseExist.setCapacity(newSingleCourse.getCapacity());
        singleCourseExist.setRemain(newSingleCourse.getRemain());
        singleCourseExist.setStatus(newSingleCourse.getStatus());
        singleCourseExist.setInfo(newSingleCourse.getInfo());
        singleCourseExist.setUpdateTime(new Date());
        return singleCourseExist;
    }

    public int setSingleCourseIndex(SingleCourse singleCourse){
        LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SingleCourse::getCourseNum, singleCourse.getCourseNum());
        return singleCourseMapper.selectList(wrapper).size()+1;
    }
}
