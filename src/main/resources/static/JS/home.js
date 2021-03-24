$(function () {
   $(document).ready(function(){
       $('.imageShow').modal('show');
    });
    if (localStorage.getItem("id") != null)
    {
        document.cookie = "user=" + localStorage.getItem("id");
        console.log("old id   " + localStorage.getItem("id"));
    }
    else {
        var id = gen();
        let date = new Date( new Date(2023, 11, 31, 0,0,0));
        date = date.toUTCString();
        document.cookie = "user=" + id + "expires=" + date;
        console.log("new" + id);
        localStorage.setItem("id", id);
    }
});

function gen() {
    return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g,c=>(c^crypto.getRandomValues(new Uint8Array(1))[0]&15 >> c/4).toString(16));
}

function randomInt(min, max) {
    return min + Math.floor((max - min) * Math.random());
}

function StartSession ()
{
    $('.guide').modal('show');
    //$('.imageShow').modal('show');
}

