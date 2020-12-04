function sending() {
    let tmp = document.getElementById('sending_form');
    //console.log("here");
    tmp.submit();
    result();
}
function result() {
    $('#sent').modal('show');
}