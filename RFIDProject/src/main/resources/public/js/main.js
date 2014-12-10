var a = [];



function createAccount() {
  //  alert("Creating Account....");
}

function resetForm() {
 //   alert("Resetting Form");
    document.getElementById("registrationForm").reset();
}

function searchUsers() {
 //   alert("Searching for Users");
    var fname = document.getElementById('fName').value;
    var lname = document.getElementById('lName').value;
    //document.getElementById("page_About").style.display="none";
  //  alert("Fname---> " + fname + "  Lname---> " + lname);

    // $.get('/userDetails/'+fname+'/'+lname)
    //.done(function( data ) {
    //  alert( "Data Loaded: " + data );
    // alert(data[0]._id);
    // });

    var abc = $.ajax({
        dataType: "json",
        url: "/userDetails/" + fname + '/' + lname,
        global: false,
        async: false,
        success: function(data) {
            for (var i = 0; i < data.length; i++) {
                a[i] = data[i]._id;
            }

            $.each(data, function(idx, elem) {
                $('table#searchTable TBODY').append('<tr><td>' + elem._id + '</td><td>' + elem.firstName + '</td><td>' + elem.lastName + '</td><td>' + elem.ssn + '</td><td>' + elem.gender + '</td><td>' + elem.state + '</td><td>' + elem.city + '</td><td>' + elem.zipCode + '</td><td>' + elem.phone + '</td><td>' + elem.email + '</td><td>' + elem.tagNo + '</td><td>' + elem.bedId + '</td></tr>');
            });


            //b=data[1]._id;
       //     alert("success");
            // });
        }

    }).responseText;
  //  alert(abc);



  //  alert(a);
    document.getElementById("searchResults").style.display = "block";
    document.getElementById("searchCallBack").style.display = "block";
    document.getElementById("searchForm").style.display = "none";



    //alert(b);
    //$(document).ajaxSuccess(function(){
    //  alert("AJAX request successfully completed");
    //});
}



function searchCallBack() {
   // alert("Back to searching!!!");
    document.getElementById("searchResults").style.display = "none";
    document.getElementById("searchCallBack").style.display = "none";
    document.getElementById("searchForm").style.display = "block";
    document.getElementById("searchForm").reset();
}

function viewAllUsers() {
  //  alert("Loading All Users....Whoaaaa!!!");
	document.getElementById("viewAllResults").style.display="block";
    var abc = $.ajax({
        dataType: "json",
        url: "/viewAllUsers",
        global: false,
        async: false,
        success: function(data) {
            $.each(data, function(idx, elem) {
                $('table#viewAllTable TBODY').append('<tr><td>' + elem._id + '</td><td>' + elem.firstName + '</td><td>' + elem.lastName + '</td><td>' + elem.ssn + '</td><td>' + elem.gender + '</td><td>' + elem.state + '</td><td>' + elem.city + '</td><td>' + elem.zipCode + '</td><td>' + elem.phone + '</td><td>' + elem.email + '</td><td>' + elem.tagNo + '</td><td>' + elem.bedId + '</td></tr>');
            });
         //   alert("success");
        }

    }).responseText;
    document.getElementById("updateUserResults").style.display="none";
    document.getElementById("updateForm").style.display="none";
    document.getElementById("deleteUsersResults").style.display="none";
    document.getElementById("viewAllBedResults").style.display="none";
    document.getElementById("deleteBedResults").style.display="none";
}


//function view()
//{
//	var abc = $.ajax({
//        dataType: "json",
//        url: "/viewAllUsers",
//        global: false,
//        async: false,
//        success: function(data) {
//            $.each(data, function(idx, elem) {
//                $('table#viewAllTable TBODY').append('<tr><td>' + elem._id + '</td><td>' + elem.firstName + '</td><td>' + elem.lastName + '</td><td>' + elem.ssn + '</td><td>' + elem.gender + '</td><td>' + elem.state + '</td><td>' + elem.city + '</td><td>' + elem.zipCode + '</td><td>' + elem.phone + '</td><td>' + elem.email + '</td><td>' + elem.tagNo + '</td><td>' + elem.bedId + '</td></tr>');
//            });
//         //   alert("success");
//        }
//
//    }).responseText;
//	
//   // alert("Loading All Beds....Whoaaaa!!!");
//    var abcd = $.ajax({
//        dataType: "json",
//        url: "/viewAllBeds",
//        global: false,
//        async: false,
//        success: function(data) {
//            $.each(data, function(idx, elem) {
//                $('table#viewAllTable TBODY').append('<tr><td>' + elem._id + '</td><td>' + elem.status + '</td></tr>');
//            });
//           // alert("success");
//        }
//
//    }).responseText;
//}