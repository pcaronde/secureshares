<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<h3>Secure Files</h3>
<div id="contentList">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th>Name</th>
            <th>Date</th>
        </tr>
        <c:forEach var="file" items="${dbManager.files}" varStatus="status">
            <tr>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>>${file.filename}</td>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>><fmt:formatDate pattern="yyyy-MM-dd" value="${file.date.time}" /></td>
            </tr>
        </c:forEach>
    </table>
</div>