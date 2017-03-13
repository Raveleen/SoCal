/**
 * Created by Святослав on 12.03.2017.
 */
$(document).ready(function () {
    $("#create-event-button").prop("disabled", true);
    var flag_title = 0;
    var flag_info = 0;
    var flag_date = 0;
    var flag_address = 0;
    //FIRST ACTIONS ON LOAD.
    $("#arrow-up").hide();
    $("#arrow-up").removeClass("hidden");
    $("#create-event-form-div").hide();
    $("#create-event-form-div").removeClass("hidden");
    //_Counter of symbols
    $("#event-info").keyup(function () {
        if ($("#event-info").val().length == 0) {
            flag_info = 0;
        }
        if ($("#event_info").val().length > 0) {
            flag_info = 1;
        }
        if ((flag_info + flag_text) == 2) {
            $("#create-event-button").prop("disabled", false);
        } else {
            $("#create-event-button").prop("disabled", true);
        }
        $("#characters-number").text(5000 - $("#event-info").val().length);
    });
    //_Arrow, which opens form
    $("#arrow-down").click(function () {
        if ($("#arrow-down").is(":visible")) {
            $("#arrow-down").hide();
            if ($("#arrow-up").is(":hidden")) {
                $("#arrow-up").show();
                $("#create-event-form-div").slideDown();
            }
        }
    });
    //_Arrow, which closes form
    $("#arrow-up").click(function () {
        if ($("#arrow-up").is(":visible")) {
            $("#arrow-up").hide();
            if ($("#arrow-down").is(":hidden")) {
                $("#arrow-down").show();
                $("#create-event-form-div").slideUp();
            }
        }
    });
});