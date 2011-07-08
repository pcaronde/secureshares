goToUsers=function(){
        $.get('contentUsers.jsp', function(data){
          $('#indexContainer').html(data);
       });
    }

goToFiles=function(){
    /*$.get('contentFiles.jsp', function(data){
      $('#col3_content').html(data);
   });
    $.get('fileList.jsp', function(data){
      $('#col2_content').html(data);
   });*/
}

reloadFileList=function(){
    /*$.get('fileList.jsp', function(data){
      $('#col2_content').html(data);
   });*/
}

goToHome=function(){
    $.get('contentHome.jsp', function(data){
      $('#indexContainer').html(data);
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

