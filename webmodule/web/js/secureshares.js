goToUsers=function(){
    showLoading();
    $.get('contentUsers.jsp', function(data){
        $('#indexContainer').html(data);
        $('.active').each(function(index, item){
           $(item).removeClass("active");
        });
        $('#lnkusers').parent().addClass("active");
   });
}

goToUpload=function(){
    showLoading();
    $.get('contentUpload.jsp', function(data){
        $('#indexContainer').html(data);
        $('.active').each(function(index, item){
           $(item).removeClass("active");
        });
        $('#lnkupload').parent().addClass("active");
   });
}

goToFiles=function(){
    showLoading();
    $.get('contentFiles.jsp', function(data){
            $('#indexContainer').html(data);
            $('.active').each(function(index, item){
               $(item).removeClass("active");
            });
            $('#lnkfiles').parent().addClass("active");
       });
}

goToHome=function(){
    showLoading();
    $.get('contentHome.jsp', function(data){
       $('#indexContainer').html(data);
       $('.active').each(function(index, item){
          $(item).removeClass("active");
       });
       $('#lnkhome').parent().addClass("active");
   });
}

goToSupport=function(){
    showLoading();
    $.get('contentSupport.jsp', function(data){
       $('#indexContainer').html(data);
       $('.active').each(function(index, item){
          $(item).removeClass("active");
       });
       $('#lnksupport').parent().addClass("active");
   });
}

goToHowTo=function(){
    showLoading();
    $.get('contentHowItWorks.jsp', function(data){
       $('#indexContainer').html(data);
       $('.active').each(function(index, item){
          $(item).removeClass("active");
       });
       $('#lnkhow').parent().addClass("active");
   });
}

changeLanguage=function(lang){
    $.post('language.jsp', {l: lang}, function(data){
        window.location.reload(true);
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

showLoading=function(){
    $('#indexContainer').html("<img src='images/content-loader.gif' alt='loading' style='margin-top: 50px'/><br/><p>loading. please be patient ...</p>");
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
