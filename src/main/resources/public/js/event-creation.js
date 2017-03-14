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
        if ($("#event-info").val().length > 0) {
            flag_info = 1;
        }
        if ((flag_title + flag_info) == 2) {
            $("#create-event-button").prop("disabled", false);
        } else {
            $("#create-event-button").prop("disabled", true);
        }
        $("#characters-number").text(1000 - $("#event-info").val().length);
    });
    $("#event-title").keyup(function () {
        if ($("#event-title").val().length == 0) {
            flag_title = 0;
        }
        if ($("#event-title").val().length > 0) {
            flag_title = 1;
        }
        if ((flag_info + flag_title) == 2) {
            $("#create-event-button").prop("disabled", false);
        } else {
            $("#create-event-button").prop("disabled", true);
        }
        $("#characters-title-number").text(30 - $("#event-title").val().length);
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
    $("#create-event-button").click(function () {
        var form = $('form')[0];
        var formData = new FormData(form);
        $.ajax({
            url: '/event-create',
            data: formData,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function (data) {
                $("#my-events-container").prepend(concatEvent(data[0]));
                $("#special-alert").remove();
                $("#posts-container").append('' + special_alert_no_more_posts);
                document.getElementById('create-event-form').reset();
                $("#characters-number").text(1000);
                $("#characters-title-number").text(1000);
                $("#create-event-button").prop("disabled", true);
            }
        })
    });
    $(document.body).on("click", ".delete-button", function () {
        var id = $(this).closest(".event").attr("id");
        $.ajax({
            url: '/event-delete/' + id,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function () {
                $("#" + id).remove();
            }
        })
    });
});