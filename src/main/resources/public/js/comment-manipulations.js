/**
 * Created by Святослав on 04.03.2017.
 */
$(document).ready(function () {
    var load_more_comments = "<div id=\"load-more-comments\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><hr class=\"middle\"></div><div class=\"col-sm-12 load-more\"><a id=\"load-comments-href\"><span id=\"plus\" class=\"glyphicon glyphicon-plus\"></span><span class=\"glyphicon glyphicon-menu-up hidden\"></span></a></div><div class=\"col-sm-12\"><hr class=\"middle\"></div></div></div>";
    var comments_from = 0;
    var comments_flag = true;
    function appender(data, b) {
        var i = 0;
        if (data[0] !== null) {
            while (i < data.length) {
                b.prepend('' + data[i]);
                if ((i === data.length - 1 ) && (data.length < 10)) {
                } else if ((i === data.length - 1 ) && (data.length === 10)) {
                    b.prepend('' + load_more_comments);
                }
                i++;
            }
        }
    }
    $(".create-comment-button").prop("disabled", true);
    $(document.body).on("click", ".comment-button", function () {
        if($(this).closest(".post").find(".comment-text").val().length === 0) {
            $(this).closest(".post").find(".create-comment-button").prop("disabled", true);
        } else {
            $(this).closest(".post").find(".create-comment-button").prop("disabled", false);
        }
        comments_from = 0;
        comments_flag = true;
        var id = $(this).closest(".post").attr("id");
        $(".comment").remove();
        $(".post-comments").hide();
        var a = $("#" + id).find(".post-comments");
        a.removeClass("hidden");
        a.show();
        var b = a.find(".comment-container");
        $.ajax({
            url: '/get-comments/' + id + '/0',
            type: 'GET',
            contentType: false,
            processData: false,
            success: function(data) {
                appender(data, b);
            }
        })
    });
    $(document.body).on("click", ".delete-button-comment", function () {
        var comment_id = $(this).closest(".comment").attr("id");
        var id = $(this).closest(".post").attr("id");
        $.ajax({
            url: '/comment-delete/' + comment_id,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function() {
                $("#" + comment_id).remove();
                comments_from -= 1;
                var temp = parseInt($("#" + id).find(".comments-number").text(), 10);
                $("#" + id).find(".comments-number").text(temp - 1);
            }
        })
    });
    $(document.body).on("click", ".create-comment-button", function () {
        $(this).closest(".post").find(".create-comment-button").prop("disabled", true);
        var id = $(this).closest(".post").attr("id");
        var a = $("#" + id).find(".post-comments");
        var b = a.find(".comment-container");
        var c = document.getElementById('form-' + id);
        var formData = new FormData(c);
        $.ajax({
            url: '/comment-create/' + id,
            data: formData,
            type: 'POST',
            contentType: false,
            processData: false,
            success: function(data) {
                b.append(data);
                document.getElementById('form-' + id).reset();
                $(this).prop("disabled", true);
                comments_from += 1;
                var temp = parseInt($("#" + id).find(".comments-number").text(), 10);
                $("#" + id).find(".comments-number").text(temp + 1);
            }
        })
    });
    $(document.body).on("click", "#load-comments-href", function () {
        comments_from += 10;
        var id = $(this).closest(".post").attr("id");
        var a = $("#" + id).find(".post-comments");
        var b = a.find(".comment-container");
        $("#load-more-comments").remove();
        $.ajax({
            url: '/get-comments/' + id + '/' + comments_from,
            type: 'GET',
            contentType: false,
            processData: false,
            success: function(data) {
                appender(data, b);
            }
        })
    });
    $(document.body).on("keyup", ".comment-text", function () {
        var id = $(this).closest(".post").attr("id");
        var a = $("#" + id).find(".create-comment-button");
        if($(this).val().length === 0) {
            a.prop("disabled", true);
        } else {
            a.prop("disabled", false);
        }
    });
});