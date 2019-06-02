package com.controller;

import com.google.gson.Gson;
import com.method.Login;
import com.model.EasyUser;
import com.model.LoginUser;
import com.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.HttpCookie;
import java.util.ArrayList;

@Controller
@RequestMapping(path = "/index")
public class IndexController {
    @RequestMapping(path = "/Register")
    public String register(Model model) {
        return "register";
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public String login(Model model, @RequestParam(name = "userId") String userId, @RequestParam(name = "passWord") String passWord, HttpServletRequest request, HttpServletResponse response) {
        String loginResult = new Login().processLogin(userId, passWord);
        if (!loginResult.equals("0")) {
            if (loginResult.equals("1")) {
                model.addAttribute("result", loginResult);
                return "index";
            } else if (loginResult.equals("2")) {
                model.addAttribute("result", loginResult);
                model.addAttribute("userId", userId);
                return "index";
            }
        }
        User user = new Login().getUserInfo(userId);
        model.addAttribute("user", user);
        Cookie[] cookies = request.getCookies();
        boolean ishave = false;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("web_userId")) {
                if (cookie.getValue().equals(userId)) {
                    ishave = true;
                    break;
                } else {
                    ishave = false;
                    break;
                }
            }
        }
        if (!ishave) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
            }
            cookies = new Cookie[14];
            cookies[0] = new Cookie("web_userId", userId);
            cookies[1] = new Cookie("web_passWord", passWord);
            cookies[2] = new Cookie("web_userType", user.getUserType());
            cookies[3] = new Cookie("web_userName", user.getUserName());
            cookies[4] = new Cookie("web_phoneNum", user.getPhoneNum());
            cookies[5] = new Cookie("web_email", user.getEmailAddress());
            cookies[6] = new Cookie("web_IDcard", user.getIdCard());
            cookies[7] = new Cookie("web_status1", user.getStatus1());
            cookies[8] = new Cookie("web_status2", user.getStatus2());
            cookies[9] = new Cookie("web_realName", user.getRealName());
            cookies[10] = new Cookie("web_sex", user.getSex());
            cookies[11] = new Cookie("web_prefers", user.getPrefers());
            cookies[12] = new Cookie("web_isLeader", user.getIsLeader());
            cookies[13] = new Cookie("web_teamName", user.getTeamName());
            for (int i = 0; i < 14; i++) {
                cookies[i].setPath("/webLab");
                response.addCookie(cookies[i]);
            }
        }
        return "afterLogin";
    }

    @RequestMapping(path = "/Forget")
    public String findPW(Model model) {
        return "forgetPW";
    }

    @RequestMapping(path = "/showUserList")
    public String showUsers(Model model){
        return "Users";
    }

    @RequestMapping(path = "/searchUserList")
    public String searchUser(Model model,@RequestParam(name = "userInfo")String userInfo,HttpServletRequest request){
        userInfo=userInfo.replaceAll(" +"," ");
        String[] infoList=userInfo.split(" ");
        ArrayList<EasyUser> easyUsers=new Login().searchUsers(infoList);
        request.getSession().setAttribute("userList",easyUsers);
        return "forgetPW";
    }
}
