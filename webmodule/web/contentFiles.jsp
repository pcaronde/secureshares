<h2>Manage Files and Shares</h2>

<div id="container">
    <h3>Secure Uploads</h3>
		<P>Choose files from your local computer and securely upload to the server</P>
    <div id="upload" style="text-indent:0px;">
        <object
                width="400"
                height="300" classid="clsid:8AD9C840-044E-11D1-B3E9-00805F499D93"
                codebase="http://java.sun.com/products/plugin/autodl/jinstall-1_4-windows-i586.cab#Version=1,4,0,0">
            <param name="codebase_lookup" value="false">
            <param name="id" value="myApplet"/>
            <param name="code" value="ro.panzo.secureshares.Upload"/>
            <param name="archive" value="secure-shares.applet-1.0.jar"/>
            <param name="cache_option" value="No">
            <!--[if !IE]> -->
            <object width="400" height="300" type="application/x-java-applet">
                <param name="codebase_lookup" value="false">
                <param name="id" value="myApplet"/>
                <param name="code" value="ro.panzo.secureshares.Upload"/>
                <param name="archive" value="secure-shares.applet-1.0.jar"/>
                <param name="cache_option" value="No">
            </object>
            <!-- <![endif]-->
        </object>
    </div>

    <br/>

    <h3 id="securefiles">Availability</h3>
		<P>Set the time your file(s) will be available to external users</P>
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