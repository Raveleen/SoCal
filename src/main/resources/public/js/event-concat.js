/**
 * Created by Святослав on 11.03.2017.
 */
function concatEvent(array) {
    var string = "<div id=\"event-";
    string += array[0];
    string += "\" class=\"event row\"><div class=\"event-header row\"><div class=\"col-sm-6 left-m\"><div class=\"col-sm-12 header\"><h5>";
    string += array[7];
    string += "</h5>";
    string += "</div>";
    string += "<div class=\"col-sm-12 header-date\"><h5>";
    string += array[3];
    string += "</h5>";
    string += "</div></div><div class=\"col-sm-6 user-char\"><div class=\"col-sm-10\"><div class=\"profile-usertitle-name-small\"><p class=\"user-name-small\"><a class=\"user-name\" href=\"/user/";
    string += array[1];
    string += "\">";
    string += array[2];
    string += "</a></p></div></div>";
    string += "<div class=\"col-sm-2\">";
    if (array[6] === "-1") {
        string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/images/default-user-image.png\">";
    } else {
        string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/profile-image/";
        string += array[6];
        string += "\">";
    }
    string += "</div></div></div>";
    if (array[10] === "-1") {
        string += "<div class=\"row\"><div class=\"col-sm-6 profile-usertitle-left\"><p>Number of potential visitors:</p>";
        string += "<p class=\"visitors\">";
        string += array[9];
        string += "</p></a></div><div class=\"col-sm-6 profile-usertitle-right\"><p>Average user rating:</p><p class=\"mark\">N/A</p></a></div></div>";
    } else {
        string += "<div class=\"row\"><div class=\"col-sm-6 profile-usertitle-left\"><p>Number of potential visitors:</p>";
        string += "<p class=\"visitors\">";
        string += array[9];
        string += "</p></a></div><div class=\"col-sm-6 profile-usertitle-right\"><p>Average user rating:</p><p class=\"mark\">"
        string += array[10];
        string += "</p></a></div></div>";
    }
    string += "<div class=\"event-body row\"><div class=\"col-sm-12 event-body-text-div\"><p class=\"post-body-text\">";
    string += array[8];
    string += "</p></br></div></div><div class=\"event-footer row\">";
    if (array[5] === "0") {
        //IF YOU ARE AUTHOR _ DELETE FUNCTION
        string += "<div class=\"delete-button-div\"><a class=\"event-delete-button\"><span class=\"glyphicon glyphicon-remove\"></span></a></div>";
    } else if (array[5] === "1") {
        if (array[10] === "-1") {
            //IF EVENT IS GOING TO HAPPEN
            //attend event button
            string += "<div class=\"col-sm-12\"><div class=\"attend-event-button-div\"><button type=\"button\" id=\"event-attend-";
            string += array[0];
            string += "\" class=\"attend-event-button btn btn-primary btn-md btn-block\">Attend an event</button></div></div>";
        } else {

        }
    } else if (array[5] === "2") {
        string += "<div class=\"delete-button-div\"><a class=\"event-delete-button\"><span class=\"glyphicon glyphicon-remove\"></span></a></div>";
    } else if (array[5] === "3") {
    }
    string += "</div>";
    string += "</div></div></div></div>";
    return string;
}