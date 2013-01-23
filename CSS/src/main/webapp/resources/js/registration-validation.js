$(document).ready(function () {
	$("#loginErrorMessage").hide();
	$("#passwordErrorMessage").hide();
	$("#passwordConfirmErrorMessage").hide();
	$("#firsnameErrorMessage").hide();
	$("#lastnameErrorMessage").hide();
	
	$("#btnSignUp").click(function (e) {
		if (checkLogin() == false || 
			checkPassword() == false || 
			checkPasswordConfirm() == false || 
			checkFirstname() == false || 
			checkLastname() == false)
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
		$("#loginErrorMessage").html("Login is required.");
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