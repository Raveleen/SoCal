/**
 * Created by Святослав on 18.01.2017.
 */
$(document).ready(function () {
    var special_alert_1 = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-users\"><h5>THERE IS NO MORE USERS WITH SUCH LOGIN.</h5></div></div></div><hr class=\"middle\"></div>";
    var special_alert_2 = "<div id=\"special-alert\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-users\"><h5>THERE IS NO USERS WITH SUCH LOGIN.</h5></div></div></div><hr class=\"middle\"></div>";
    var flag = true;
    var from = 0;
    var temp_input_length = 0;
    var temp = "<div id=\"";
    var temp_1 = "\" class=\"search appended-result\"><div class=\"row search-result\"><div class=\"col-sm-2\"><div>";
    var temp_2 = "<img class=\"profile-userpic-small centered-and-cropped\" src=\"images/default-user-image.png\">";
    var temp_2_1 = "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/profile-image/";
    var temp_2_1_0 = "\">";
    var temp_3 = "</div></div><div class=\"col-sm-6\"><div class=\"profile-usertitle-small\"><div class=\"profile-usertitle-name-small\"><p class=\"user-name\"><a class=\"user-name\" href=\"/user/";
    var temp_3_1 = "\">";
    var temp_4_1 = "</a></p></div></div></div><div class=\"col-sm-2\"><div class=\"profile-userbuttons\"><div class=\"calendar-href\"><a href=\"/calendar/";
    var temp_4_1_2 = "\"><button type=\"submit\" class=\"btn btn-primary btn-md btn-block button-calendar\"><span class=\"glyphicon glyphicon-th-large glyphicon\"></span></button></a></div></div></div><div class=\"col-sm-2\"><div class=\"message-href\" class=\"\"><a href=\"/message-to/";
    var temp_4_1_3 = "\"><button id=\"button-message\" type=\"submit\" class=\"btn btn-primary btn-md btn-block\"><span class=\"glyphicon glyphicon-envelope\"></span></button></a></div></div></div><hr class=\"middle\"></div>";
    var temp_4_2 = "</a></p></div></div></div><div class=\"col-sm-2\"><div class=\"profile-userbuttons\"><div class=\"calendar-href\"><a href=\"/calendar/";
    var temp_4_2_2 = "\"><button type=\"submit\" class=\"btn btn-primary btn-md btn-block button-calendar\"><span class=\"glyphicon glyphicon-th-large glyphicon\"></span></button></a></div></div></div><div class=\"col-sm-2\"><div class=\"message-href\" class=\"\"><button id=\"button-message\" type=\"submit\" class=\"btn btn-primary btn-md btn-block\" disabled><span class=\"glyphicon glyphicon-remove\"></span></button></div></div></div><hr class=\"middle\"></div>";
    $("#input-search").keyup(function () {
        if ($("#input-search").val().length != temp_input_length) {
            $(".appended-result").remove();
            if (temp_input_length > $("#input-search").val().length) {
                temp_input_length -= 1;
            } else {
                temp_input_length += 1;
            }
            if ($("#input-search").val().length > 0) {
                $.get("/search/" + $("#input-search").val() + "/0", function (data) {
                    flag = true;
                    var local_flag = true;
                    var i = 0;
                    var array = data;
                    while ((local_flag == true) && (i < 10)) {
                        var login = array[i][0];
                        var user_id = array[i][1];
                        var photo_id = array[i][2];
                        if ((login != null) && (user_id != null) && (photo_id != null)) {
                            temp = temp + user_id + temp_1;
                            if (photo_id == "-1") {
                                temp = temp + temp_2;
                            } else {
                                temp = temp + temp_2_1 + photo_id + temp_2_1_0;
                            }
                            temp = temp + temp_3 + user_id + temp_3_1 + login;
                            if (user_id == $("#ids").text()) {
                                temp = temp + temp_4_2 + $("#ids").text() + temp_4_2_2;
                                $("#search-append").append('' + temp);
                            } else {
                                temp = temp + temp_4_1 + user_id + temp_4_1_2 + user_id + temp_4_1_3;
                                $("#search-append").append('' + temp);
                            }
                            temp = "<div id=\"";
                        } else {
                            local_flag = false;
                            if (document.getElementById("#special-alert") != null) {
                                $("#special-alert").remove();
                                if ( i == 0 ) {
                                    $("#search-append").append('' + special_alert_2);
                                } else {
                                    $("#search-append").append('' + special_alert_1);
                                }
                            } else {
                                $("#special-alert").remove();
                                if ( i == 0 ) {
                                    $("#search-append").append('' + special_alert_2);
                                } else {
                                    $("#search-append").append('' + special_alert_1);
                                }
                            }
                        }
                        i++;
                    }
                });
            }
        }
    });
    $(window).scroll(function () {
        if (($(window).scrollTop() + $(window).height() > $(document).height() - 50) && (flag == true)) {
            from += 10;
            $.get("/search/" + $("#input-search").val() + "/" + from, function (data) {
                    var local_flag = true;
                    var i = 0;
                    var array = data;
                    while ((local_flag == true) && (i < 10)) {
                        var login = array[i][0];
                        var user_id = array[i][1];
                        var photo_id = array[i][2];
                        i++;
                        if ((login != null) && (user_id != null) && (photo_id != null)) {
                            temp = temp + user_id + temp_1;
                            if (photo_id == "-1") {
                                temp = temp + temp_2;
                            } else {
                                temp = temp + temp_2_1 + photo_id + temp_2_1_0;
                            }
                            temp = temp + temp_3 + user_id + temp_3_1 + login;
                            if (user_id == $("#ids").text()) {
                                temp = temp + temp_4_2 + $("#ids").text() + temp_4_2_2;
                                $("#search-append").append('' + temp);
                            } else {
                                temp = temp + temp_4_1 + user_id + temp_4_1_2 + user_id + temp_4_1_3;
                                $("#search-append").append('' + temp);
                            }
                            temp = "<div id=\"";
                        } else {
                            local_flag = false;
                            flag = false;
                            from = 0;
                            if (document.getElementById("#special-alert") != null) {

                            } else {
                                $("#special-alert").remove();
                                $("#search-append").append('' + special_alert_1);
                            }
                        }
                    }
                }
            );
        }
    });
});