/**
 * Created by Святослав on 11.01.2017.
 */
$(document).ready(function () {
    var input_a = 0;
    var input_b = 0;
    var input_c = 0;
    var input_d = 0;

    function signUpDisableToFalse() {
        if ((input_a + input_b + input_c + input_d) == 4) {
            $("#sign-up").prop("disabled", false);
        }
    }

    $("#sign-up").prop("disabled", true);
    $("#link-to-login").click(function () {
        $("#register-form").hide();
        $("#register-link-to-login").hide();
        $("#login-form").show();
        $("#login-link-to-register").show();
    });
    $("#link-to-register").click(function () {
        $("#login-form").hide();
        $("#login-link-to-register").hide();
        $("#register-form").removeClass("hidden");
        $("#register-link-to-login").removeClass("hidden");
        $("#register-form").show();
        $("#register-link-to-login").show();
    });
    $("#login-input").keyup(function () {
        $.get("login/validation?login=" + $("#login-input").val(), function (data) {
            $("#alert-login-too-short").hide();
            $("#alert-login-is-not-valid").hide();
            $("#alert-login-is-not-a-word").hide();
            $("#alert-login-is-valid").hide();
            $("#alert-login-too-short").removeClass("hidden");
            $("#alert-login-is-not-valid").removeClass("hidden");
            $("#alert-login-is-not-a-word").removeClass("hidden");
            $("#alert-login-is-valid").removeClass("hidden");
            if (data == "1") {
                input_a = 0;
                $("#sign-up").prop("disabled", true);
                $("#alert-login-too-short").show();
            }
            if (data == "2") {
                input_a = 0;
                $("#sign-up").prop("disabled", true);
                $("#alert-login-is-not-valid").show();
            }
            if (data == "3") {
                input_a = 0;
                $("#sign-up").prop("disabled", true);
                $("#alert-login-is-not-a-word").show();
            }
            if (data == "4") {
                input_a = 1;
                signUpDisableToFalse();
            }
        })
    });
    $("#email-input").keyup(function () {
        $("#alert-email-too-short").hide();
        $("#alert-email-too-short").removeClass("hidden");
        if ($("#email-input").val().length < 5) {
            input_b = 0;
            $("#sign-up").prop("disabled", true);
            $("#alert-email-too-short").show();
        } else {
            input_b = 1;
            signUpDisableToFalse();
        }
    });
    $("#phone-input").keyup(function () {
        $("#alert-phone-too-short").hide();
        $("#alert-phone-too-short").removeClass("hidden");
        if ($("#phone-input").val().length < 8) {
            input_c = 0;
            $("#sign-up").prop("disabled", true);
            $("#alert-phone-too-short").show();
        } else {
            input_c = 1;
            signUpDisableToFalse();
        }
    });
    $("#password-input").keyup(function () {
        $("#alert-password-too-short").hide();
        $("#alert-password-not-confirmed").hide();
        $("#alert-password-too-short").removeClass("hidden");
        $("#alert-password-not-confirmed").removeClass("hidden");
        if ($("#password-input").val().length < 6) {
            input_d = 0;
            $("#sign-up").prop("disabled", true);
            $("#alert-password-too-short").show();
        } else if ($("#password-input").val() != $("#password-confirm-input").val()) {
            input_d = 0;
            $("#sign-up").prop("disabled", true);
            $("#alert-password-not-confirmed").show();
        } else {
            input_d = 1;
            signUpDisableToFalse();
        }
    });
    $("#password-confirm-input").keyup(function () {
        $("#alert-password-too-short").hide();
        $("#alert-password-not-confirmed").hide();
        $("#alert-password-too-short").removeClass("hidden");
        $("#alert-password-not-confirmed").removeClass("hidden");
        if ($("#password-input").val().length < 6) {
            input_d = 0;
            $("#sign-up").prop("disabled", true);
            $("#alert-password-too-short").show();
        } else if ($("#password-input").val() != $("#password-confirm-input").val()) {
            input_d = 0;
            $("#sign-up").prop("disabled", true);
            $("#alert-password-not-confirmed").show();
        } else {
            input_d = 1;
            signUpDisableToFalse();
        }
    });
});