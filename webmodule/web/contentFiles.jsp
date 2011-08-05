<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<s:check role="admin">
<h3>Secure Files</h3>
<div id="contentList">
    <table border="0" cellpadding="0" cellspacing="0" class="files">
        <tr>
            <th>&nbsp;</th>
            <th>Name</th>
            <th>Date</th>
        </tr>
        <c:forEach var="file" items="${dbManager.files}" varStatus="status">
            <tr>
                <td align="center" <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>>
                    <input type="radio" name="file" value="${file.id}" align="left" <c:if test="${status.first}">checked="checked"</c:if>/>
                </td>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>>
                    <span class="ext_${fn:toLowerCase(fn:substringAfter(file.filename, "."))}">${file.filename}</span>
                </td>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>><fmt:formatDate pattern="yyyy-MM-dd" value="${file.date.time}" /></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" align="right">
                <input type="button" id="send" value="Send Download Link"/>
                <input type="button" id="delete" value="Delete File"/>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    $('#delete').bind('click', function(){
       $('#delete').attr("disabled", "true");
       if(confirm("Are you sure you want to delete the selected file?")){
           $.post("service", {a: 5, id: $("input[@name=file]:checked").val()}, function(data){
                var response = $.parseJSON(data);
                if("OK" == response.status){
                    goToFiles();
                } else {
                    showErrorMessage($('#ok'), $('#error'), response.messages);
                }
            });
       } else {
           $('#delete').removeAttr("disabled");
       }
    });
    $('#send').bind('click', function(){
        $('#send').attr("disabled", "true");
        var fileId = $("input[@name=file]:checked").val();
        showLoading();
        $.get('sendDownloadLink.jsp', {id: fileId}, function(data){
           $('#indexContainer').html(data);
        });
    });
</script>
</s:check>