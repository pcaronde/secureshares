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
    </div>

    <br/>

    <h3 id="securefiles">Availability</h3>

    <div id="content" style="text-indent:0px;">
        <input type="radio" name="downloadtype" align="left" checked="checked"> Single download<br/>
        <input type="radio" name="downloadtype" align="left"> One Hour<br/>
        <input type="radio" name="downloadtype" align="left"> One Day<br/>
        <input type="radio" name="downloadtype" align="left"> One Week<br/>
        <input type="radio" name="downloadtype" align="left"> Disable download<br/>
    </div>
    <br/>
</div>
<br/>
<br/><br/><br/>