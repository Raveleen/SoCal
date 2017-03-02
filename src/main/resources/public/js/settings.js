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
    function inputLog(input, log) {
        if (input.length) {
            input.val(log);
        } else {
            if (log) alert(log);
        }
    }
    function disableUpdate(temp) {
        $("#update").prop("disabled", true);
        $(temp).show();
    }
    function enableUpdate() {
        if ((input_a + input_b + input_c) === 3) {
            $("#update").prop("disabled", false);
        }
    }
    function inputCheck(inputId, size, alert, varName) {
        hideAndRemoveHidden([alert]);
        if ($(inputId).val().length < size) {
            if (varName === "c") {
                input_c = 0;
            }
            if (varName === "b") {
                input_b = 0;
            }
            disableUpdate(alert);
        } else {
            if (varName === "c") {
                input_c = 1;
            }
            if (varName === "b") {
                input_b = 1;
            }
            enableUpdate();
        }
    }
    $("#upload").prop("disabled", true);
    document.getElementById("info").defaultValue = $("#usinfo").text();
    var input_a = 1;
    var input_b = 1;
    var input_c = 1;
    $("#update").prop("disabled", false);
    $("#login-input").keyup(function () {
        $.get("settings/validation?login=" + $("#login-input").val(), function (data) {
            hideAndRemoveHidden(["#alert-login-too-short","#alert-login-is-not-valid",
                "#alert-login-is-not-a-word","#alert-login-is-valid"]);
            if (data === "1") {
                input_a = 0;
                disableUpdate("#alert-login-too-short");
            }
            if (data === "2") {
                input_a = 1;
                enableUpdate()
            }
            if (data === "3") {
                input_a = 0;
                disableUpdate("#alert-login-is-not-valid");
            }
            if (data === "4") {
                input_a = 0;
                disableUpdate("#alert-login-is-not-a-word");
            }
            if (data === "5") {
                input_a = 1;
                enableUpdate();
            }
        })
    });
    $("#email-input").keyup(function () {
        inputCheck("#email-input", 5, "#alert-email-too-short", "b");
    });
    $("#phone-input").keyup(function () {
        inputCheck("#phone-input", 8, "#alert-phone-too-short", "c");
    });
    $("#info").keyup(function () {
        $("#characters-number").text(200 - $("#info").val().length);
    });
    $(':file').on('fileselect', function(event, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;

        if (numFiles === 0) {
            inputLog(input, log);
            $("#upload").prop("disabled", true);
        } else {
            inputLog(input, log);
            $("#upload").prop("disabled", false);
        }

    });
});