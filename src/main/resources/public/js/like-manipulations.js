/**
 * Created by Святослав on 04.03.2017.
 */
$(document).ready(function () {
    $(document.body).on("click", ".like-button", function () {
        var id = $(this).closest(".post").attr("id");
        var a = $("#" + id).find(".like-button-div");
        var b = a.find(".like-button");
        var c = a.find(".unlike-button");
        var d = a.find(".likes-number");
        $.ajax({
            url: '/like/' + id,
            type: 'GET',
            contentType: false,
            processData: false,
            success: function() {
                b.hide();
                c.removeClass("hidden");
                c.show();
                $.ajax({
                    url: '/number-of-likes/' + id,
                    type: 'GET',
                    success: function (data) {
                        d.text(data);
                    }
                })
            }
        })
    });
    $(document.body).on("click", ".unlike-button", function () {
        var id = $(this).closest(".post").attr("id");
        $.ajax({
            url: '/unlike/' + id,
            type: 'GET',
            contentType: false,
            processData: false,
            success: function() {
                var a = $("#" + id).find(".like-button-div");
                a.find(".unlike-button").hide();
                a.find(".unlike-button").removeClass("hidden");
                a.find(".like-button").show();
                $.ajax({
                    url: '/number-of-likes/' + id,
                    type: 'GET',
                    success: function (data) {
                        a.find(".likes-number").text(data);
                    }
                })
            }
        })
    });
});