document.getElementById("warning-msg").style.display = "none";

function nullCheck(params) {
    if(params.trim()==='') {
        return true
    }
    else
        return false
}


document.getElementById("btn-submit").addEventListener("click", (e) => {
	let rqstTitle = document.getElementById("rqstTitle").value;
	let rqstDesc = document.getElementById("rqstDesc").value;
	let deptSelect = document.getElementById("deptSelect").value;
	let userSelect = document.getElementById("userSelect").value;
	let status = document.getElementById("status").value;


	

	let valid = 1;
	
	document.getElementById("warning-msg").classList.add("alert");
	document.getElementById("warning-msg").classList.add("alert-warning");
	document.getElementById("warning-msg").classList.add("text-center");
	
	if(nullCheck(rqstTitle) && nullCheck(rqstDesc) && valid == 1) {
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter All The Required* Fields";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
	}
    
    if(nullCheck(rqstTitle) && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter Request Title Field";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }

	if(nullCheck(rqstDesc) && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Enter Request Description Field";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }

	if(deptSelect=="none" && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Select A Department code";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }

	if(userSelect=="none" && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Select A User ID";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }

	if(status == "none" && valid == 1) {
		
		document.getElementById("warning-msg").style.display = "block";
		document.getElementById("warning-msg").innerHTML = "Please Select A Status";
		setTimeout(()=> {
			document.getElementById("warning-msg").style.display = "none";
		}, 4000);
		
        valid = 0;
    }


	if(valid == 0) {
		e.preventDefault();
	}

});





//=======================================================================================

var req = new XMLHttpRequest();

// document.onload((e) => {
// 	let deptId = document.getElementById("deptSelect").value;
// 	console.log(deptId);
// 	var reqUrl = 'http://localhost:9090/getAllUsersByDept?deptId='+deptId;
//     console.log(reqUrl);
//     req.onreadystatechange = getResponse;
//     req.open('GET', reqUrl);
//     req.send();
// })



document.addEventListener("DOMContentLoaded", function(){
    //dom is fully loaded, but maybe waiting on images & css files
	let deptId = document.getElementById("deptSelect").value;
	console.log(deptId);
	var reqUrl = 'http://localhost:9090/getAllUsersByDept?deptId='+deptId;
    console.log(reqUrl);
    req.onreadystatechange = getResponse;
    req.open('GET', reqUrl);
    req.send();
});

// document.getElementById("deptSelect").addEventListener("load", (e) => {
	
// })

function getResponse() {
    // const cityList = document.querySelector('#cityList');
    // cityList.innerHTML = ''
    if(req.readyState == 4 && req.status == 200) {
        var jsonData = JSON.parse(req.responseText);
        console.log(jsonData);
        // for(var i = 0; i < jsonData.length; i++) {
        //     console.log(jsonData[i].name);
        //     cityList.appendChild(createCityList(jsonData[i].name));
        // }
		const selectBox = document.getElementById("userSelect");
		
		/*for(var i = 0; i < jsonData.length; i++) {
			var newOption = document.createElement('option');
			console.log("hi");
			console.log(jsonData);
			newOption.value = jsonData.key;
			newOption.text = jsonData.value;
			selectBox.appendChild(newOption);
		}*/
		while (selectBox.lastElementChild) {
				selectBox.removeChild(selectBox.lastElementChild);
		}
		
		
		for(data in jsonData) {

			

			var newOption = document.createElement('option');
			
			
			newOption.value = jsonData[data].userId;
			newOption.text = jsonData[data].userName;
			selectBox.appendChild(newOption);


			console.log(jsonData[data].userName)
		}
    }
}





//////////////////////////////////////////////////////////////////////////////////////////////////



// var req = new XMLHttpRequest();

document.getElementById("deptSelect").addEventListener("change", (e) => {
	let deptId = document.getElementById("deptSelect").value;
	console.log(deptId);
	var reqUrl = 'http://localhost:9090/getAllUsersByDept?deptId='+deptId;
    console.log(reqUrl);
    req.onreadystatechange = getResponse;
    req.open('GET', reqUrl);
    req.send();
})

// function getResponse() {
//     // const cityList = document.querySelector('#cityList');
//     // cityList.innerHTML = ''
//     if(req.readyState == 4 && req.status == 200) {
//         var jsonData = JSON.parse(req.responseText);
//         console.log(jsonData);
//         // for(var i = 0; i < jsonData.length; i++) {
//         //     console.log(jsonData[i].name);
//         //     cityList.appendChild(createCityList(jsonData[i].name));
//         // }
// 		const selectBox = document.getElementById("userSelect");
		
// 		/*for(var i = 0; i < jsonData.length; i++) {
// 			var newOption = document.createElement('option');
// 			console.log("hi");
// 			console.log(jsonData);
// 			newOption.value = jsonData.key;
// 			newOption.text = jsonData.value;
// 			selectBox.appendChild(newOption);
// 		}*/
// 		while (selectBox.lastElementChild) {
// 				selectBox.removeChild(selectBox.lastElementChild);
// 		}
		
		
// 		for(data in jsonData) {

			

// 			var newOption = document.createElement('option');
			
			
// 			newOption.value = jsonData[data].userId;
// 			newOption.text = jsonData[data].userName;
// 			selectBox.appendChild(newOption);


// 			console.log(jsonData[data].userName)
// 		}
//     }
// }