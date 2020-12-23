$(document).ready(function(){
    $('.imageShow').modal('show');
    //localStorage.removeItem('id');
    var my_timer = document.getElementById("my_timer");
    var t = localStorage.getItem('timer');
    if (t == null) {
        t = '00:15:01';
        console.log("null")
    }
    my_timer.innerHTML = t;
    localStorage.setItem('timer', t);
    console.log(document.cookie);
    startTimer();
});

document.addEventListener('keydown', function(event) {
    const key = event.key; // const {key} = event; in ES6+
    if (key === "Escape") {

        localStorage.setItem("timer", "00:15:01");
        document.location.replace("http://localhost:8081/stop")
        //document.location.replace("http://localhost:8080")
    }
});

function imgLoaded(img){
    var imgWrapper = img.parentNode;
    /*let s = Math.floor(img.height * 1.333);
    let ww = imgWrapper.innerWidth;
    console.log("ww = " + ww)
    console.log(img.height + ' ' +  img.width + ' s = ' + s)
    img.style.width = s + 'px';
    img.style.width = Math.floor(s/ww * 100) + '%';*/
    imgWrapper.className += imgWrapper.className ? ' loaded' : 'loaded';
}

function imgLoadedRef(img){
    var imgWrapper = img.parentNode;
    let s = Math.floor(img.height * 1.333);
    console.log(img.height + ' ' +  img.width + ' s = ' + s)
    img.style.width = s + 'px';
    img.width = s + 'px';
    //img.style.width = Math.floor(s/ww * 100) + '%';
    imgWrapper.className += imgWrapper.className ? ' loaded' : 'loaded';
}

function startTimer() {
    if (localStorage.getItem('timer') == null)
    {
        var timer = "00:15:01";
        localStorage.setItem('timer', timer);
        var my_timer = document.getElementById("my_timer");
        my_timer.innerHTML = timer;
    }
    else {
        //let t = localStorage.getItem('timer');
        var my_timer = document.getElementById("my_timer");
        var time = my_timer.innerHTML;
        var arr = time.split(":");
        var h = arr[0];
        var m = parseInt(arr[1]);
        var s = parseInt(arr[2]);
        console.log(s + typeof s)
        if (m == 0) {
            //alert("Время вышло");
            stopsession();
            return;
        }
        else if (s == 0) {
            m--;
            if (m < 10) m = "0" + m;
            s = "59";
        }
        else s--;
        if (s < 10) s = "0" + s;
        document.getElementById("my_timer").innerHTML = h+":"+m+":"+s;
        localStorage.setItem("timer", h+":"+m+":"+s);
        setTimeout(startTimer, 1000);
    }
}

function stopsession() {
    $('.imageShow').modal('hide');
    localStorage.setItem("timer", "00:15:01");
    $('.thanks').modal('show');
}