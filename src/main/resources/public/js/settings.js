/**
 * Created by Святослав on 17.01.2017.
 */
$(document).on('change', ':file', function() {
    var input = $(this),
        numFiles = input.get(0).files ? input.get(0).files.length : 1,
        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [numFiles, label]);
});
$(document).ready(function () {
    document.getElementById("info").defaultValue = $("#usinfo").text();
    var input_a = 1;
    var input_b = 1;
    var input_c = 1;
    $("#update").prop("disabled", false);
    $("#login-input").keyup(function () {
        $.get("settings/validation?login=" + $("#login-input").val(), function (data) {
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
                $("#update").prop("disabled", true);
                $("#alert-login-too-short").show();
            }
            if (data == "2") {
                input_a = 1;
                if ((input_a + input_b + input_c) == 3) {
                    $("#update").prop("disabled", false);
                }
            }
            if (data == "3") {
                input_a = 0;
                $("#update").prop("disabled", true);
                $("#alert-login-is-not-valid").show();
            }
            if (data == "4") {
                input_a = 0;
                $("#update").prop("disabled", true);
                $("#alert-login-is-not-a-word").show();
            }
            if (data == "5") {
                input_a = 1;
                if ((input_a + input_b + input_c) == 3) {
                    $("#update").prop("disabled", false);
                }
            }
        })
    });
    $("#email-input").keyup(function () {
        $("#alert-email-too-short").hide();
        $("#alert-email-too-short").removeClass("hidden");
        if ($("#email-input").val().length < 5) {
            input_b = 0;
            $("#update").prop("disabled", true);
            $("#alert-email-too-short").show();
        } else {
            input_b = 1;
            if ((input_a + input_b + input_c) == 3) {
                $("#update").prop("disabled", false);
            }
        }
    });
    $("#phone-input").keyup(function () {
        $("#alert-phone-too-short").hide();
        $("#alert-phone-too-short").removeClass("hidden");
        if ($("#phone-input").val().length < 8) {
            input_c = 0;
            $("#update").prop("disabled", true);
            $("#alert-phone-too-short").show();
        } else {
            input_c = 1;
            if ((input_a + input_b + input_c) == 3) {
                $("#update").prop("disabled", false);
            }
        }
    });
    $("#info").keyup(function () {
        $("#characters-number").text(200 - $("#info").val().length);
    });
    $(':file').on('fileselect', function(event, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;

        if( input.length ) {
            input.val(log);
        } else {
            if( log ) alert(log);
        }

    });
});