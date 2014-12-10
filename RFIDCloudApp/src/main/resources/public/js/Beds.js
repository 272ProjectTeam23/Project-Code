function viewAllBeds() {
//    alert("Loading All Beds....Whoaaaa!!!");
    var abc = $.ajax({
        dataType: "json",
        url: "/viewAllBeds",
        global: false,
        async: false,
        success: function(data) {
            $.each(data, function(idx, elem) {
                $('table#viewAllBedsTable TBODY').append('<tr><td>' + elem._id + '</td><td>' + elem.status + '</td></tr>');
            });
       //     alert("success");
        }

    }).responseText;
    document.getElementById("viewAllBedResults").style.display="block";
    document.getElementById("deleteBedResults").style.display="none";
}

function deleteBeds()
{
//	alert("Deleting.....");
//	alert(document.getElementById("viewAllBedResults"));
	document.getElementById("viewAllBedResults").style.display="none";
//	document.getElementById("updateForm").style.display="none";
	document.getElementById("deleteBedResults").style.display="block";
	
   // document.getElementById("viewAllBedResults").style.display="none";
   // document.getElementById("deleteBedResults").style.display="none";
	var abc= $.ajax({
    	dataType: "json",
    	url: "/viewAllBeds",
    	global: false,
        async:false,
    	success: function(data){
    			for(var i=0;i<data.length;i++)
    			{
    				a[i]=data[i]._id;
    			}
    			
    			$.each(data, function(idx, elem){
    	    	    $('table#deleteBedTable TBODY').append('<tr><td>'+elem._id+'</td><td>'+elem.status+'</td><td><input type="button" class="mybutton" id="'+elem._id +'" value="Delete" onclick="deleteBed(this.id)"></input></td></tr>');
    	    	    });
    			
    			
    			
    			
    	    //b=data[1]._id;
    //		alert("success");
    	                      // });
    	}

    	}).responseText;
	document.getElementById("viewAllResults").style.display="none";
	document.getElementById("updateUserResults").style.display="none";
	document.getElementById("updateForm").style.display="none";
	document.getElementById("updateUserResults").style.display="none";
    //document.getElementById("updateForm").style.display="none";
    document.getElementById("deleteUserResults").style.display="none";
	}

function deleteBed(id)
{
//	alert(id);
//	alert("/deleteBed/" + id);
	var abc = $.ajax({
        dataType: "json",
        url: "/deleteBed/" + id,
        global: false,
        async: false,
        success: function(data) {
        //	alert("success");
        	location.reload();
        }
	}).responseText;
//	alert(abc);
	}

function addBeds()
{
//	alert("Adding Beds");
	var abc = $.ajax({
		type: 'POST',
		dataType: "json",
    	url: "/addBed",
    	global: false,
        async:false,
    	success: function(data){
        	//alert("success");
        	location.reload();
        }
	}).responseText;
//	alert(abc);
	//document.getElementById("viewAllBedResults").style.display="none";
	
}

