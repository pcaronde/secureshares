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
<h3 id="securefiles">Availability</h3>
		<P>Set the time your file(s) will be available to external users</P>
    <div style="text-indent:0px;">
        <c:forEach var="downloadType" items="${dbManager.downloadTypes}" varStatus="status">
            <input type="radio" name="downloadtype" align="left" value="${downloadType.id}" <c:if test="${status.first}">checked="checked"</c:if>/>${downloadType.name}<br/>
        </c:forEach>
    </div>