/**
 * Created by Святослав on 04.03.2017.
 */
$(document).ready(function () {
    var user_id = $("#ids").text();
    var from = 0;
    var flag = true;
    var special_alert_no_more_posts = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-posts\"><h5>THERE IS NO MORE POSTS.</h5></div></div></div><hr class=\"middle\"></div>";
    var special_alert_no_posts = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-posts\"><h5>THERE IS NO POSTS.</h5></div></div></div><hr class=\"middle\"></div>";
    $.get("/get-posts/" + user_id + "/0", function (data) {
        var i = 0;
        if (data[0][0] === null) {
            $("#special-alert").remove();
            $("#posts-container").append('' + special_alert_no_posts);
        } else {
            while (i < data.length) {
                $("#posts-container").append('' + concatPost(data[i]));
                if ((i === data.length - 1 ) && (data.length < 10)) {
                    $("#special-alert").remove();
                    $("#posts-container").append('' + special_alert_no_more_posts);
                }
                i++;
            }
        }
    });
    $(window).scroll(function () {
        if (($(window).scrollTop() + $(window).height() > $(document).height() - 50) && (flag == true)) {
            from += 10;
            $.get("/get-posts/" + user_id + "/" + from, function (data) {
                var i = 0;
                if (data[0][0] === null) {
                    flag = false;
                    $("#special-alert").remove();
                    $("#posts-container").append('' + special_alert_no_more_posts);
                } else {
                    while (i < data.length) {
                        $("#posts-container").append('' + concatPost(data[i]));
                        if ((i == data.length - 1 ) && (data.length < 10)) {
                            $("#special-alert").remove();
                            $("#posts-container").append('' + special_alert_no_more_posts);
                            flag = false;
                        }
                        i++;
                    }
                }
            });
        }
    });
    $("#create-post-button").click(function () {
        var form = $('form')[0];
        var formData = new FormData(form);
        $.ajax({
            url: '/post-create',
            data: formData,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function (data) {
                $("#posts-container").prepend(concatPost(data[0]));
                $("#special-alert").remove();
                $("#posts-container").append('' + special_alert_no_more_posts);
                document.getElementById('create-post-form').reset();
                $("#characters-number").text(5000);
                from += 1;
                flag_file = 0;
                flag_text = 0;
                $("#create-post-button").prop("disabled", true);
            }
        })
    });
    $(document.body).on("click", ".delete-button", function () {
        var id = $(this).closest(".post").attr("id");
        $.ajax({
            url: '/post-delete/' + id,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function () {
                $("#" + id).remove();
                from -= 1;
            }
        })
    });
});