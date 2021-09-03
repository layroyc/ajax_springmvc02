package com.xiexin.controller;

import com.xiexin.bean.AdminInfo;
import com.xiexin.bean.Dog;
import com.xiexin.bean.Lover;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    /*注册*/
    //注册成功后，如果是单体项目------就要跳转到登录页面，这个跳转是后台的 转发，重定向，总之是
    /*
    *       后台负责 跳转，携带数据的跳转页面的
    *  如果是  新型的项目，即前后端分离的，那么 后台 只负责  返回给前端json数据，
    *  跳转是前端来处理的， 前端根据后台返回code代码，进行跳转，
    *  如果前端负责跳转，他会起一个好听的名字，叫做路由!
    *
    * */

    // 什么是前后端分离即:项目 上的分离和数据上的分离
    //项目上的分离: 前端一 个项目， 后台一 一个项目     2  项目  他们的认证是 token/jwt+redis
    //数据上的分离: 还是一 个项目， 只不过前后端用json来交互数据。
    //很少在用jstl/ el表达式来写项目。他们的认证 是  session

    //layui  在ssm  框架的使用，其实就是数据上的分离，也可以是 项目上的分离
    //那么 他就是json 交互的， 那么 后台只需要  给他 返回json数据就可以了

    //以前 在servlet中，  resp.getWriter().print(new JSONObject.toString(map)).输出json
    //现在  用mvc框架的，高级了  使用注解  @ResponseBody

    /*
    * 以前收参数   req.getParamter("adminName")
    * adminName:12345
    * adminPwd
    * adminPwdR
    * */

    //第一种收参方式：  数据类型接收参数
    @RequestMapping("/reg") //layui 版本的
    @ResponseBody   //这个注解就是  返回前端的json数据
    public Map reg(String adminName,String adminPwd,String adminPwdR,String adminTime){
        System.out.println("adminName = " + adminName);
        System.out.println("adminTime = " + adminTime);
        Map codeMap = new HashMap();
        if(!adminPwd.equals(adminPwdR)){
            codeMap.put("code","4001");
            codeMap.put("msg","你输入的重复密码与密码不一致，请重新输入");
            return codeMap;
        }
        if(adminName.length()<=0){
            codeMap.put("code","4002");
            codeMap.put("msg","adminName表单未填写完整");
            return codeMap;
        }
        if(adminPwd.length()<=0){
            codeMap.put("code","4002");
            codeMap.put("msg","adminPwd表单未填写完整");
            return codeMap;
        }
        codeMap.put("code",0);
        codeMap.put("msg","注册成功");
        return codeMap;
    }

    //传统版本   不返回json  跳转使用转发或者重定向
    @RequestMapping("/regForm")
    public String regForm(String adminName,String adminPwd){
        System.out.println("adminName = " + adminName);
        System.out.println("adminPwd = " + adminPwd);
        //注册成功跳转到登录页
        return "loginForm";
    }

    //第二种收参方式   叫做实体类收参数
    @RequestMapping("/regByBean")
    @ResponseBody
    public Map regByBean(AdminInfo adminInfo){
        System.out.println("adminInfo = " + adminInfo);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","注册成功");
        return codeMap;
    }

    //ajax 接收  数据/集合   第三种
    @RequestMapping("/ajax03")
    @ResponseBody
    public Map ajax03(@RequestParam("ids[]") List<Integer> ids){   //前端 ids[]   后台ids
                                            //当前后端  参数不一样时，那么需要使用注解来调整   @RequestParam()
        for (Integer id : ids) {
            System.out.println("id = " + id);
        }
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","请求访问成功");
        codeMap.put("data",ids);
        return codeMap;
    }

    //ajax 接收  传json对象   第四种
    @RequestMapping("/ajax04")
    @ResponseBody
    public Map ajax04(@RequestBody AdminInfo adminInfo){//@RequestBody  注解就是指的前端用json请求
        System.out.println("adminInfo = " + adminInfo);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","请求访问成功");
        codeMap.put("data",adminInfo);
        return codeMap;
    }

    //ajax05  接收前端传过来的多个参数
    @RequestMapping("/ajax05")
    @ResponseBody
    public Map ajax05(@ModelAttribute Lover lover, @ModelAttribute Dog dog){
        System.out.println("lover = " + lover);
        System.out.println("dog = " + dog);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","请求访问成功");
        codeMap.put("data1",lover);
        codeMap.put("data2",dog);
        return codeMap;
    }

    //前端传来的多个 对象  需要 根据请求的前缀进行绑定
    @InitBinder("lover")
    public void binding01(WebDataBinder webDataBinder){//WebDataBinder  网络数据绑定
        webDataBinder.setFieldDefaultPrefix("lover.");//设置前缀   lover.name
    }
    @InitBinder("dog")
    public void binding02(WebDataBinder webDataBinder){
        webDataBinder.setFieldDefaultPrefix("dog.");
    }

    //ajax06   json 收取多个对象
    @RequestMapping("/ajax06")  // type:'GET',  get请求   Query String Parameters  不安全 直接放在浏览器上
    @ResponseBody
    public Map ajax06(@RequestBody List<Lover> loverList){//@ResponseBody   他是方法体中拿取得数据的，所以不能用get请求！！！
        for (Lover lover : loverList) {
            System.out.println("lover = " + lover);
        }
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","请求访问成功");
        codeMap.put("data1",loverList);
        return codeMap;
    }

    //ajax07   用map传参+json
    @RequestMapping("/ajax07")
    @ResponseBody   //十分常用  servlet =中servlet  多表的动态参数 就是用map    能搞定一切
    public Map ajax07(@RequestBody Map map){
        System.out.println("map = " + map);
        System.out.println("map的adminName = " + map.get("adminName"));
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","请求访问成功");
        codeMap.put("data1",map);
        return codeMap;
    }

    //ajax08 传 对象+常用类型  混合，常用于 带参数的分页查询
    @RequestMapping("/ajax08")
    @ResponseBody           //required = false  是否必备，为false时可带可不带
    public Map ajax08(Lover lover,@RequestParam(value="limit" , required = false,defaultValue = "5") Integer pageSize,Integer page){
        //默认值是5条
        System.out.println("lover = " + lover);
        System.out.println("pageSize = " + pageSize);
        System.out.println("page = " + page);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","请求访问成功");
        codeMap.put("data1",lover);
        codeMap.put("data2",pageSize);
        codeMap.put("data3",page);
        return codeMap;

    }



    //以上项目 是前后端分离  最新项目用到的知识点，  那么也有  传统项目   后台负责跳到另一个界面


    //第一种   springMVC 的传统方式！！   原始方式： request + session + request 的转发
   //页面传值：即四大作用域  Request,session,applation,page
    //传统的mvc的方法（不返回json数据的，不使用 @ResponseBody注解），他要跳转jsp，跳转jsp的方式1，返回值是 String类型
    @RequestMapping("/yuansheng")   //什么叫页面传值？  登录页（admin，123456）----->yuansheng()----->(admin) home
    //public String yuansheng(AdminInfo adminInfo, HttpSession session){
    public String yuansheng(HttpSession session, HttpServletRequest request){
        System.out.println("使用原生方式  页面传值");
        //System.out.println("adminInfo = " + adminInfo);
        //登录如果验证成功，那么就需要把登录信息，存放到session作用域当中
        //session.setAttribute("adminInfo",adminInfo);
        String adminName = request.getParameter("adminName");
        String adminPwd = request.getParameter("adminPwd");
        request.setAttribute("adminName",adminName);
        request.setAttribute("adminPwd",adminPwd);

        //servlet的转发    不支持
        //request.getRequestDispatcher("home.jsp").forward(request,response);

        //return "home";    底层转发  //这个  和 PagesController 里的 查找jsp 是没有太大联系的
        //return "forward:/WEB-INF/pages/home.jsp";//springMVC的转发
        //return "forward:/pages/home";//springMVC的转发


        //重定向   servlet   response.sendredDirect("https://www.baidu.com")  重定向携带不了数据
        //return "redirect:https://www.baidu.com";    //不带/  和 带/ 的区别   绝对路径和相对路径
        return "redirect:/https://www.baidu.com";    //不带/  和 带/ 的区别   http://localhost:8080/https://www.baidu.com

    }

    //第二种   springMVC 的传值方式！！   ModelAndView
    @RequestMapping("/modelAndView")
    public ModelAndView modelAndView(AdminInfo adminInfo){
        //ModelAndView  模型和视图  通俗 数据和显示 可以充当转发的功能
        ModelAndView mv = new ModelAndView();
        mv.addObject("adminName",adminInfo.getAdminName());
        mv.addObject("adminPwd",adminInfo.getAdminPwd());
        System.out.println("以上model 的绑定，即数据的绑定");
        mv.setViewName("home");
        return mv;
    }

    //第三种   springMVC 的传值方式！！   model  代码少
    @RequestMapping("/model")
    public String model(AdminInfo adminInfo, Model model){
        model.addAttribute("adminName",adminInfo.getAdminName());
        model.addAttribute("adminPwd",adminInfo.getAdminPwd());
        return "home";
    }


    //第四种   springMVC 的传值方式！！   modelMap
    @RequestMapping("/modelMap")
    public String modelMap(AdminInfo adminInfo, ModelMap modelMap){
        modelMap.put("adminName",adminInfo.getAdminName());
        modelMap.put("adminPwd",adminInfo.getAdminPwd());
        return "home";
    }

    //第五种   springMVC 的传值方式！！   map 比较灵活
    @RequestMapping("/map") //除了ModelAndView    都是String接收
    public String map(AdminInfo adminInfo,Map<String,Object> map){
        map.put("adminName",adminInfo.getAdminName());
        map.put("adminPwd",adminInfo.getAdminPwd());
        return "home";

    }


}
