$(document).ready(function(){
    $('.imageShow').modal('show');

});

/*
$('#ref').style.position = "center";
$('#ref').style.width= 0.666 * document.documentElement.clientWidth;
*/

//setTimeout(imgLoaded, 5000);
function stopsession() {
    $('.imageShow').modal('hide');
    $('.thanks').modal('show');

    //document.location.replace("http://localhost:8080");
}
/*
$(".gohome").click( function () {
    document.location.replace("http://localhost:8080")
});*/

function gohome() {
    document.location.replace("http://localhost:8081")
}

document.addEventListener('keydown', function(event) {
    const key = event.key; // const {key} = event; in ES6+
    if (key === "Escape") {
        document.location.replace("http://localhost:8081/stop")
        //document.location.replace("http://localhost:8080")
    }
});

function imgLoaded(img){
    var imgWrapper = img.parentNode;
    //console.log(img.height);
    let s = Math.floor(img.height * 1.333);
    let ww = window.innerWidth;
    console.log(s/ww * 100);
    img.style.width = s + 'px';
    //img.style.width = Math.floor(s/ww * 100) + '%';

    imgWrapper.className += imgWrapper.className ? ' loaded' : 'loaded';

}



//esc нажатие такая же обработка

/*

$("").click( function () {
    this.getElementById("chosen").action = "/start/1"
});
$("#two").click( function () {
    this.getElementById("chosen").action = "/start/2"
});
*/
