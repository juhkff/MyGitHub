package com.controller;

import com.method.*;
import com.model.Project;
import com.model.Project_In_Published;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;

@Controller
@RequestMapping(path = "/pros")
public class ProController {
    @RequestMapping(path = "/chooseType")
    public String chooseType(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName,@RequestParam(name = "proName")String proName){
        model.addAttribute("proName",proName);
        return "loginAsStu/chooseType";
    }

    @RequestMapping(path = "/details")
    public String showDetails(Model model, @RequestParam(name = "proName")String proName){
        try {
            proName=URLDecoder.decode(proName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Project project=new Project_Details().getOneProject(proName);
        model.addAttribute("proInfo",project);
        return "proDetails";
    }

    @RequestMapping(path = "/details2")
    public String showDetails2(Model model, @RequestParam(name = "proName")String proName){
        try {
            proName=URLDecoder.decode(proName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Project project=new Project_Details().getOneProject(proName);
        model.addAttribute("proInfo",project);
        return "proDetails2";
    }

    @RequestMapping(path = "/cancel")
    public String cancelPro(Model model,@RequestParam(name = "userName")String userName,@RequestParam(name = "proName")String proName){
//        String result=new Project_Details().
        ArrayList<Project_In_Published> project_in_publisheds=new Project_Published().getPublishProjectList(userName);
        model.addAttribute("pros",project_in_publisheds);
        return "loginAsGuest/pros_publishing";
    }

    @RequestMapping(path = "/agree")
    public String agreePro(Model model, @RequestParam(name = "proName")String proName, RedirectAttributes redirectAttributes,
                           @RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        String result=new Project_Submit().passProject(proName);
        model.addAttribute("result",result);    //0-right ; -1/1:error
        redirectAttributes.addAttribute("userId",userId);
        redirectAttributes.addAttribute("userName",userName);
        return "redirect:../login/submit-label";
    }

    @RequestMapping(path = "/pass")
    public String passPro(Model model,@RequestParam(name = "proName")String proName,RedirectAttributes redirectAttributes,
                          @RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        String result=new Project_Request().agreeProjectRequest(proName);
        model.addAttribute("result",result);    //0-right ; -1/1:error
        redirectAttributes.addAttribute("userId",userId);
        redirectAttributes.addAttribute("userName",userName);
        return "redirect:../login/request-label";
    }

    @RequestMapping(path = "/reject")
    public String rejectPro(Model model,@RequestParam(name = "proName")String proName,RedirectAttributes redirectAttributes,
                          @RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        String result=new Project_Request().rejectProjectRequest(proName);
        model.addAttribute("result",result);    //0-right ; -1/1:error
        redirectAttributes.addAttribute("userId",userId);
        redirectAttributes.addAttribute("userName",userName);
        return "redirect:../login/request-label";
    }

    @RequestMapping(path = "quit")
    public String quitPro(Model model,@RequestParam(name = "proName")String proName,RedirectAttributes redirectAttributes,
                          @RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        String result=new Project_Accepted().quitProject(proName);
        model.addAttribute("result",result);    //0-right ; -1/1:error
        redirectAttributes.addAttribute("userId",userId);
        redirectAttributes.addAttribute("userName",userName);
        return "redirect:../login/received-label";
    }

    @RequestMapping(path = "submit")
    public String submitPro(Model model,@RequestParam(name = "proName")String proName,RedirectAttributes redirectAttributes,
                          @RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        String result=new Project_Accepted().submitProject(proName);
        model.addAttribute("result",result);    //0-right ; -1/1:error
        redirectAttributes.addAttribute("userId",userId);
        redirectAttributes.addAttribute("userName",userName);
        return "redirect:../login/received-label";
    }
}
