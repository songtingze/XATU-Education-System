package com.xatu.system.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.common.constant.CodeConstants;
import com.xatu.common.domain.EnumResult;
import com.xatu.common.domain.PageResult;
import com.xatu.common.domain.Result;
import com.xatu.common.enums.*;
import com.xatu.system.domain.Course;
import com.xatu.system.domain.SingleCourse;
import com.xatu.system.domain.vo.CourseVo;
import com.xatu.system.domain.vo.SelectionValue;
import com.xatu.system.mapper.CourseMapper;
import com.xatu.system.mapper.SingleCourseMapper;
import com.xatu.system.service.CourseService;
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
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private SingleCourseMapper singleCourseMapper;

    @Override
    public PageResult<Course> getCourseList(CourseVo courseVo) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        IPage page = new Page(courseVo.getCurrent(),courseVo.getSize());
        if(courseVo.getNumber() != null && !courseVo.getNumber().equalsIgnoreCase("")) wrapper.like(Course::getNumber, courseVo.getNumber());
        if(courseVo.getName() != null && !courseVo.getName().equalsIgnoreCase("")) wrapper.like(Course::getName, courseVo.getName());
        if(courseVo.getNameEn() != null && !courseVo.getNameEn().equalsIgnoreCase("")) wrapper.like(Course::getNameEn, courseVo.getNameEn());
        if(courseVo.getSchool() != 0) wrapper.eq(Course::getSchool, courseVo.getSchool());
        if(courseVo.getCreditValue() != null && !courseVo.getCreditValue().equalsIgnoreCase("")) wrapper.eq(Course::getCredit, Integer.parseInt(courseVo.getCreditValue()));
        if(courseVo.getCreditHourValue() != null && !courseVo.getCreditHourValue().equalsIgnoreCase("")) wrapper.eq(Course::getCreditHour, Integer.parseInt(courseVo.getCreditHourValue()));
        if(courseVo.getGradeValue() != null && !courseVo.getGradeValue().equalsIgnoreCase("")) {
            courseVo.setGrade(GradeEnum.getByDesc(courseVo.getGradeValue()));
            wrapper.eq(Course::getGrade, courseVo.getGrade());
        }
        if(courseVo.getPeriodValue() != null && !courseVo.getPeriodValue().equalsIgnoreCase("")) {
            courseVo.setPeriod(PeriodEnum.getByDesc(courseVo.getPeriodValue()));
            wrapper.eq(Course::getPeriod, courseVo.getPeriod());
        }
        if(courseVo.getIsOnlyMajorValue() != null && !courseVo.getIsOnlyMajorValue().equalsIgnoreCase("")) {
            courseVo.setIsOnlyMajor(courseVo.getIsOnlyMajorValue().equalsIgnoreCase("全校任选")?0:1);
            wrapper.eq(Course::getIsOnlyMajor, courseVo.getIsOnlyMajor());
        }
        if(courseVo.getAssessmentValue() != null && !courseVo.getAssessmentValue().equalsIgnoreCase("")) {
            courseVo.setAssessment(CourseAssessmentEnum.getByDesc(courseVo.getAssessmentValue()));
            wrapper.eq(Course::getAssessment, courseVo.getAssessment());
        }
        if(courseVo.getStatusValue() != null && !courseVo.getStatusValue().equalsIgnoreCase("")) {
            courseVo.setStatus(CourseStateEnum.getByDesc(courseVo.getStatusValue()));
            wrapper.eq(Course::getStatus, courseVo.getStatus());
        }

        courseMapper.selectPage(page,wrapper);
        return PageResult.success(page);
    }

    @Override
    public boolean batchImport(String fileName, MultipartFile file) throws Exception {
        boolean notnull = false;
        List<Course> courseList = new ArrayList<>();
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
        Course course = null;
        for(int r=1;r<=sheet.getLastRowNum();r++){ //r = 2 表示从第三行开始循环 如果你的第三行开始是数据
            Row row = sheet.getRow(r);//通过sheet表单对象得到行对象
            if(row == null){
                continue;
            }
            //sheet.getLastRowNum() 的值是 10，所以Excel表中的数据至少是10条；不然报错 NullPointerException
            course = new Course();
            row.getCell(0).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String number =row.getCell(0).getStringCellValue();//得到每一行第一个单元格的值
            if(number == null || number.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，课程编号未填写)");
            }
            row.getCell(1).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String name =row.getCell(1).getStringCellValue();//得到每一行第一个单元格的值
            if(name == null || name.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，课程姓名未填写)");
            }
            row.getCell(2).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String nameEn =row.getCell(2).getStringCellValue();//得到每一行第一个单元格的值
            if(nameEn == null || nameEn.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，课程英文名称未填写)");
            }
            row.getCell(3).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String schoolValue =row.getCell(3).getStringCellValue();
            if(schoolValue == null || schoolValue.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，开课学院未填写)");
            }
            row.getCell(4).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String credit =row.getCell(4).getStringCellValue();
            if(credit == null || credit.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，学分未填写)");
            }
            row.getCell(5).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String creditHour =row.getCell(5).getStringCellValue();
            if(creditHour == null || creditHour.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，学时未填写)");
            }
            row.getCell(6).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String gradeValue =row.getCell(6).getStringCellValue();
            if(gradeValue == null || gradeValue.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，开课年级未填写)");
            }
            row.getCell(7).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String periodValue =row.getCell(7).getStringCellValue();
            if(periodValue == null || periodValue.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，开课时段未填写)");
            }
            row.getCell(8).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String isOnlyMajorValue =row.getCell(8).getStringCellValue();
            if(isOnlyMajorValue == null || isOnlyMajorValue.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，限选说明未填写)");
            }
            row.getCell(9).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String assessmentValue =row.getCell(9).getStringCellValue();
            if(assessmentValue == null || assessmentValue.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，考核方式未填写)");
            }
            row.getCell(10).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
            String info =row.getCell(10).getStringCellValue();
            if(info == null || info.isEmpty()){
                throw new Exception("导入失败(第\"+(r+1)+\"行，课程介绍未填写)");
            }
