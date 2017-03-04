/**
 * Created by Святослав on 12.01.2017.
 */
$(document).on('change', ':file', function () {
    var input = $(this),
        numFiles = input.get(0).files ? input.get(0).files.length : 1,
        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
    input.trigger('fileselect', [numFiles, label]);
});
$(document).ready(function () {
    $("#create-post-button").prop("disabled", true);
    $(".create-comment-button").prop("disabled", true);
    var flag_file = 0;
    var flag_text = 0;
    //FIRST ACTIONS ON LOAD.
    $("#arrow-up").hide();
    $("#arrow-up").removeClass("hidden");
    $("#create-post-form-div").hide();
    $("#create-post-form-div").removeClass("hidden");
    //_Counter of symbols
    $("#post-text").keyup(function () {
        if ($("#post-text").val().length == 0) {
            flag_text = 0;
        }
        if ($("#post-text").val().length > 0) {
            flag_text = 1;
        }
        if ((flag_file + flag_text) == 2) {
            $("#create-post-button").prop("disabled", false);
        } else {
            $("#create-post-button").prop("disabled", true);
        }
        $("#characters-number").text(5000 - $("#post-text").val().length);
    });
    //_Arrow, which opens form
    $("#arrow-down").click(function () {
        if ($("#arrow-down").is(":visible")) {
            $("#arrow-down").hide();
            if ($("#arrow-up").is(":hidden")) {
                $("#arrow-up").show();
                $("#create-post-form-div").slideDown();
            }
        }
    });
    //_Arrow, which closes form
    $("#arrow-up").click(function () {
        if ($("#arrow-up").is(":visible")) {
            $("#arrow-up").hide();
            if ($("#arrow-down").is(":hidden")) {
                $("#arrow-down").show();
                $("#create-post-form-div").slideUp();
            }
        }
    });
    //_Controls file input
    $(':file').on('fileselect', function (event, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;
        if (numFiles == 0) {
            if (input.length) {
                input.val(log);
            } else {
                if (log) alert(log);
            }
            flag_file = 0;
            $("#create-post-button").prop("disabled", true);
        } else {
            if (input.length) {
                input.val(log);
            } else {
                if (log) alert(log);
            }
            flag_file = 1;
            if ((flag_file + flag_text) == 2) {
                $("#create-post-button").prop("disabled", false);
            }
        }

    });
});