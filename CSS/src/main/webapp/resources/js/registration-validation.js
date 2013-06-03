/*
 * Copyright (c) 2013, RMIT University, Australia.
 * All rights reserved.
 * 
 * This code is under the BSD license. See 'license.txt' for details.
 * Project hosted at: https://code.google.com/p/climate-smart-seaports/
 */

$(document).ready(function () {
	$("#loginErrorMessage").hide();
	$("#passwordErrorMessage").hide();
	$("#passwordConfirmErrorMessage").hide();
	$("#firsnameErrorMessage").hide();
	$("#lastnameErrorMessage").hide();
	$("#emailErrorMessage").hide();
	
	$("#btnSignUp").click(function (e) {
		if (checkLogin() == false || 
			checkPassword() == false || 
			checkPasswordConfirm() == false || 
			checkFirstname() == false || 
			checkLastname() == false || 
			checkEmail() == false)
			e.preventDefault();
	});
});

function checkLogin() {
	if ($("#txtLogin").val().length > 0) {
		$("#txtLogin").removeClass("error");
		$("#loginErrorMessage").hide();
		return true;
	}
	else {
		$("#loginErrorMessage").html("Username is required.");
	}
	$("#txtLogin").addClass("error");
	$("#loginErrorMessage").show();
	return false;
}

function checkPassword() {
	var password = $("#txtPassword").val();
	
	if (password.length >= 5) {
		$("#txtPassword").removeClass("error");
		$("#passwordErrorMessage").hide();
		return true;
	}
	else if (password.length == 0) {
		$("#passwordErrorMessage").html("Password is required.");
	}
	else {
		$("#passwordErrorMessage").html("Password must be at least 5 characters.");
	}
	$("#txtPassword").addClass("error");
	$("#passwordErrorMessage").show();
	return false;
}

function checkPasswordConfirm() {
	var passwordConfirm = $("#txtConfirmPassword").val();
	
	if (passwordConfirm.length == 0) {
		$("#passwordConfirmErrorMessage").html("Password confirmation is required.");
	}
	else if (passwordConfirm == $("#txtPassword").val()) {
		$("#txtConfirmPassword").removeClass("error");
		$("#passwordConfirmErrorMessage").hide();
		return true;
	}
	else {
		$("#passwordConfirmErrorMessage").html("Passwords do not match.");
	}
	$("#txtConfirmPassword").addClass("error");
	$("#passwordConfirmErrorMessage").show();
	return false;
}

function checkFirstname() {
	if ($("#txtFirstname").val().length > 0) {
		$("#txtFirstname").removeClass("error");
		$("#firstnameErrorMessage").hide();
		return true;
	}
	else {
		$("#firstnameErrorMessage").html("First name is required.");
	}
	$("#txtFirstname").addClass("error");
	$("#firstnameErrorMessage").show();
	return false;
}

function checkLastname() {
	if ($("#txtLastname").val().length > 0) {
		$("#txtLastname").removeClass("error");
		$("#lastnameErrorMessage").hide();
		return true;
	}
	else {
		$("#lastnameErrorMessage").html("Last name is required.");
	}
	$("#txtLastname").addClass("error");
	$("#lastnameErrorMessage").show();
	return false;
}

function checkEmail() {
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if ($("#txtEmail").val().length > 0) {
		if (filter.test($("#txtEmail").val())) {
			$("#txtEmail").removeClass("error");
			$("#emailErrorMessage").hide();
			return true;
		}
		else {			$("#emailErrorMessage").html("Please enter a valid e-mail address.");
		}
	}
	else {
		$("#emailErrorMessage").html("E-mail address is required.");
	}
	$("#txtEmail").addClass("error");
	$("#emailErrorMessage").show();
	return false;
}