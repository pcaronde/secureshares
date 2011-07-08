goToUsers=function(){
        $.get('contentUsers.jsp', function(data){
            $('#indexContainer').html(data);
            $('#lnkusers').parent().addClass("active");
            $('#lnkfiles').parent().removeClass("active");
            $('#lnkupload').parent().removeClass("active");
       });
    }

goToUpload=function(){
        $.get('contentUpload.jsp', function(data){
            $('#indexContainer').html(data);
            $('#lnkupload').parent().addClass("active");
            $('#lnkfiles').parent().removeClass("active");
            $('#lnkusers').parent().removeClass("active");
       });
    }

goToFiles=function(){
    $.get('contentFiles.jsp', function(data){
            $('#indexContainer').html(data);
            $('#lnkfiles').parent().addClass("active");
            $('#lnkupload').parent().removeClass("active");
            $('#lnkusers').parent().removeClass("active");
       });
}

goToHome=function(){
    $.get('contentHome.jsp', function(data){
       $('#indexContainer').html(data);
       $('#lnkusers').parent().removeClass("active");
       $('#lnkfiles').parent().removeClass("active");
       $('#lnkupload').parent().removeClass("active");
       $('#lnkhome').parent().addClass("active");
   });
}

showErrorMessage=function(bt, field, messages){
    bt.removeAttr("disabled");
    field.html("");
    var ul = field.append("<ul></ul>");
    $.each(messages, function(i, current){
        ul.append("<li>" + current.message + "</li>");
    });
}

saveFileTransaction=function(fileName){
    $.post("service", {a: 4, fn: fileName}, function(data){
            var response = $.parseJSON(data);
            if("OK" == response.status){
                //reloadFileList();
            } else {
                //showErrorMessage($('#ok'), $('#error'), response.messages);
            }
        });
}
