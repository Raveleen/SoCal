/**
 * Created by Святослав on 16.03.2017.
 */
$(document).ready(function () {
    $(document.body).on("click", ".awful", function () {
        var id = $(this).closest(".event").attr("id");
        $.ajax({
            url: '/event-rate/' + id + "/" + 1,
            type: 'GET',
            contentType: false,
            processData: false,
            success: function () {
                $("#" + id).find(".rate-buttons-block").remove();
            }
        })
    });
    $(document.body).on("click", ".could-be-worse", function () {
        var id = $(this).closest(".event").attr("id");
        $.ajax({
            url: '/event-rate/' + id + "/" + 2,
            type: 'GET',
            contentType: false,
            processData: false,
            success: function () {
                $("#" + id).find(".rate-buttons-block").remove();
            }
        })
    });
    $(document.body).on("click", ".pretty-good", function () {
        var id = $(this).closest(".event").attr("id");
        $.ajax({
            url: '/event-rate/' + id + "/" + 3,
            type: 'GET',
            contentType: false,
            processData: false,
            success: function () {
                $("#" + id).find(".rate-buttons-block").remove();
            }
        })
    });
    $(document.body).on("click", ".awesome", function () {
        var id = $(this).closest(".event").attr("id");
        $.ajax({
            url: '/event-rate/' + id + "/" + 4,
            type: 'GET',
            contentType: false,
            processData: false,
            success: function () {
                $("#" + id).find(".rate-buttons-block").remove();
            }
        })
    });
});