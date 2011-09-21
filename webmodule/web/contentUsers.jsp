<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<c:set var="lastAction" value="${4}" scope="session"/>
<s:check role="admin">
<h3>Manage Secure Shares Users</h3>
<P>Here you can define the premissions for users internal and external</P>
<div id="contentList">
    <span style="color:red;" id="error"></span>
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th>&nbsp;</th>
            <th>Username</th>
            <th>Role</th>
        </tr>
        <c:forEach var="user" items="${dbManager.users}" varStatus="status">
            <tr>
                <td align="center" <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>>
                    <input type="radio" name="user" value="${user.id}" align="left" <c:if test="${status.first}">checked="checked"</c:if>/>
                </td>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>>${user.username}</td>
                <td <c:if test="${status.count % 2 == 0}">class="highlighted"</c:if>>${user.role}</td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" align="right">
                <input type="button" id="add" value="Add User"/>
                <input type="button" id="edit" value="Edit User"/>
                <input type="button" id="delete" value="Delete User"/>
            </td>
        </tr>
    </table>
</div>
<script type="text/javascript">
    $('#add').bind('click', function(){
       $('#add').attr("disabled", "true");
       showLoading();
       $.get('addEditUser.jsp', function(data){
          $('#indexContainer').html(data);
       });
    });
    $('#edit').bind('click', function(){
       $('#edit').attr("disabled", "true");
       var userId = $("input[@name=user]:checked").val();
       showLoading();
       $.get('addEditUser.jsp', {id: userId}, function(data){
          $('#indexContainer').html(data);
       });
    });
    $('#delete').bind('click', function(){
       $('#delete').attr("disabled", "true");
       if(confirm("Are you sure you want to delete the selected users?")){
           $.post("service", {a: 3, id: $("input[@name=user]:checked").val()}, function(data){
                var response = $.parseJSON(data);
                if("OK" == response.status){
                    goToUsers();
                } else {
                    showErrorMessage($('#ok'), $('#error'), response.messages);
                }
            });
       } else {
           $('#delete').removeAttr("disabled");
       }
    });
</script>
<br/>
<br/>
<br/><br/><br/>
</s:check>