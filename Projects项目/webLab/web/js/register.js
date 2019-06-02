function getType(evt) {
    if(evt.className==="bottom_left")
        window.location.pathname="/webLab/register/GuestReg";
    else if(evt.className==="bottom_right")
        window.location.pathname="/webLab/register/StudentReg";
}