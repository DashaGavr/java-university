$(document).ready(function(){
        $('.imageShow').modal('show');

});

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
    document.location.replace("http://localhost:8080")
}

document.addEventListener('keydown', function(event) {
    const key = event.key; // const {key} = event; in ES6+
    if (key === "Escape") {
        document.location.replace("http://localhost:8080/stop")
        //document.location.replace("http://localhost:8080")
    }
});


//esc нажатие такая же обработка

/*

$("").click( function () {
    this.getElementById("chosen").action = "/start/1"
});
$("#two").click( function () {
    this.getElementById("chosen").action = "/start/2"
});
*/