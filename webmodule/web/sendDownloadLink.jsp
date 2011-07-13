<script type="text/javascript" src="js/secureshares.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<s:check role="admin">
<h3>Send Download Link</h3>
<c:if test="${!empty param.id}">
    <jsp:useBean id="invoker" class="ro.panzo.secureshares.util.Invoker"/>
    <jsp:setProperty name="invoker" property="target" value="${dbManager}"/>
    <jsp:setProperty name="invoker" property="methodName" value="getFileById"/>
    <jsp:setProperty name="invoker" property="addParamClasses" value="long"/>
    <jsp:setProperty name="invoker" property="addParamObjects" value="${0 + param.id}"/>
    <c:set var="file" value="${invoker.invoke}"/>
    <input type="hidden" id="id" value="${file.id}"/>

    <div style="text-align: left">
        <table border="0" cellpadding="5" cellspacing="0" class="files">
            <tr>
                <td colspan="2"><p style="color:red;" id="error"></p></td>
            </tr>
            <tr>
                <td>File:</td>
                <td><span class="ext_${fn:toLowerCase(fn:substringAfter(file.filename, "."))}">${file.filename}</span></td>
            </tr>
            <tr>
                <td valign="top">Availability:</td>
                <td>
                    <c:forEach var="downloadType" items="${dbManager.downloadTypes}" varStatus="status">
                        <input type="radio" name="downloadtype" align="left" value="${downloadType.id}" <c:if test="${status.first}">checked="checked"</c:if>/>${downloadType.name}<br/>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="right">
                    <input type="button" id="send" value="Send"/>
                    <input type="button" id="cancel" value="Cancel"/>
                </td>
            </tr>
        </table>
    </div>
</c:if>
<script type="text/javascript">
    $('#cancel').bind('click', function(){
       $('#cancel').attr("disabled", "true");
       goToFiles();
    });
</script>
</s:check>