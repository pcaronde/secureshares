<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<h3>Secure Files</h3>
<div id="contentList">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th>&nbsp;</th>
            <th>Name</th>
            <th>Date</th>
        </tr>
        <c:forEach var="file" items="${dbManager.files}" varStatus="status">
            <tr>
                <td align="center" <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>><input type="radio" name="file" value="${file.id}" align="left" <c:if test="${status.first}">checked="checked"</c:if>/></td>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>>${file.filename}</td>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>><fmt:formatDate pattern="yyyy-MM-dd" value="${file.date.time}" /></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" align="right">
                <input type="button" id="add" value="Send Download Link"/>
                <input type="button" id="delete" value="Delete File"/>
            </td>
        </tr>
    </table>
</div>