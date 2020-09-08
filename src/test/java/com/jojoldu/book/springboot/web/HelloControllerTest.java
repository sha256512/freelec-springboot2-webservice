package com.jojoldu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class) // JUnit에 내장된 실행자 외에 다른 실행자를 실행시킴
//스프링부트와 Junit 사이에 연결자 역할
@WebMvcTest(controllers = HelloController.class) // Web에 집중할 수 있는 어노테이션
public class HelloControllerTest {
    @Autowired // 스프링이 관리하는 빈을 주입받습니다
    private MockMvc mvc; // 웹 API를 테스트할 때 사용, 스프링 MVC 테스트의 시작점 http, get, post 등에 대한 api test

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";
        // MockMvc를 통해 /hello 주소로 http get 요청
        // 체이닝이 지원됨
        //status().isOK --> 200, 404, 500 등 상태를 검증 ok는 200인지 아닌지 검증
        //content().string(hello) --> mvc.perform 결과를 검증
        mvc.perform(get("/hello")).andExpect(status().isOk()).andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}