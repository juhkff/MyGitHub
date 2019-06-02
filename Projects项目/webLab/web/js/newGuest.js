
var userName;
var passWord;
var phoneNum;
var email;
var comName;
var comAddress;
var realName;
var IDcard;
var sex;
function register(){
    var iList=document.getElementsByTagName("input");

    userName=iList[0].value;
    passWord=iList[1].value;
    phoneNum=iList[2].value;
    email=iList[3].value;
    comName=iList[4].value;
    comAddress=iList[5].value;
    realName=iList[6].value;
    IDcard=iList[7].value;
    if(iList[8].checked===true)
        sex="1";
    else if(iList[9].checked===true)
        sex="0";
    else {
        alert("ÇëÑ¡ÔñÐÔ±ð!");
        return ;
    }
    if(email==="")
        email=null;

    window.location.href="RegComplete?userType=1&userName="+userName+"&passWord="+passWord+"&phoneNum="+phoneNum+"&email="+email+"&comName="
        +comName+"&comAddress="+comAddress+"&realName="+realName+"&IDcard="+IDcard+"&sex="+sex;
}
