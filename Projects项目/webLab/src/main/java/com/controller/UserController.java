package com.controller;

import com.method.Login;
import com.method.Team_Detail;
import com.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Controller
@RequestMapping(path = "/")
public class UserController {
    @RequestMapping(path = "/userInfo")
    public String showUserDetails(Model model, @RequestParam(name = "userName")String userName){
        User user=new Login().getUserInfo(null,userName);
        model.addAttribute("user",user);
        return "userDetail";
    }

    @RequestMapping(path = "/userInfo2")
    public String showUserDetails2(Model model, @RequestParam(name = "realName")String realName){
        User user=new Team_Detail().getUserInfomation(realName);
        model.addAttribute("user",user);
        return "userDetail";
    }

    @RequestMapping(path = "/userInfo/getImg")
    public void showImg(@RequestParam(name = "userName")String userName, HttpServletResponse response) throws IOException {
        OutputStream outputStream = response.getOutputStream();
        InputStream inputStream = new Login().getHeadIcon(null,userName);
        byte[] headIcon = new byte[inputStream.available()];
        inputStream.read(headIcon, 0, inputStream.available());
        inputStream.close();
        outputStream.write(headIcon, 0, headIcon.length);
    }
}
