package com.xiexin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 该  控制类  是为了  查找 jsp 或者  带参数访问jsp  或者  跳转的
 */
@Controller
@RequestMapping("/pages")
public class PagesController {
    @RequestMapping("/reg")
    public String reg(){
        System.out.println("请求注册进入hello。。。了");
        return "reg";  //故意写的 强调  必须和XXX rello
    }

    @RequestMapping("/regForm")
    public String regForm(){
        return "regForm";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @RequestMapping("/ajaxCommit")
    public String ajaxCommit(){
        return "ajaxCommit";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }
}
