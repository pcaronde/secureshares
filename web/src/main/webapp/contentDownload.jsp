<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="l" %>
<c:set var="lastAction" value="${8}" scope="session"/>
<s:check role="admin,user,viewer">
<h3><l:text key="contentDownloadTitle"/></h3>
<div id="contentList">
    <table border="0" cellpadding="0" cellspacing="0" class="files">
        <tr>
            <th><l:text key="contentFilesName"/></th>
            <th><l:text key="contentFilesDate"/></th>
        </tr>
        <c:forEach var="file" items="${dbManager.files}" varStatus="status">
            <tr>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>>
                    <span class="ext_${fn:toLowerCase(fn:substringAfter(file.filename, "."))}">
                        <a href="webdownload?id=${file.id}">${file.filename}</a>
                    </span>
                </td>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>><fmt:formatDate pattern="yyyy-MM-dd" value="${file.date.time}" /></td>
            </tr>
        </c:forEach>
    </table>
</div>
</s:check>