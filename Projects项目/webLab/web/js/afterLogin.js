<!-- 获得<li>列表集合 -->
var lis=document.getElementsByTagName("li");

var curPage=0;

var frame=document.getElementsByClassName("container-frame")[0];

var iName="info-panel";

var headerTitle=document.getElementsByClassName("top_text")[0];

var userId=document.cookie.split("web_userId=")[1].split(";")[0];
var userName=document.cookie.split("web_userName=")[1].split(";")[0];
var teamName=document.cookie.split("web_teamName=")[1].split(";")[0];

function changeHeaderTitle() {
    switch (iName){
        case "info-panel":
            headerTitle.innerText="个人中心";
            break;
        case "publisher-label":
            headerTitle.innerText="在发布的外包";
            break;
        case "finished-label":
            headerTitle.innerText="已结束的外包";
            break;
        case "submit-label":
            headerTitle.innerText="提交列表";
            break;
        case "new-label":
            headerTitle.innerText="发布新外包";
            break;
        case "request-label":
            headerTitle.innerText="外包申请";
            break;
        case "received-label":
            headerTitle.innerText="已接外包";
            break;
        case "completed-label":
            headerTitle.innerText="已完成外包";
            break;
        case "refers-label":
            headerTitle.innerText="偏好设置";
            break;
        case "showAll-label":
            headerTitle.innerText="可接外包";
            break;
        case "teams-label":
            headerTitle.innerText="团队界面";
            break;
        default:
            headerTitle.innerText="undefined";
            break;
    }
}

changeHeaderTitle();

if (curPage === 0) {
    lis[0].style.backgroundColor="white";
    lis[0].style.cursor="default";
    lis[0].onmouseover=function () {
        this.style.backgroundColor="white";
    };
    lis[0].onmouseout=function () {
        this.style.backgroundColor="white";
    };
}

function changeBackGroundColor(target){
    for(var i=0;i<lis.length;i++){
        lis[i].style.backgroundColor="#cbcbcb";
        lis[i].style.cursor="pointer";
        lis[i].onmouseover=function(){
            this.style.backgroundColor="gray";
        };
        lis[i].onmouseout=function(){
            this.style.backgroundColor="#cbcbcb";
        };
    }
    target.style.backgroundColor="white";
    target.onmouseover=function(){
        this.style.backgroundColor="white";
    };
    target.onmouseout=function(){
        this.style.backgroundColor="white";
    };
    target.style.cursor="default";
}

function changeFrame(target){
    iName=target.id;
    frame.src="../login/"+iName+"?userId="+userId+"&userName="+userName;
    if (iName === "teams-label") {
        frame.src+="&teamName="+teamName;
    }
    changeHeaderTitle();
}