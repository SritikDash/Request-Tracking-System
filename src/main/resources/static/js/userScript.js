document.getElementById("warning-msg").style.display = "none";

function nullCheck(params) {
    if(params.trim()==='') {
        return true
    }
    else
        return false
}


document.getElementById("btn-submit").addEventListener("click", (e) => {
	let userName = document.getElementById("userName").value;
	let firstName = document.getElementById("firstName").value;
	let lastName = document.getElementById("lastName").value;
	let email = document.getElementById("email").value;
	let password = document.getElementById("password").value;


	

	let valid = 1;
	
	document.getElementById("warning-msg").classList.add("alert");
	document.getElementById("warning-msg").classList.add("alert-warning");
	document.getElementById("warning-msg").classList.add("text-center");
	
	if(nullCheck(userName) && nullCheck(firstName) && nullCheck(lastName)  && nullCheck(email)  && nullCheck(password)  && valid == 1) {
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter All The Required* Fields";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
	}
    
    if(nullCheck(userName) && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter UserName";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }

	if(userName.length < 8 && valid == 1) {
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "UserName length must be greater than equals to 8!!";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
	}

	if(nullCheck(firstName) && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter FirstName";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }

    if(nullCheck(lastName) && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter LastName";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }

    if(nullCheck(email) && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter Email";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }

    if(nullCheck(password) && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter Password";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }

	

	

	


	if(valid == 0) {
		e.preventDefault();
	}
	
	document.getElementById("userName").value=userName.trim();
	document.getElementById("firstName").value=firstName.trim();
	document.getElementById("lastName").value=lastName.trim();
	document.getElementById("email").value=email.trim();

});