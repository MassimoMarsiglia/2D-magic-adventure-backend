package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/testing")
    public List<TestEntry> greeting(){
        List<TestEntry> test = new ArrayList<>();
        test.add(new TestEntry("test1", 1));
        test.add(new TestEntry("test2", 2));
        test.add(new TestEntry("test3", 3));
        test.add(new TestEntry("test4", 4));
        test.add(new TestEntry("test5", 5));
        return test;
    }
}