//            row.getCell(9).setCellType(CellType.STRING);//将每一行第一个单元格设置为字符串类型
//            String photoUrl =row.getCell(9).getStringCellValue();
//            if(photoUrl == null || photoUrl.isEmpty()){
//                throw new Exception("导入失败(第\"+(r+1)+\"行，证件照路径未填写)");
//            }
            int school = SchoolEnum.getByDesc(schoolValue);
            int grade = GradeEnum.getByDesc(gradeValue);
            int period = PeriodEnum.getByDesc(periodValue);
            int assessment = CourseAssessmentEnum.getByDesc(assessmentValue);
            int isOnlyMajor = isOnlyMajorValue.equalsIgnoreCase("全校任选")?0:1;
            if(info==null || info.equalsIgnoreCase("")) info="略";
            //完整的循环一次 就组成了一个对象
            course.setNumber(number);
            course.setName(name);
            course.setNameEn(nameEn);
            course.setSchool(school);
            course.setCredit(Integer.parseInt(credit));
            course.setCreditHour(Integer.parseInt(creditHour));
            course.setGrade(grade);
            course.setPeriod(period);
            course.setIsOnlyMajor(isOnlyMajor);
            course.setAssessment(assessment);
            course.setInfo(info);
            course.setStatus(0);
            course.setCreateTime(new Date());
            course.setUpdateTime(new Date());
            courseList.add(course);
        }
        for(Course newCourse : courseList){
            LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Course::getNumber, newCourse.getNumber());
            Course courseExist = courseMapper.selectOne(wrapper);
            if(courseExist == null){
                courseMapper.insert(newCourse);
                System.out.println("==>插入："+newCourse);
            }else{
                courseMapper.updateById(coverCourse(courseExist,newCourse));
                System.out.println("==>更新："+courseExist);
            }
        }
        return notnull;
    }

    @Override
    public SelectionValue getSelections() {
        List<EnumResult> list = new ArrayList<>();
        list.add(new EnumResult(0,"全校任选"));
        list.add(new EnumResult(1,"仅限本学院选"));

        SelectionValue selectionValue = new SelectionValue();
        selectionValue.setSchools(SchoolEnum.getAllSchools());
        selectionValue.setGrades(GradeEnum.getAllGrades());
        selectionValue.setPeriods(PeriodEnum.getAllPeriods());
        selectionValue.setIsOnlyMajors(list);
        selectionValue.setAssessments(CourseAssessmentEnum.getAllAssessments());
        selectionValue.setStates(CourseStateEnum.getAllCourseStatus());
        selectionValue.setWeeks(WeekEnum.getAllWeeks());
        selectionValue.setHourPeriods(HourPeriodEnum.getAllHourPeriods());

        return selectionValue;
    }

    public Course coverCourse(Course courseExist,Course newCourse){
        courseExist.setName(newCourse.getName());
        courseExist.setNameEn(newCourse.getNameEn());
        courseExist.setSchool(newCourse.getSchool());
        courseExist.setCredit(newCourse.getCredit());
        courseExist.setCreditHour(newCourse.getCreditHour());
        courseExist.setGrade(newCourse.getGrade());
        courseExist.setPeriod(newCourse.getPeriod());
        courseExist.setIsOnlyMajor(newCourse.getIsOnlyMajor());
        courseExist.setAssessment(newCourse.getAssessment());
        courseExist.setInfo(newCourse.getInfo());
        courseExist.setUpdateTime(new Date());

        if(courseExist.getStatus() != newCourse.getStatus()){
            courseExist.setStatus(newCourse.getStatus());
            LambdaQueryWrapper<SingleCourse> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SingleCourse::getCourseNum,courseExist.getNumber());
            List<SingleCourse> singleCourseList = singleCourseMapper.selectList(wrapper);
            for(SingleCourse singleCourse:singleCourseList){
                singleCourse.setStatus(newCourse.getStatus());
                singleCourseMapper.updateById(singleCourse);
            }
        }

        return courseExist;
    }

    @Override
    public Result<Boolean> update(Course newCourse) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getNumber, newCourse.getNumber());
        Course courseExist = courseMapper.selectOne(wrapper);
        if(newCourse.getInfo()==null || newCourse.getInfo().equalsIgnoreCase("")) newCourse.setInfo("略");
        courseMapper.updateById(coverCourse(courseExist,newCourse));
        return Result.success();
    }

    @Override
    public Result<Boolean> add(Course newCourse) throws ParseException {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getNumber, newCourse.getNumber());
        Course courseExist = courseMapper.selectOne(wrapper);
        if(courseExist != null){
            return Result.error(CodeConstants.INSERT_DUPLICATE_ERROR,"该课程已存在");
        }
        if(newCourse.getInfo()==null || newCourse.getInfo().equalsIgnoreCase("")) newCourse.setInfo("略");
        newCourse.setStatus(0);
        newCourse.setCreateTime(new Date());
        newCourse.setUpdateTime(new Date());
        courseMapper.insert(newCourse);
        return Result.success();
    }

    @Override
    public Result<Boolean> delete(int id) {
        courseMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<Boolean> batchDelete(String sid) {
        String[] ids = sid.split(",");
        for(String id:ids){
            courseMapper.deleteById(Integer.parseInt(id));
        }
        return Result.success();
    }

    @Override
    public Course getCourseByNumber(String number) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getNumber, number);
        return courseMapper.selectOne(wrapper);
    }
}
