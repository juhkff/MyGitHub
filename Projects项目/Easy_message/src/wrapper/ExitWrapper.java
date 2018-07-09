package wrapper;

import test.Client.Request;
import test.Client.RequestProperty;

import java.util.HashMap;
import java.util.Map;

import static wrapper.StaticVariable.URL_ADDRESS;

public class ExitWrapper {

    public static void exit(String userID){
        Map<String,String> parameters=new HashMap<String, String>();
        parameters.put("userID",userID);
        Request request=new Request(URL_ADDRESS+"/Exit",parameters,RequestProperty.APPLICATION);
        String result=request.doPost();
    }
}
