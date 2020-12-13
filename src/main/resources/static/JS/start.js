
//localStorage.setItem("timer", "00:00:00");
/*
$('#my_timer').innerText = "00:00:00";
startTimer();
*/

$(document).ready(function(){
    $('.imageShow').modal('show');
    var my_timer = document.getElementById("my_timer");
    var t = localStorage.getItem('timer');
    if (t == null) {
        t = '00:00:00';
    }
    my_timer.innerHTML = t;
    localStorage.setItem('timer', t);
    console.log(document.cookie);
    startTimer();
});


/*
$(".gohome").click( function () {
    document.location.replace("http://localhost:8080")
});*/

document.addEventListener('keydown', function(event) {
    const key = event.key; // const {key} = event; in ES6+
    if (key === "Escape") {

        localStorage.setItem("timer", "00:00:00");
        document.location.replace("http://localhost:8081/stop")
        //document.location.replace("http://localhost:8080")
    }
});


function gohome() {
    document.location.replace("http://localhost:8081")
}

function imgLoaded(img){
    var imgWrapper = img.parentNode;
    //console.log(img.height);
    let s = Math.floor(img.height * 1.333);
    let ww = window.innerWidth;

    img.style.width = s + 'px';
    //img.style.width = Math.floor(s/ww * 100) + '%';

    imgWrapper.className += imgWrapper.className ? ' loaded' : 'loaded';
}

function startTimer() {
    if (localStorage.getItem('timer') == null)
    {
        var timer = "00:00:00";
        localStorage.setItem('timer', timer);
        var my_timer = document.getElementById("my_timer");
        my_timer.innerHTML = timer;
    }
    else {
        let t = localStorage.getItem('timer');
        var my_timer = document.getElementById("my_timer");
        var time = my_timer.innerHTML;
        var arr = time.split(":");
        var h = arr[0];
        var m = arr[1];
        var s = arr[2];
        if (m == 15) {
            //alert("Время вышло");
            stopsession();
            return;
        }
        else if (s == 59) {
            m++;
            if (m < 10) m = "0" + m;
            s = "0" + 0;
        }
        else s++;
        if (s < 10 && s != "00") s = "0" + s;
        document.getElementById("my_timer").innerHTML = h+":"+m+":"+s;
        localStorage.setItem("timer", h+":"+m+":"+s);
        setTimeout(startTimer, 1000);
    }
}

function stopsession() {
    $('.imageShow').modal('hide');
    localStorage.setItem("timer", "00:00:00");
    $('.thanks').modal('show');

    //document.location.replace("http://localhost:8080");
}

/*
$("").click( function () {
    this.getElementById("chosen").action = "/start/1"
});
$("#two").click( function () {
    this.getElementById("chosen").action = "/start/2"
});
*/
