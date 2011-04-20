<h2>Manage Files</h2>

<div id="container">
    <h3>Secure Files</h3>
    <ul type="disc">
        <li><b>-rw-r--r-- secure_user users 4.6K 2011-03-11 12:20 admin.wav</b></li>
        <li><b>-rw-r--r-- secure_user users 5.6K 2011-03-14 12:06 my_movie.mpg</b></li>
        <li><b>-rw-r--r-- secure_user users 4.7K 2011-03-14 12:02 help.zip</b></li>
        <li><b>-rw-r--r-- secure_user users 5.6K 2011-03-14 12:01 pix.tar.gz</b></li>

    </ul>
    <br/>

    <h3 id="secureuploads">Secure Uploads</h3>

    <div id="upload" style="text-indent:0px;">
        <object
                width="400"
                height="300" classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
                codebase="http://java.sun.com/products/plugin/autodl/jinstall-1_4-windows-i586.cab#Version=1,4,0,0">
            <param name="codebase_lookup" value="false">
            <param name="id" value="myApplet"/>
            <param name="code" value="ro.panzo.securedshares.Upload"/>
            <param name="archive" value="secured-shares.applet-1.0.jar"/>
            <!--[if !IE]> -->
            <object width="400" height="300" type="application/x-java-applet">
                <param name="codebase_lookup" value="false">
                <param name="id" value="myApplet"/>
                <param name="code" value="ro.panzo.securedshares.Upload"/>
                <param name="archive" value="secured-shares.applet-1.0.jar"/>
            </object>
            <!-- <![endif]-->
        </object>
        <%--<noscript>A browser with JavaScript enabled is required for this page to operate properly.</noscript>
        <script type="text/javascript">
            // check if current JRE version is greater than 1.6.0
            //alert("versioncheck " + deployJava.versionCheck('1.6.0_10+'));
            if (deployJava.versionCheck('1.6.0_10+') == false) {
                userInput = confirm("You need the latest Java(TM) Runtime Environment. Would you like to update now?");
                if (userInput == true) {
                    // Set deployJava.returnPage to make sure user comes back to
                    // your web site after installing the JRE
                    deployJava.returnPage = location.href;
                    // install latest JRE or redirect user to another page to get JRE from.
                    deployJava.installLatestJRE();
                }
            }
            <!-- applet id can be used to get a reference to the applet object -->
            var attributes = { id:'myApplet',
                code:'ro.panzo.securedshares.Upload',
                archive:'secured-shares.applet-1.0.jar',
                width:400,
                height:300 };
            //var parameters = {jnlp_href: 'filebrowser.jnlp'};
            var parameters = {};
            deployJava.runApplet(attributes, parameters, '1.6');
        </script>--%>
    </div>

    <br/>

    <h3 id="securefiles">Availability</h3>

    <div id="content" style="text-indent:0px;">
        <INPUT type="radio" name="one-time" align="left"> Single download
        <br/>
        <INPUT type="radio" name="one-hour" align="left"> One Hour
        <br/>
        <INPUT type="radio" name="one-day" align="left"> One Day
        <br/>
        <INPUT type="radio" name="one-week" align="left"> One Week
        <br/>
        <INPUT type="radio" name="disabled" align="left"> Disable download
        <br/>
    </div>
    <br/>
</div>
</div>
<br/>
<br/><br/><br/>