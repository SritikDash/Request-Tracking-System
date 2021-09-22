


//////////////////////////////////////////////////////////////////////////////

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
	let oldPassword = document.getElementById("oldPassword").value;
	let newPassword = document.getElementById("newPassword").value;
	let confirmPassword = document.getElementById("confirmPassword").value;
	let valid = 1;
	
	document.getElementById("warning-msg").classList.add("alert");
	document.getElementById("warning-msg").classList.add("alert-warning");
    
    if(nullCheck(oldPassword) && valid == 1) {
        
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Current Password cannot be empty";
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



// document.getElementById("oldPassword").addEventListener("keyup", () => {
	
// 	document.getElementById("warning-msg").classList.add("alert");
// 	document.getElementById("warning-msg").classList.add("alert-warning");
	
// 	let userName = document.getElementById("userName").value;
// 	if(userName.trim().length <= 8) {
// 		document.getElementById("warning-msg").style.display = "block";
// 		document.getElementById("warning-msg").innerHTML = "Password length should be greater than 8";
// 		document.getElementById("btn-submit").disabled = true;
// 		document.getElementById("btn-submit").style.opacity = 0.5;
// 		document.getElementById("btn-submit").style.cursor = not-allowed;
// 	} else {
// 		document.getElementById("warning-msg").style.display = "none";
// 		document.getElementById("btn-submit").disabled = false;
// 		document.getElementById("btn-submit").style.opacity = 1;
// 		document.getElementById("btn-submit").style.cursor = pointer;
// 	}
// })

// document.getElementById("newPassword").addEventListener("keyup", () => {
	
// 	document.getElementById("warning-msg").classList.add("alert");
// 	document.getElementById("warning-msg").classList.add("alert-warning");
	
// 	let userName = document.getElementById("userName").value;
// 	if(userName.trim().length <= 8) {
// 		document.getElementById("warning-msg").style.display = "block";
// 		document.getElementById("warning-msg").innerHTML = "New Password length should be greater than 8";
// 		document.getElementById("btn-submit").disabled = true;
// 		document.getElementById("btn-submit").style.opacity = 0.5;
// 		document.getElementById("btn-submit").style.cursor = not-allowed;
// 	} else {
// 		document.getElementById("warning-msg").style.display = "none";
// 		document.getElementById("btn-submit").disabled = false;
// 		document.getElementById("btn-submit").style.opacity = 1;
// 		document.getElementById("btn-submit").style.cursor = pointer;
// 	}
// })

// document.getElementById("confirmPassword").addEventListener("keyup", () => {
	
// 	document.getElementById("warning-msg").classList.add("alert");
// 	document.getElementById("warning-msg").classList.add("alert-warning");
	
// 	let userName = document.getElementById("userName").value;
// 	if(userName.trim().length <= 8) {
// 		document.getElementById("warning-msg").style.display = "block";
// 		document.getElementById("warning-msg").innerHTML = "For Confirmation Password length should be greater than 8";
// 		document.getElementById("btn-submit").disabled = true;
// 		document.getElementById("btn-submit").style.opacity = 0.5;
// 		document.getElementById("btn-submit").style.cursor = not-allowed;
// 	} else {
// 		document.getElementById("warning-msg").style.display = "none";
// 		document.getElementById("btn-submit").disabled = false;
// 		document.getElementById("btn-submit").style.opacity = 1;
// 		document.getElementById("btn-submit").style.cursor = pointer;
// 	}
// })