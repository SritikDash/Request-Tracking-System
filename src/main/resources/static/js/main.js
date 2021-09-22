const inputs = document.querySelectorAll(".input");

function addcl(){
	let parent = this.parentNode.parentNode;
	parent.classList.add("focus");
}

function remcl(){
	let parent = this.parentNode.parentNode;
	if(this.value == ""){
		parent.classList.remove("focus");
	}
}


inputs.forEach(input => {
	input.addEventListener("focus", addcl);
	input.addEventListener("blur", remcl);
});


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
	let userName = document.getElementById("userName").value;
	let password = document.getElementById("password").value;

	let valid = 1;
	
	document.getElementById("warning-msg").classList.add("alert");
	document.getElementById("warning-msg").classList.add("alert-warning");
    
    if(nullCheck(userName) && valid == 1) {
        // alert("UserName/Email cannot be empty");
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "UserName/Email cannot be empty";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
		//document.getElementById("warning-msg").innerHTML = "UserName/Email cannot be empty";
        valid = 0;
    }

	if(nullCheck(password) && valid == 1) {
        // alert("Password cannot be empty");
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Password cannot be empty";
		setTimeout(() => {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		//document.getElementById("warning-msg").innerHTML = "Password cannot be empty";
        valid = 0;
    }

	if(valid == 0) {
		e.preventDefault();
	}

});



/*document.getElementById("userName").addEventListener("keyup", () => {
	
	document.getElementById("warning-msg").classList.add("alert");
	document.getElementById("warning-msg").classList.add("alert-warning");
	
	let userName = document.getElementById("userName").value;
	if(userName.trim().length <= 8) {
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "UserName/Email length should be greater than 8";
		document.getElementById("btn-submit").disabled = true;
		document.getElementById("btn-submit").style.opacity = 0.5;
		document.getElementById("btn-submit").style.cursor = not-allowed;
	} else {
		document.getElementById("warning-msg").style.display = "none";
		document.getElementById("btn-submit").disabled = false;
		document.getElementById("btn-submit").style.opacity = 1;
		document.getElementById("btn-submit").style.cursor = pointer;
	}
})*/


const togglePassword = document.querySelector('#togglePassword');
const password = document.querySelector('#password');

togglePassword.addEventListener('click', function () {
    // toggle the type attribute
	console.log("entered");
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
});