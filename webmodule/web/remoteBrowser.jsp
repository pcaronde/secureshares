<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="container">
    <br/><br/><br/><br/><br/>
    <h3>Secure Files</h3>
    <div id="fileTreeRemote" class="panel"></div>
</div>
<script type="text/javascript">

    showTree=function(){
        $('#fileTreeRemote').fileTree({ script: 'jqueryFileTree.jsp', multiFolder: false }, function(file) {
            alert(file);
        });
    }

    $(function() {
        showTree();
    });
</script>