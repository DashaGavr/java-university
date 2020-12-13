$(function () {
   $(document).ready(function(){
       $('.imageShow').modal('show');
    });
    if (localStorage.getItem("id") != null)
    {
        document.cookie = "user=" + localStorage.getItem("id");
        console.log("old id" + localStorage.getItem("id"));
    }
    else {
        var id = randomInt(0, 10000);
        document.cookie = "user=" + id;
        console.log("new" + id);
        localStorage.setItem('id', id);
    }
});


function randomInt(min, max) {
    return min + Math.floor((max - min) * Math.random());
}

function StartSession ()
{
    $('.imageShow').modal('show');
}

