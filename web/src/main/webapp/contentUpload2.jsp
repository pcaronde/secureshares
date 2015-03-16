<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<s:check role="admin,user">
<c:set var="lastAction" value="${6}" scope="session"/>
<h3>Secure Uploads</h3>

<P>Choose files from your local computer and securely upload to the server</P>

<div id="upload" style="text-indent:0px;">
    <p>
        <label for="file">Choose file</label>
        <input type="file" id="file" name="file" accept="image/jpg, image/jpeg, image/gif, image/png"/>
    </p>

    <p>
        <button id="uploadbt">Upload</button>
    </p>
    <div style="text-align: left">
        <span id="progress" class="progress">0%</span>
    </div>
</div>


<script type="text/javascript">
    $(function () {
        var $b = $('#uploadbt'),
                $f = $('#file'),
                $p = $('#progress'),
                up = new uploader($f.get(0), {
                    url: 'service?a=4',
                    onprogress: function (ev) {
                        $p.html(Math.round((ev.loaded / ev.total) * 100) + '%');
                        $p.css('width', $p.html());
                        //console.log('progress: ' + $p.html());
                    },
                    error: function (ev) {
                        //console.log('error');
                    },
                    success: function (data) {
                        //console.log('success');
                        $p.html('100%');
                        $p.css('width', $p.html());
                    }
                });

        $b.click(function () {
            up.send();
        });
    });
</script>
</s:check>