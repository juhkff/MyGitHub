package com.controller;

import com.method.Register;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/register")
public class RegisterController {
    @RequestMapping(path = "/StudentReg")
    public String newStudent(Model model){
        model.addAttribute("userType","0");                     //学生:"0"
        return "register/newStudent";
    }

    @RequestMapping(path = "/GuestReg")
    public String newGuest(Model model){
        model.addAttribute("userType","1");                     //客户:"1"
        return "register/newGuest";
    }

    @RequestMapping(path = "/RegComplete")
    public String completeReg(Model model, @RequestParam(name = "userType")String userType,@RequestParam(name = "userName")String userName,
                              @RequestParam(name = "passWord")String passWord,@RequestParam(name = "phoneNum")String phoneNum,
                              @RequestParam(name = "email")String email,@RequestParam(name = "comName")String comName,
                              @RequestParam(name = "comAddress")String comAddress,@RequestParam(name = "realName")String realName,
                              @RequestParam(name = "IDcard")String IDcard,@RequestParam(name = "sex")String sex){
        String newID="error!";
        try {
            newID=new Register().createNewMember(userType,userName,passWord,phoneNum,email,sex,realName,IDcard,comName,comAddress);
        } catch (Exception e) {
            e.printStackTrace();
            newID="error in process!";
        }

        model.addAttribute("userId",newID);
        return "register/RegCompleted";
    }
}
