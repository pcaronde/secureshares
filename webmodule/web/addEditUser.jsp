<script type="text/javascript" src="js/secureshares.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<s:check role="admin">
<h3>Secure Users</h3>
<c:if test="${!empty param.id}">
    <jsp:useBean id="invoker" class="ro.panzo.secureshares.util.Invoker"/>
    <jsp:setProperty name="invoker" property="target" value="${dbManager}"/>
    <jsp:setProperty name="invoker" property="methodName" value="getUserById"/>
    <jsp:setProperty name="invoker" property="addParamClasses" value="java.lang.Long"/>
    <jsp:setProperty name="invoker" property="addParamObjects" value="${0 + param.id}"/>
    <c:set var="user" value="${invoker.invoke}"/>
    <input type="hidden" id="id" value="${user.id}"/>
</c:if>

<div id="addEditUser" style="text-indent:0px;">
    <table border="0" cellpadding="5" cellspacing="0">
        <tr>
            <td colspan="2"><span style="color:red;" id="error"></span></td>
        </tr>
        <tr>
            <td>Username:</td>
            <td><input type="text" id="username" value="<c:if test="${!empty user}">${user.username}</c:if>" <c:if test="${!empty param.id}">readonly="readonly"</c:if>/></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" id="password" value=""/></td>
        </tr>
        <tr>
            <td>Retype Password:</td>
            <td><input type="password" id="retypepassword" value=""/></td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="button" id="ok" value="<c:choose><c:when test="${empty param.id}">Insert</c:when><c:otherwise>Save</c:otherwise></c:choose>"/>
                <input type="button" id="cancel" value="Cancel"/>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">

    $('#ok').bind('click', function(){
        $('#ok').attr("disabled", "true");
        <c:choose>
            <c:when test="${empty param.id}">
                $.post("service", {a: 1, u: $("#username").val(), p :$("#password").val(), rp: $("#retypepassword").val()}, function(data){
                    var response = $.parseJSON(data);
                    if("OK" == response.status){
                        goToUsers();
                    } else {
                        showErrorMessage($('#ok'), $('#error'), response.messages);
                    }
                });
            </c:when>
            <c:otherwise>
                $.post("service", {a: 2, id: $("#id").val(), p :$("#password").val(), rp: $("#retypepassword").val()}, function(data){
                    var response = $.parseJSON(data);
                    if("OK" == response.status){
                        goToUsers();
                    } else {
                        showErrorMessage($('#ok'), $('#error'), response.messages);
                    }
                });
            </c:otherwise>
        </c:choose>
    });
    $('#cancel').bind('click', function(){
       $('#cancel').attr("disabled", "true");
       goToUsers();
    });
</script>
<br/>
<br/>
<br/><br/><br/>
</s:check>