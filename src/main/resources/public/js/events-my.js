/**
 * Created by Святослав on 11.03.2017.
 */
$(document).ready(function () {
    var load_more_my_future_events = "<div id=\"load-more-my-future-events\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><hr class=\"middle\"></div><div class=\"col-sm-12 load-more\"><a id=\"load-more-my-future-events-href\"><span id=\"plus\" class=\"glyphicon glyphicon-plus\"></span><span class=\"glyphicon glyphicon-menu-up hidden\"></span></a></div><div class=\"col-sm-12\"><hr class=\"middle\"></div></div></div>";
    var user_id = $("#ids").text();
    var from = 0;
    var flag = true;
    var special_alert_no_my_future_events = "<div id=\"special-alert-no-my-future-events\" class=\"appended-result\"><div class=\"row search-result\"><div class=\"col-sm-12\"><div id=\"no-more-posts\"><h5>THERE IS NO POSTS.</h5></div></div></div><hr class=\"middle\"></div>";
    var array = getFirstTen(user_id, "/get-my-future-events/", "#special-alert-no-my-future-events", "#my-events", load_more_my_future_events );
    $(document.body).on("click", "#load-more-my-future-events-href", function () {
        var array = getNextTen(user_id, );
        from = array[0];
    });
});