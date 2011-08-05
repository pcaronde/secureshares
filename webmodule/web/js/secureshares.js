goToUsers=function(){
        $.get('contentUsers.jsp', function(data){
            $('#indexContainer').html(data);
            $('.active').each(function(index, item){
               $(item).removeClass("active");
            });
            $('#lnkusers').parent().addClass("active");
       });
    }

goToUpload=function(){
        $.get('contentUpload.jsp', function(data){
            $('#indexContainer').html(data);
            $('.active').each(function(index, item){
               $(item).removeClass("active");
            });
            $('#lnkupload').parent().addClass("active");
       });
    }

goToFiles=function(){
    $.get('contentFiles.jsp', function(data){
            $('#indexContainer').html(data);
            $('.active').each(function(index, item){
               $(item).removeClass("active");
            });
            $('#lnkfiles').parent().addClass("active");
       });
}

goToHome=function(){
    $.get('contentHome.jsp', function(data){
       $('#indexContainer').html(data);
       $('.active').each(function(index, item){
          $(item).removeClass("active");
       });
       $('#lnkhome').parent().addClass("active");
   });
}

goToSupport=function(){
    $.get('contentSupport.jsp', function(data){
       $('#indexContainer').html(data);
       $('.active').each(function(index, item){
          $(item).removeClass("active");
       });
       $('#lnksupport').parent().addClass("active");
   });
}

goToHowTo=function(){
    $.get('contentHowItWorks.jsp', function(data){
       $('#indexContainer').html(data);
       $('.active').each(function(index, item){
          $(item).removeClass("active");
       });
       $('#lnkhow').parent().addClass("active");
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
