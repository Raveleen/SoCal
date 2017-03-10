/**
 * Created by Святослав on 10.03.2017.
 */
function concatUsers(array) {
    var string = "<div id=\"";
    string += array[0];
    string += "\" class=\"search appended-result\"><div class=\"row search-result\"><div class=\"col-sm-2\"><div>";
    if (array[1] === "-1") {
        string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/images/default-user-image.png\">";
    } else {
        string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/profile-image/";
        string += array[1];
        string += "\">";
    }
    string += "</div></div><div class=\"col-sm-6\"><div class=\"profile-usertitle-small\"><div class=\"profile-usertitle-name-small\"><a class=\"user-name\" href=\"/user/";
    string += array[0];
    string += "\">";
    string += array[2];
    string += "</a></p></div></div></div><div class=\"col-sm-2\"><div class=\"profile-userbuttons\"><div class=\"calendar-href\"><a href=\"/calendar/";
    string += array[0];
    if (array[3] === "1") {
        string += "\"><button type=\"submit\" class=\"btn btn-primary btn-md btn-block button-calendar\"><span class=\"glyphicon glyphicon-th-large glyphicon\"></span></button></a></div></div></div><div class=\"col-sm-2\"><div class=\"message-href\" class=\"\"><button id=\"button-message\" type=\"submit\" class=\"btn btn-primary btn-md btn-block\" disabled><span class=\"glyphicon glyphicon-remove\"></span></button></div></div></div><hr class=\"middle\"></div>";
    } else {
        string += "\"><button type=\"submit\" class=\"btn btn-primary btn-md btn-block button-calendar\"><span class=\"glyphicon glyphicon-th-large glyphicon\"></span></button></a></div></div></div><div class=\"col-sm-2\"><div class=\"message-href\" class=\"\"><a href=\"/message-to/";
        string += array[0];
        string += "\"><button id=\"button-message\" type=\"submit\" class=\"btn btn-primary btn-md btn-block\"><span class=\"glyphicon glyphicon-envelope\"></span></button></a></div></div></div><hr class=\"middle\"></div>";
    }
    return string;
}