package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@RestController
public class HelloController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Collection<String> hello(@RequestParam(name = "name", required = false) String name) {
        return Collections.singleton("Hello " + name);
    }
}
