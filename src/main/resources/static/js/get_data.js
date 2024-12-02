function getData (url) {
    var requestURL = url;
    var request = new XMLHttpRequest();

    request.open("GET", requestURL);

    request.responseType = "json";
    request.send();

    request.onload = function () {
        var superHeroes = request.response;
        console.log(superHeroes);
    };

}

export default getData;