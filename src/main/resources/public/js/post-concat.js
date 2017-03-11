/**
 * Created by Святослав on 09.03.2017.
 */
function concatPost(array) {
    var string = "<div id=\"";
    string += array[0];
    string += "\" class=\"post row\"><div class=\"post-header row\"><div class=\"col-sm-2\"><div>";
    if (array[1] === "-1") {
        string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/images/default-user-image.png\">";
    } else {
        string += "<img class=\"profile-userpic-small centered-and-cropped\" src=\"/profile-image/";
        string += array[1];
        string += "\">";
    }
    string += "</div></div><div class=\"col-sm-9\"><div class=\"profile-usertitle-small\"><div class=\"profile-usertitle-name-small\"><p class=\"user-name\"><a class=\"user-name\" href=\"/user/";
    string += array[2];
    string += "\">";
    string += array[3];
    string += "</a></p></div></div></div><div class=\"col-sm-1\">";
    if (array[10] === "1") {
        string += "<div class=\"delete-button-div\"><a class=\"delete-button\"><span class=\"glyphicon glyphicon-remove\"></span></a></div>";
    }
    string += "</div></div><div class=\"post-body row\"><div class=\"col-sm-12 post-body-img-div\"><img class=\"post-userpic centered-and-cropped\" src=\"/image/";
    string += array[4];
    string += "\"></div><div class=\"col-sm-12 post-body-text-div\"><p class=\"post-body-text\">";
    string += array[5];
    string += "</p></br><p class=\"post-body-date\">";
    string += array[6];
    string += "</p></div></div><div class=\"post-footer row\">";
    if (array[7] === "true") {
        string += "<div class=\"col-sm-6\"><div class=\"like-button-div\"><a class=\"like-button hidden\"><span class=\"glyphicon glyphicon-heart\"></span></a><a class=\"unlike-button\"><span class=\"glyphicon glyphicon-heart\"></span> </a><a href=\"/post-likes/";
        string += array[0];
        string += "\"><span class=\"likes-number\">";
    } else {
        string += "<div class=\"col-sm-6\"><div class=\"like-button-div\"><a class=\"like-button\"><span class=\"glyphicon glyphicon-heart\"></span></a><a class=\"unlike-button hidden\"><span class=\"glyphicon glyphicon-heart\"></span></a><a href=\"/post-likes/";
        string += array[0];
        string += "\"><span class=\"likes-number\">";
    }
    string += array[8];
    string += "</span></a></div></div><div class=\"col-sm-6\"><div class=\"comment-quantity-div\"><a class=\"comment-button\"><p class=\"\"><span class=\"comments-number\">";
    string += array[9];
    string += "</span> comments.<p></a></div></div><div class=\"post-comments row hidden\"><div class=\"comment-container\"></div><form id=\"form-";
    string += array[0];
    string += "\" enctype=\"multipart/form-data\" class=\"create-comment-form\" method=\"POST\"><div class=\"form-group\"><div class=\"col-sm-12\"><textarea class=\"form-control comment-text\" minlength=\"20\" maxlength=\"500\" rows=\"2\" name=\"comment-text\"></textarea></div></div><div class=\"form-group\"><div class=\"col-sm-12\"><button disabled type=\"button\" class=\"create-comment-button btn btn-primary btn-md btn-block\">Comment it</button></div></div></form></div></div></div>";
    return string;
}