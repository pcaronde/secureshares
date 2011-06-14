<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="container">
    <h3>Secure Files</h3>
    <div id="filelist" class="filepanel">
        <table border="1" cellpadding="0" cellspacing="0" width="100%">
            <tr>
                <th>Name</th>
                <th>Download Type</th>
            </tr>
            <c:forEach var="file" items="${dbManager.files}" varStatus="status">
            <tr>
                <td>${file.filename}</td>
                <td>${file.downloadType.name}</td>
            </tr>
        </c:forEach>
        </table>
    </div>
</div>
<script type="text/javascript">
    saveFileTransaction=function(fileName){
        $.post("service", {a: 4, tdid: $("input[@name=downloadtype]:checked").val(), fn: fileName}, function(data){
                var response = $.parseJSON(data);
                if("OK" == response.status){
                    reloadFileList()
                } else {
                    //showErrorMessage($('#ok'), $('#error'), response.messages);
                }
            });
    }
</script>