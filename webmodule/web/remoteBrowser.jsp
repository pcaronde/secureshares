<div id="container">
    <br/><br/><br/><br/><br/>
    <h3>Secure Files</h3>
    <div id="fileTreeRemote" class="panel"></div>
</div>
<script type="text/javascript">
    $(function() {
        $('#fileTreeRemote').fileTree({ root: '/home/guesftp/securedrepository', script: 'jqueryFileTree.jsp', multiFolder: false }, function(file) {
            alert(file);
        });
    });
</script>