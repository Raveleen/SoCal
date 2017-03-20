/**
 * Created by Святослав on 11.03.2017.
 */
function concatEvent(array) {
    var string = "<div id=\"event-";
    string += array[0];
    string += "\" class=\"event row\"><div class=\"event-header row\"><div class=\"col-sm-12 header\"><h5>";
    string += array[7];
    string += "</h5>";
    if (array[5] === "0") {
        string += "<div class=\"delete-button-div\"><a class=\"event-delete-button\"><span class=\"glyphicon glyphicon-remove\"></span></a></div>";
    }
    string += "</div>";
    string += "<div class=\"col-sm-12 header-date\"><h5>";
    string += array[7];
    string += "</h5>";
    string += "<div class=\"col-sm-2\"><div>";
    if (array[6] === "-1") {
        string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/images/default-user-image.png\">";
    } else {
        string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/profile-image/";
        string += array[6];
        string += "\">";
    }
    string += "</div></div><div class=\"col-sm-10\"><div class=\"profile-usertitle-name-small\"><p class=\"user-name-small\"><a class=\"user-name\" href=\"/user/";
    string += array[1];
    string += "\">";
    string += array[2];
    string += "</a></p></div></div>";
    string += "</div></div><div class=\"event-body row\"><div class=\"col-sm-12 post-body-text-div\"><p class=\"event-body-text\">";
    string += array[8];
    string += "</p></br></div></div><div class=\"event-footer row\"></div></div></div></div>";
    return string;
}