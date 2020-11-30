$(function () {
   $(document).ready(function(){
       $('.imageShow').modal('show');
    });
    /*$(".start-button").click( function (e) {
        $('.imageShow').modal('show');
    });*/
});


function StartSession ()
{
    $('.imageShow').modal('show');
}


/*
function getImage()
{
    //alert("STAAAAAAAAAAR!!!!!!!");
    //let ref = document.getElementById("ref")
    let ref = new Image();
    /!*ref.onload= function()
    {
        document.getElementById("ref").src = "images/databaseImage/I11_3_0_8.bmp";
    }*!/
    ref.src = "images/databaseImage/I11_3_0_8.bmp";
    //$("#ref").attr("src", searchPic);
    document.getElementById("ref").src = ref.src;


    let dist1 = new Image();
    dist1.src = "images/databaseImage/I11_3_0_8.bmp";
    //$("#ref").attr("src", searchPic);
    document["dist"].src = dist1.src;

    /!*var image = document.createElement("img");
    var imageParent = document.getElementsByClassName("hidden-start")[0];
    image.id = "ref_1";
    //image.className = "class";
    image.src = ref.src;
    imageParent.appendChild(image);*!/

    /!*let dist2 =document.getElementById("dist2")
    var dist1 = document.getElementById("dist1")

    dist1.src = "images/databaseImage/I01_0.2719_3.7582_11.9509.bmp"

    dist2.src = "images/databaseImage/I12_0.2964_2.5314_2.1854.bmp"*!/
}*/
