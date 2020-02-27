// Get by name parameter from URL
function getParameterByName(name, url) {
    if (!url) url = window.location.href;
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

//Delete login form CSS classes if URL parameter 'error = true'
$(document).ready(function () {
    var urlParam = getParameterByName('error');
    if (urlParam === 'true') {
        $("div#log").removeClass("fadeInDown");
        $("div#userIcon").removeClass("fadeIn first");
        $("div#username").removeClass("fadeIn second");
        $("div#password").removeClass("fadeIn third");
        $("div#submit").removeClass("fadeIn fourth");
    }
});

//Hide 'singIn' and 'singUp' on forms login/registration
$(document).ready(function () {
    if (window.location.href.indexOf("login") > -1 || window.location.href.indexOf("registration") > -1) {
        $("#singIn").hide();
        $("#singUp").hide();
    }
});