function updateUsers()
{
	//alert("Updating.....");
	//alert(document.getElementById("viewAllResults"));
	document.getElementById("viewAllResults").style.display="none";
	document.getElementById("updateUserResults").style.display="block";
	//document.getElementById("updateUserResults").style.display="none";
    //document.getElementById("updateForm").style.display="block";
   
	var abc= $.ajax({
    	dataType: "json",
    	url: "/viewAllUsers",
    	global: false,
        async:false,
    	success: function(data){
    			for(var i=0;i<data.length;i++)
    			{
    				a[i]=data[i]._id;
    			}
    			
    			$.each(data, function(idx, elem){
    	    	    $('table#updateUserTable TBODY').append('<tr><td>'+elem._id+'</td><td>'+elem.firstName +'</td><td>'+elem.lastName +'</td><td>'+elem.ssn +'</td><td>'+elem.gender +'</td><td>'+elem.state +'</td><td>'+elem.city +'</td><td>'+elem.zipCode +'</td><td>'+elem.phone +'</td><td>'+elem.email +'</td><td>'+elem.tagNo +'</td><td>'+elem.bedId +'</td><td><input type="button" class="mybutton" id="'+elem._id +'" value="Update" onclick="updateUser(this.id)"></input></td></tr>');
    	    	    });
    			
    			
    	    //b=data[1]._id;
    		//alert("success");
    	                      // });
    	}

    	}).responseText;
	
	 document.getElementById("deleteUserResults").style.display="none";
	    document.getElementById("viewAllBedResults").style.display="none";
	    document.getElementById("deleteBedResults").style.display="none";
	}

function updateUser(id)
{
	//alert(id);
	document.getElementById("viewAllResults").style.display="none";
	document.getElementById("updateUserResults").style.display="none";
	document.getElementById("updateForm").style.display="block";
	document.getElementById("l_id").innerHTML=id;
	document.getElementById("updateUserResults").style.display="none";
    //document.getElementById("updateForm").style.display="none";
    document.getElementById("deleteUserResults").style.display="none";
    document.getElementById("viewAllBedResults").style.display="none";
    document.getElementById("deleteBedResults").style.display="none";
	
}

function update()
{
	//alert("Finally Updating");
	var id = document.getElementById('l_id').innerHTML;
//	alert(id);
	var fname = document.getElementById('u_firstName').value;
	var lname = document.getElementById('u_lastName').value;
	var gender = document.getElementById('u_gender').value;
	var state = document.getElementById('u_state').value;
	var city = document.getElementById('u_city').value;
	var zipCode = document.getElementById('u_zipCode').value;
	var phone = document.getElementById('u_phone').value;
	var email = document.getElementById('u_email').value;
	var tagNo = document.getElementById('u_tagNo').value;
	var bedId = document.getElementById('u_bedId').value;

	
	var abc = $.ajax({
        dataType: "json",
        url: "/updateUserInfo/" + id + "/" + fname + "/" + lname + "/" + gender + "/" + ssn + "/" + city + "/" + state + "/" + zipCode + "/" + phone + "/" + email + "/" + tagNo + "/" + bedId,
        global: false,
        async: false,
        success: function(data) {
        	
        	location.reload();
        	//alert("success");
        }
	}).responseText;
//	alert(abc);
	
}


function deleteUsers()
{
//	alert("Deleting.....");
//	alert(document.getElementById("viewAllResults"));
	document.getElementById("viewAllResults").style.display="none";
	document.getElementById("updateForm").style.display="none";
	document.getElementById("deleteUsersResults").style.display="block";
	
	//document.getElementById("viewAllResults").style.display="none";
	
	
	var abc= $.ajax({
    	dataType: "json",
    	url: "/viewAllUsers",
    	global: false,
        async:false,
    	success: function(data){
    			for(var i=0;i<data.length;i++)
    			{
    				a[i]=data[i]._id;
    			}
    			
    			$.each(data, function(idx, elem){
    	    	    $('table#deleteUserTable TBODY').append('<tr><td>'+elem._id+'</td><td>'+elem.firstName +'</td><td>'+elem.lastName +'</td><td>'+elem.ssn +'</td><td>'+elem.gender +'</td><td>'+elem.state +'</td><td>'+elem.city +'</td><td>'+elem.zipCode +'</td><td>'+elem.phone +'</td><td>'+elem.email +'</td><td>'+elem.tagNo +'</td><td>'+elem.bedId +'</td><td><input type="button" class="mybutton" id="'+elem._id +'" name="'+elem.firstName +'/'+elem.lastName +'" value="Delete" onclick="deleteUser(this.id,this.name)"></input></td></tr>');
    	    	    });
    			
    			
    	    //b=data[1]._id;
    	//	alert("success");
    	                      // });
    	}

    	}).responseText;
	
	document.getElementById("updateUserResults").style.display="none";
	//document.getElementById("updateForm").style.display="block";
	document.getElementById("updateUserResults").style.display="none";
    //document.getElementById("updateForm").style.display="none";
    //document.getElementById("deleteUserResults").style.display="none";
    document.getElementById("viewAllBedResults").style.display="none";
    document.getElementById("deleteBedResults").style.display="none";
	}

function deleteUser(id, b_name)
{
//	alert(id+", name--->  "+b_name);
//	alert("/deleteUser/" + b_name + "/" + id);
	var abc = $.ajax({
        dataType: "json",
        url: "/deleteUser/" + b_name + "/" + id,
        global: false,
        async: false,
        success: function(data) {
        	location.reload();
        	//alert("success");
        }
	}).responseText;
//	alert(abc);
	}


