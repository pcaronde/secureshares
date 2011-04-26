<div id="container">
    <br/><br/><br/><br/><br/>
    <h3>Secure Files</h3>
    <div id="fileTreeRemote" class="panel"></div>
</div>
<script type="text/javascript">

    showTree=function(){
        $('#fileTreeRemote').fileTree({ root: '/home/guesftp/securedrepository', script: 'jqueryFileTree.jsp', multiFolder: false }, function(file) {
        //$('#fileTreeRemote').fileTree({ root: '/home/cticu/securedrepository', script: 'jqueryFileTree.jsp', multiFolder: false }, function(file) {
            alert(file);
        });
    }

    $(function() {
        showTree();
    });
</script>