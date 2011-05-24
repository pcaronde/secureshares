<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<s:check role="admin">
<h3>Secure Users</h3>
<div id="content" style="text-indent:0px;">
    <span style="color:red;" id="error"></span>
    <table border="0" cellpadding="5" cellspacing="0">
        <tr>
            <th>&nbsp;</th>
            <th>Username</th>
            <th>Role</th>
        </tr>
        <c:forEach var="user" items="${dbManager.users}" varStatus="status">
            <tr>
                <td><input type="radio" name="user" value="${user.id}" align="left" <c:if test="${status.first}">checked="checked"</c:if>/></td>
                <td>${user.username}</td>
                <td>${user.role}</td>
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
       $.get('addEditUser.jsp', function(data){
          $('#col3_content').html(data);
          $('#col2_content').html("");
       });
    });
    $('#edit').bind('click', function(){
       $('#edit').attr("disabled", "true");
       $.get('addEditUser.jsp', {id: $("input[@name=user]:checked").val()}, function(data){
          $('#col3_content').html(data);
          $('#col2_content').html("");
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