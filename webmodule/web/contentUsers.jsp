<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3>Secure Users</h3>
<div id="content" style="text-indent:0px;">
    <table border="0" cellpadding="5" cellspacing="0">
        <tr>
            <th>&nbsp;</th>
            <th>Username</th>
            <th>Role</th>
        </tr>
        <c:forEach var="user" items="${dbManager.users}" varStatus="status">
            <tr>
                <td><input type="radio" name="user" align="left" <c:if test="${status.first}">checked="checked"</c:if>/></td>
                <td>${user.username}</td>
                <td>${user.role}</td>
            </tr>
        </c:forEach>
    </table>
</div>
<br/>
<br/>
<br/><br/><br/>