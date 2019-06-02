package com.controller;

import com.method.*;
import com.model.*;
import com.mysql.fabric.Server;
import com.sun.org.apache.xpath.internal.operations.Mod;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping(path = "/login")
public class LoginController {
    @RequestMapping(path = "/teams-label")
    public String showTeamInfo(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName,/*@RequestParam(name = "teamName")String teamName,*/
                               HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        String teamName = null;
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("web_teamName"))
                teamName=cookie.getValue();
        }
        System.out.println("teamName的值1:"+teamName);
        teamName=teamName.replaceAll(" +"," ");
        System.out.println("teamName的值2:"+teamName);
        if (teamName.startsWith(" "))
            teamName=teamName.substring(1);
        System.out.println("teamName的值3:"+teamName);
        if (teamName==null||teamName.equals("")||teamName.equals(" ")){
            System.out.println("if语句执行！");
            //用户未加入团队，加载用户列表
            ArrayList<Team> teams = new Team_Show().getTeamList();
            model.addAttribute("team", teams);
            return "loginAsStu/TeamList";
        }else {
            Team team=new Team_Detail().getTeamDetails(teamName);
            model.addAttribute("team",team);
            return "loginAsStu/TeamDetail";
        }
    }

    @RequestMapping(path = "/showTeamDetail")
    public String showTeamDetails(Model model,@RequestParam(name = "teamName")String teamName){
        Team team=new Team_Detail().getTeamDetails(teamName);
        model.addAttribute("team",team);
        return "loginAsStu/TeamDetail";
    }

    @RequestMapping(path = "/joinTeam")
    public String joinTeam(Model model,@RequestParam(name = "realName")String realName,@RequestParam(name = "stuId")String stuId,@RequestParam(name = "teamName")String teamName){
        String result=new Team_Show().addMember(stuId,realName,teamName);
        if (result.equals("0")){
            //complete
            Team team=new Team_Detail().getTeamDetails(teamName);
            model.addAttribute("result",result);
            model.addAttribute("team",team);
            return "loginAsStu/TeamDetail";
        }else {
            //false/error
            model.addAttribute("result",result);
            return "loginAsStu/TeamList";
        }
    }

    @RequestMapping(path = "/createNewTeam")
    public String showCreateTDetails(Model model){
        return "loginAsStu/createTeam";
    }

    @RequestMapping(path = "/createTeam")
    public String createTeam(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName,@RequestParam(name = "realName")String realName,@RequestParam(name = "stuId")String stuId,
                             @RequestParam(name = "teamName")String teamName,@RequestParam(name = "teamIntro")String teamIntro,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
        String result=new Team_Show().createTeam(userName,realName,stuId,teamName,teamIntro);
        if (result.equals("0")) {
            Cookie[] cookies=request.getCookies();
            int i=0;
            for(Cookie cookie:cookies){
                if (cookie.getName().equals("web_isLeader")){
                    cookie=new Cookie("web_isLeader","1");
                    cookie.setPath("/webLab");
                    response.addCookie(cookie);
                    i++;
                    if (i==2)
                        break;
                }else if (cookie.getName().equals("web_teamName")){
                    cookie=new Cookie("web_teamName",teamName);
                    cookie.setPath("/webLab");
                    response.addCookie(cookie);
                    i++;
                    if (i==2)
                        break;
                }
            }
            /*
            Team team = new Team_Detail().getTeamDetails(teamName);
            model.addAttribute("team", team);
            model.addAttribute("result", result);
            */
            redirectAttributes.addAttribute("userId",userId);
            redirectAttributes.addAttribute("userName",userName);
            redirectAttributes.addAttribute("teamName",teamName);
            return "redirect:teams-label";
        }else {
            model.addAttribute("result",result);
            return "loginAsStu/TeamList";
        }
    }

    /*@RequestMapping(path = "/quitTeam")
    public String quitTeam(Model model){
        String result=new Team_Detail().
    }*/

    @RequestMapping(path = "/uploadRefers")
    public String uploadRefers(Model model, @RequestParam(name = "referList")String preferList, @RequestParam(name = "userId")String userId,
                               @RequestParam(name = "userName")String userName,RedirectAttributes redirectAttributes){
        String result=new Preference().uploadPrefer(preferList,userId);
        redirectAttributes.addAttribute("userId",userId);
        redirectAttributes.addAttribute("userName",userName);
        model.addAttribute("result",result);

        return "redirect:refers-label";
    }

    @RequestMapping(path = "/prefers-sub")
    public String getRefers(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        String[] prefers=new Preference().showPrefers(userId);
        if (prefers.length==1&&prefers[0].equals(""))
            prefers[0]="待添加";
        model.addAttribute("preferList",prefers);
        return "loginAsStu/prefers_sub";
    }

    @RequestMapping(path = "/refers-label")
    public String showRefers(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        String[] prefers=new Preference().showPrefers(userId);
        String prefer="";
        for(String each:prefers)
            prefer+=each+" ";
        prefer=prefer.replaceAll(" +"," ");
        if (prefer.equals(" ")||prefer.equals(""))
            prefer="";
        model.addAttribute("preferString",prefer);
        return "loginAsStu/prefers";
    }

    @RequestMapping(path = "/showAll-label")
    public String showAll(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        ArrayList<Project_In_Show> project_in_shows=new Project_Show().getProjectList();
        model.addAttribute("pros",project_in_shows);
        return "loginAsStu/pros_All";
    }

    @RequestMapping(path = "/searchPros")
    public String searchPros(Model model,/*@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName,*/@RequestParam(name = "proTags")String proTags){
        ArrayList<Project_In_Show> project_in_shows=new Project_Show().searchProjects(proTags);
        model.addAttribute("pros",project_in_shows);
        return "loginAsStu/pros_All";
    }

    @RequestMapping(path = "/sendRequest",method = RequestMethod.GET)
    public String sendRequest(Model model,RedirectAttributes redirectAttributes, @RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName,
                              @RequestParam(name = "proName")String proName,@RequestParam(name = "proType")String proType,@RequestParam(name = "receiverName")String receiverName){
        String result=new Project_Details().sendRequest(proName,proType,receiverName);
        redirectAttributes.addAttribute("userId",userId);
        redirectAttributes.addAttribute("userName",userName);
        return "redirect:showAll-label";
    }

    @RequestMapping(path = "/completed-label")
    public String showCompleted(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        ArrayList<Project_In_Finished> project_in_finisheds=new Project_Finished().getFinishedProjectList(userId);
        model.addAttribute("pros",project_in_finisheds);
        return "loginAsStu/pros_finished";
    }

    @RequestMapping(path = "/received-label")
    public String showReceived(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        ArrayList<Project_In_Accepted> project_in_accepteds=new Project_Accepted().getAcceptedProjectList(userId);
        model.addAttribute("pros",project_in_accepteds);
        return "loginAsStu/pros_received";
    }

    @RequestMapping(path = "/info-panel")
    public String showUserInfo(Model model) {
        return "loginAsGuest/guestInfo";
    }

    @RequestMapping(path = "/publisher-label")
    public String showPublishing(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        ArrayList<Project_In_Published> project_in_publisheds=new Project_Published().getPublishProjectList(userName);
        model.addAttribute("pros",project_in_publisheds);
        return "loginAsGuest/pros_publishing";
    }

    @RequestMapping(path = "/finished-label")
    public String showFinished(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        ArrayList<Project_In_Completed> project_in_completeds=new Project_Completed().getCompletedProjectList(userName);
        model.addAttribute("pros",project_in_completeds);
        return "loginAsGuest/pros_completed";
    }

    @RequestMapping(path = "/submit-label")
    public String showSubmited(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        ArrayList<Project_In_Submit> project_in_submits=new Project_Submit().getSubmitProjectList(userName);
        model.addAttribute("pros",project_in_submits);
        return "loginAsGuest/pros_submitted";
    }

    @RequestMapping(path = "/new-label")
    public String createNew(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        return "loginAsGuest/pros_new";
    }

    @RequestMapping(path = "/request-label")
    public String showRequested(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        ArrayList<Project_In_Request> project_in_requests=new Project_Request().getProjectList(userName);
        model.addAttribute("pros",project_in_requests);
        return "loginAsGuest/pros_requested";
    }

    @RequestMapping(path = "/doing")
    public String showDoing(Model model,@RequestParam(name = "userId")String userId,@RequestParam(name = "userName")String userName){
        ArrayList<Project_In_Request> project_in_requests=new Project_Doing().getDoingProjectList(userName);
        model.addAttribute("pros",project_in_requests);
        return "loginAsGuest/pros_doing";
    }

    @RequestMapping(path = "/newOne",method = RequestMethod.POST)
    public String afterCreate(Model model,@RequestParam(name = "pro_name")String proName,@RequestParam(name = "pro_refers")String proRefers,
                              @RequestParam(name = "pro_content")String proContent,@RequestParam(name = "pro_deadLine")String proDeadLine,
                              @RequestParam(name = "pro_reward")String proReward,@RequestParam(name = "user_name")String userName,
                              @RequestParam(name = "user_phoneNum")String phoneNum){
        String curDate= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String result=new Project_Release().uploadProject(proName,proRefers,proContent,proDeadLine,proReward,userName,curDate,phoneNum);
        model.addAttribute("result",result);
        ArrayList<Project_In_Published> project_in_publisheds=new Project_Published().getPublishProjectList(userName);
        model.addAttribute("pros",project_in_publisheds);
        return "loginAsGuest/pros_publishing";
    }

    @RequestMapping(path = "/uploadInfo")
    public String uploadInfo(HttpServletRequest request,Model model, /*@RequestParam(name = "userName") String userName,*/ @RequestParam(name = "phoneNum") String phoneNum, @RequestParam(name = "email") String email) {
        Cookie[] cookies = request.getCookies();
        String userId = null;
        String userName=null;
        int i=0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("web_userId")) {
                userId=cookie.getValue();
                i++;
                if (i==2)
                    break;
            }else if (cookie.getName().equals("web_userName")){
                userName=cookie.getValue();
                i++;
                if (i==2)
                    break;
            }
        }
        String result="-1";
        try {
            result = new Login().updateUserInfo(userName,phoneNum,email,userId);
            //-1:error 0:right
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("result",result);
        return "loginAsGuest/guestInfo";
    }

    @RequestMapping(path = "/getImg")
    public void showImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        for (Cookie each : cookies) {
            if (each.getName().equals("web_userId")) {
                cookie = each;
                break;
            }
        }
        OutputStream outputStream = response.getOutputStream();
        String userId;
        if (cookie != null)
            userId = cookie.getValue();
        else
            throw new IOException("找不到cookie!");
        InputStream inputStream = new Login().getHeadIcon(userId);
        byte[] headIcon = new byte[inputStream.available()];
        inputStream.read(headIcon, 0, inputStream.available());
        inputStream.close();
        outputStream.write(headIcon, 0, headIcon.length);
    }

    @RequestMapping(path = "/uploadImg", method = RequestMethod.POST)
    public String upload(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        Cookie[] cookies = request.getCookies();
        String userId = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("web_userId")) {
                userId = cookie.getValue();
                break;
            }
        }
        if (!file.isEmpty() && userId != null) {
            //上传文件名
            String fileName = file.getOriginalFilename();
            Long fileSize = file.getSize();
            long kbSize = fileSize / 1024;
            double mbSize = kbSize / 1024;
            //获得文件流
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                if (inputStream != null) {
                    new Login().updateHeadIcon(userId, inputStream);
                    inputStream.close();
                    model.addAttribute("upload", "true");
                }
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("upload", "false");
            }
        }
        return "loginAsGuest/guestInfo";
    }
}
