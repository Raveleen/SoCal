/**
 * Created by Святослав on 12.03.2017.
 */
$(document).ready(function () {
    $("#create-event-button").prop("disabled", true);
    var flag_title = 0;
    var flag_info = 0;
    var flag_date = 0;
    var flag_time = 0;
    var flag_placeId = 0;
    function varsCheck() {
        if ((flag_title + flag_info + flag_date + flag_time + flag_placeId) === 5) {
            $("#create-event-button").prop("disabled", false);
        } else {
            $("#create-event-button").prop("disabled", true);
        }
    }
    //FIRST ACTIONS ON LOAD.
    $("#arrow-up").hide();
    $("#arrow-up").removeClass("hidden");
    $("#create-event-form-div").hide();
    $("#create-event-form-div").removeClass("hidden");
    setTimeout(initMap(), 2000);
    //_Counter of symbols
    $("#event-info").keyup(function () {
        if ($("#event-info").val().length == 0) {
            flag_info = 0;
        }
        if ($("#event-info").val().length > 0) {
            flag_info = 1;
        }
        varsCheck();
        $("#characters-number").text(1000 - $("#event-info").val().length);
    });
    $("#event-title").keyup(function () {
        if ($("#event-title").val().length == 0) {
            flag_title = 0;
        }
        if ($("#event-title").val().length > 0) {
            flag_title = 1;
        }
        varsCheck();
        $("#characters-title-number").text(30 - $("#event-title").val().length);
    });
    $("#pac-input").keyup(function () {
        if ($("#pac-input").val().length == 0) {
            flag_placeId = 0;
        }
        if ($("#pac-input").val().length > 0) {
            flag_placeId = 1;
        }
        varsCheck();
        $("#characters-number").text(1000 - $("#event-info").val().length);
    });
    $("#date-input").change(function () {
        if ($("#date-input").val() < document.getElementById('date-input').min) {
            flag_date = 0;
        }
        if ($("#date-input").val() > document.getElementById('date-input').min) {
            flag_date = 1;
        }
        varsCheck();
    });
    $("#time-input").change(function () {
        if ($("#time-input").val() < document.getElementById('time-input').min) {
            flag_time = 0;
        }
        if ($("#time-input").val() > document.getElementById('time-input').min) {
            flag_time = 1;
        }
        varsCheck();
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
        var center = map.getCenter();
        google.maps.event.trigger(map, 'resize');
        map.setCenter(center);
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
                $("#future-events").prepend(concatEvent(data[0]));
                $("#special-alert-no-future-events").remove();
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
