document.getElementById("warning-msg").style.display = "none";

function nullCheck(params) {
    if(params.trim()==='') {
        return true
    }
    else
        return false
}


document.getElementById("btn-submit").addEventListener("click", (e) => {
	console.log("Inside JS");
	let nameEmail = document.getElementById("nameEmail").value;
	let newPassword = document.getElementById("newPassword").value;
	let confirmPassword = document.getElementById("confirmPassword").value;
	let valid = 1;
	
	document.getElementById("warning-msg").classList.add("alert");
	document.getElementById("warning-msg").classList.add("alert-warning");
    
	if(nullCheck(nameEmail) && nullCheck(newPassword) && nullCheck(confirmPassword) && valid == 1) {
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter All the Fields";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
	}

    if(nullCheck(nameEmail) && valid == 1) {
        
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Username/Email cannot be empty";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
		
        valid = 0;
    }

	if(nullCheck(newPassword) && valid == 1) {
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "New Password cannot be empty";
		setTimeout(() => {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
        valid = 0;
    }

	if(nullCheck(confirmPassword) && valid == 1) {
       
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Confirm Password cannot be empty";
		setTimeout(() => {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
        valid = 0;
    }

	if(valid == 0) {
		e.preventDefault();
	}

});