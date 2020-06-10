function formularioError(response) {

    $('#errorDiv').show();
    $("#errorDiv").attr("class","callout callout-danger")
    var child = document.getElementById("errorUl").lastElementChild;

    while (child) {
        document.getElementById("errorUl").removeChild(child);
        child = document.getElementById("errorUl").lastElementChild;
    }

    for(i=0;i<response.result.length;i++){
        var li = document.createElement('li');
        var contentLi = document.createTextNode(response.result[i].code);
        li.appendChild(contentLi);
        document.getElementById("errorUl").appendChild(li);
    }

}

function conexionError() {

    $('#errorDiv').show();
    $("#errorDiv").attr("class","callout callout-warning")
    var child = document.getElementById("errorUl").lastElementChild;

    while (child) {
        document.getElementById("errorUl").removeChild(child);
        child = document.getElementById("errorUl").lastElementChild;
    }

    var li = document.createElement('li');
    var contentLi = document.createTextNode("Algo salió mal, por favor inténtelo de nuevo :c");
    li.appendChild(contentLi);
    document.getElementById("errorUl").appendChild(li);

}