package com.xatu.homework;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xatu.homework.mapper.HomeworkMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class XatuHomeworkApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Test
//    Void testGetByPage(){
//        HomeworkMapper homeworkMapper = null;
//        IPage page = new Page(1,5);
//        homeworkMapper.selectPage(page,null);
//        System.out.println(page.getCurrent());
//    }


}
