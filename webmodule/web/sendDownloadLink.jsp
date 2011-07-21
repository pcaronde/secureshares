<script type="text/javascript" src="js/secureshares.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<%@ taglib uri="/WEB-INF/invoker.tld" prefix="invk" %>
<s:check role="admin">
<h3>Send Download Link</h3>
<c:if test="${!empty param.id}">
    <script type="text/javascript">
        var recipients = [];
    </script>

    <invk:execute var="file" target="dbManager" method="getFileById">
        <invk:param type="long" value="${0 + param.id}"/>
    </invk:execute>

    <input type="hidden" id="fileid" value="${file.id}"/>
    
    <div style="text-align: left">
        <table border="0" cellpadding="5" cellspacing="0" class="files">
            <tr>
                <td colspan="2"><p style="color:red;" id="error"></p></td>
            </tr>
            <tr>
                <td>Recipeints:</td>
                <td><input type="text" id="recipient" value=""/><input type="button" id="btadd" value="Add"/></td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>
                    <div id="recipientsList"></div>
                </td>
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
    $("#recipient").focus();
    deleteRecipient=function(id){
        var recipientsTmp = [];
        var index;
        var newIndex = 0;
        $('#recipientsList').html('');
        for (index = 0; index < recipients.length; index++){
            if(index != id){
                recipientsTmp[newIndex] = recipients[index];
                $('#recipientsList').append("<div class='recipientRow' id='" + newIndex + "'><div>" + recipientsTmp[newIndex] + "</div><a href='#' class='deleteRecipientBt' title='delete' onclick='deleteRecipient(" + newIndex+ ")'>&nbsp;</a></div>");
                newIndex++;
            }
        }
        recipients = recipientsTmp;
    }
{
    };
    $('#send').bind('click', function(){
        $('#send').attr("disabled", "true");
        $.post("service", {a: 6, fid: $("#fileid").val(), dtid: $("input[@name=downloadtype]:checked").val(), r: '' + recipients}, function(data){
                    var response = $.parseJSON(data);
                    if("OK" == response.status){
                        goToFiles();
                    } else {
                        showErrorMessage($('#ok'), $('#error'), response.messages);
                    }
                });
    });
    $('#cancel').bind('click', function(){
       $('#cancel').attr("disabled", "true");
       goToFiles();
    });

    addRecipient = function(){
        var recipient = $("#recipient").val();
        if(recipient && recipient.length > 0){
            if(/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/.test(recipient)){
                $('#btadd').attr("disabled", "true");
                var index = recipients.length;
                recipients[index] = recipient;
                $('#recipientsList').append("<div class='recipientRow' id='" + index + "'><div>" + recipient + "</div><a href='#' class='deleteRecipientBt' title='delete' onclick='deleteRecipient(" + index+ ")'>&nbsp;</a></div>")
                $("#recipient").val("");
                $('#btadd').removeAttr("disabled");

            } else {
                alert('Invalid recipient address (eg: my_name@email.com)');
            }
        }
        $("#recipient").focus();
    };

    $("#recipient").bind('keypress', function(e){
        var code = (e.keyCode ? e.keyCode : e.which);
        if(code == 13) {
            addRecipient();
        }
    });

    $('#btadd').bind('click', function(){
        addRecipient();
    });
</script>
</s:check>