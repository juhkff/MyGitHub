package wrapper;

import test.Client.Request;
import test.Client.RequestProperty;

import java.util.HashMap;
import java.util.Map;

import static wrapper.StaticVariable.URL_ADDRESS;

public class RegisterWrapper {
    public static void main(String[] args){
        String phoneNum="17860536151";
        String result=getCode(phoneNum);
        System.out.println(result);
    }
    public static String getCode(String phoneNum){
        Map<String,String> parameters=new HashMap<String, String>();
        parameters.put("phone_num",phoneNum);
        Request request=new Request(URL_ADDRESS+"/SendCode",parameters,RequestProperty.APPLICATION);
        String result=request.doPost();
        return result;
    }

    public static String verifyCode(String phoneNum,String code){
        Map<String,String> parameters=new HashMap<String, String>();
        parameters.put("phone_num",phoneNum);
        parameters.put("code",code);
        Request request=new Request(URL_ADDRESS+"/Compare",parameters,RequestProperty.APPLICATION);
        String result=request.doPost();
        return result;
    }

    public static String createNewUser(String nickName,String passWord,String phoneNum){
        Map<String,String> parameters=new HashMap<String, String>();
        parameters.put("nickName",nickName);
        parameters.put("passWord",passWord);
        parameters.put("phone_num",phoneNum);
        Request request=new Request(URL_ADDRESS+"/Register",parameters,RequestProperty.APPLICATION);
        String userID=request.doPost();
        return userID;
    }
}
