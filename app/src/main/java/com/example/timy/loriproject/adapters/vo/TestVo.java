package com.example.timy.loriproject.adapters.vo;

import java.util.ArrayList;
import java.util.List;

public class TestVo {
    private String name;

    public TestVo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestVo{" +
                "name='" + name + '\'' +
                '}';
    }

    public List<TestVo> getTestVo(){
        List<TestVo> testVos=new ArrayList<>();
        testVos.add(new TestVo("test1"));
        testVos.add(new TestVo("test2"));
        testVos.add(new TestVo("test3"));
        testVos.add(new TestVo("test4"));
        testVos.add(new TestVo("test5"));
        testVos.add(new TestVo("test6"));
        testVos.add(new TestVo("test7"));
        testVos.add(new TestVo("test8"));
        testVos.add(new TestVo("test9"));
        testVos.add(new TestVo("test0"));
        testVos.add(new TestVo("test10"));
        testVos.add(new TestVo("test11"));

        return testVos;
    }
}

