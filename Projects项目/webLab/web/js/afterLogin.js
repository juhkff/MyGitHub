<!-- ���<li>�б��� -->
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
            headerTitle.innerText="��������";
            break;
        case "publisher-label":
            headerTitle.innerText="�ڷ��������";
            break;
        case "finished-label":
            headerTitle.innerText="�ѽ��������";
            break;
        case "submit-label":
            headerTitle.innerText="�ύ�б�";
            break;
        case "new-label":
            headerTitle.innerText="���������";
            break;
        case "request-label":
            headerTitle.innerText="�������";
            break;
        case "received-label":
            headerTitle.innerText="�ѽ����";
            break;
        case "completed-label":
            headerTitle.innerText="��������";
            break;
        case "refers-label":
            headerTitle.innerText="ƫ������";
            break;
        case "showAll-label":
            headerTitle.innerText="�ɽ����";
            break;
        case "teams-label":
            headerTitle.innerText="�Ŷӽ���";
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