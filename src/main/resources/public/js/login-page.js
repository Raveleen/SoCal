/**
 * Created by Святослав on 11.01.2017.
 */
$(document).ready(function () {
    var input_a = 0;
    var input_b = 0;
    var input_c = 0;
    var input_d = 0;

    function signUpDisableToFalse() {
        if ((input_a + input_b + input_c + input_d) === 4) {
            $("#sign-up").prop("disabled", false);
        }
    }

    function signUpDisableToTrueAndShowElement(elementID) {
        $("#sign-up").prop("disabled", true);
        $(elementID).show();
    }

    function hideAndRemoveHidden(elementsIDs) {
        for (var i = 0; i < elementsIDs.length; i++) {
            $(elementsIDs[i]).hide();
            $(elementsIDs[i]).removeClass("hidden");
        }
    }

    function passwordConfirmation() {
        var alerts = ["#alert-password-too-short", "#alert-password-not-confirmed"]
        hideAndRemoveHidden(alerts);
        if ($("#password-input").val().length < 6) {
            input_d = 0;
            signUpDisableToTrueAndShowElement(alerts[0]);
        } else if ($("#password-input").val() !== $("#password-confirm-input").val()) {
            input_d = 0;
            signUpDisableToTrueAndShowElement(alerts[1]);
        } else {
            input_d = 1;
            signUpDisableToFalse();
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
            var alerts = ["#alert-login-too-short", "#alert-login-is-not-valid", "#alert-login-is-not-a-word", "#alert-login-is-valid"];
            hideAndRemoveHidden(alerts);
            if (data === "1") {
                input_a = 0;
                signUpDisableToTrueAndShowElement("#alert-login-too-short");
            }
            if (data === "2") {
                input_a = 0;
                signUpDisableToTrueAndShowElement("#alert-login-is-not-valid");
            }
            if (data === "3") {
                input_a = 0;
                signUpDisableToTrueAndShowElement("#alert-login-is-not-a-word");
            }
            if (data === "4") {
                input_a = 1;
                signUpDisableToFalse();
            }
        })
    });
    $("#email-input").keyup(function () {
        hideAndRemoveHidden(["#alert-email-too-short"]);
        if ($("#email-input").val().length < 5) {
            input_b = 0;
            signUpDisableToTrueAndShowElement("#alert-email-too-short");
        } else {
            input_b = 1;
            signUpDisableToFalse();
        }
    });
    $("#phone-input").keyup(function () {
        hideAndRemoveHidden(["#alert-phone-too-short"]);
        if ($("#phone-input").val().length < 8) {
            input_c = 0;
            signUpDisableToTrueAndShowElement("#alert-phone-too-short");
        } else {
            input_c = 1;
            signUpDisableToFalse();
        }
    });
    $("#password-input").keyup(function () {
        passwordConfirmation();
    });
    $("#password-confirm-input").keyup(function () {
        passwordConfirmation();
    });
});