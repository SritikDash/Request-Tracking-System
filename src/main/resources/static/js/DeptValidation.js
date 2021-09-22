document.getElementById("warning-msg").style.display = "none";

function nullCheck(params) {
    if(params.trim()==='') {
        return true
    }
    else
        return false
}

document.getElementById("btn-submit").addEventListener("click", (e) => {
    // console.log("Inside JS");
    let DeptCode = document.getElementById("DeptCode").value;
    let ParentDeptCode = document.getElementById("ParentDeptCode").value;
    let DeptName = document.getElementById("DeptName").value;
    //let Active = document.getElementById("Active").value;

    let valid = 1;
    
    document.getElementById("warning-msg").classList.add("alert");
    document.getElementById("warning-msg").classList.add("alert-warning");
    
    if(nullCheck(DeptCode) && valid == 1) {
        
        
        document.getElementById("warning-msg").style.display = "block";
        document.getElementById("warning-msg").innerHTML = "Department Code Cannot be empty";
        setTimeout(()=> {
            document.getElementById("warning-msg").style.display = "none";
        }, 4000);
        
        
        valid = 0;
    }


	if((DeptCode.length < 4 || DeptCode.length > 6) && valid == 1) {
		document.getElementById("warning-msg").style.display = "block";
        document.getElementById("warning-msg").innerHTML = "Department Code length should be between 4 and 6";
        setTimeout(() => {
            document.getElementById("warning-msg").style.display = "none";
        }, 4000);
        valid = 0;
	}  

    if(nullCheck(ParentDeptCode) && valid == 1) {
        document.getElementById("warning-msg").style.display = "block";
        document.getElementById("warning-msg").innerHTML = "Parent Department Code cannot be empty";
        setTimeout(() => {
            document.getElementById("warning-msg").style.display = "none";
        }, 4000);
        valid = 0;
    }

    if(nullCheck(DeptName) && valid == 1) {
        document.getElementById("warning-msg").style.display = "block";
        document.getElementById("warning-msg").innerHTML = "Department Name cannot be empty";
        setTimeout(() => {
            document.getElementById("warning-msg").style.display = "none";
        }, 4000);
        valid = 0;
    }

   /* if(nullCheck(Active) && valid==1){
        document.getElementById("warning-msg").style.display = "block";
        document.getElementById("warning-msg").innerHTML = "CheckBox is not Clicked";
        setTimeout(() => {
            document.getElementById("warning-msg").style.display = "none";
        }, 4000);
        valid = 0;}*/

    if(valid == 0) {
        e.preventDefault();
    }
	document.getElementById("DeptCode").value=DeptCode.trim();
	document.getElementById("DeptName").value=DeptName.trim();
	document.getElementById("ParentDeptCode").value=ParentDeptCode.trim();

});
