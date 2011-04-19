<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<title>Secure Shares</title>
    <link rel="stylesheet" type="text/css" href="css/menu.css"  />
    <link rel="stylesheet" type="text/css" href="css/main.css" />
    <link href="css/my_layout.css" rel="stylesheet" type="text/css" />
    <!--[if lte IE 7]>
    <link href="css/patch_my_layout.css" rel="stylesheet" type="text/css" />
    <![endif]-->
    <script type="text/javascript" src="js/jquery.js"></script>
</head>
<%
    if ("yes".equals(request.getParameter("logout"))) {
        if (request.getUserPrincipal() != null) {
            session.invalidate();
            response.sendRedirect("index.jsp");
            return;
        }
    }
%>
<body>

<div class="page_margins">
    <div class="page">
      <div id="header">
        <div id="topnav">
          <!-- start: skip link navigation -->
          <a class="skip" title="skip link" href="#navigation">Skip to the navigation</a><span class="hideme">.</span>
          <a class="skip" title="skip link" href="#content">Skip to the content</a><span class="hideme">.</span>
          <!-- end: skip link navigation -->
        </div>
        <center>
        <IMG src="images/title2-sm.png" alt="secure shares" width="450" height="100" vspace="10">
	    </center>
      </div>
      <div id="nav">
        <!-- skiplink anchor: navigation -->
        <a id="navigation" name="navigation"></a>
        <div class="hlist">
          <!-- main navigation: horizontal list -->
          <ul>
            <li class="active"><strong>Home</strong></li>
            <li><a href="#">Products</a></li>
            <li><a href="#">Support</a></li>
            <li><a href="#">Shares</a></li>
            <li><a href="index.jsp?logout=yes">Logout</a></li>
          </ul>
        </div>
      </div>
      <div id="main">
        <div id="col1">
          <div id="col1_content" class="clearfix">
            <!-- add your content here -->
            <ul TYPE="circle">
              <li><a href="#" onclick="gotoUsers();">User Administration</a></li>
              <li><a href="#" onclick="gotoFiles();">Files Management</a></li>
              <li>Reporting</li>
              <li>Preferences</li>
              <li>Help</li>
            </ul>
          </div>
        </div>
        <div id="col3">
          <div id="col3_content" class="clearfix">
            <!-- add your content here -->


          </div>
          <!-- IE Column Clearing -->
          <div id="ie_clearing"> &#160; </div>
        </div>
      </div>
      <!-- begin: #footer -->
      <div id="footer">
        <h5>PCConsultants Ltd &amp; Co KG</h5>Layout based on <a href="http://www.yaml.de/">YAML</a>
      </div>
    </div>
  </div>
<script type="text/javascript">

    gotoUsers=function(){
        $.get('contentUsers.jsp', function(data){
          $('#col3_content').html(data);
       });
    }

    gotoFiles=function(){
        $.get('contentFiles.jsp', function(data){
          $('#col3_content').html(data);
       });
    }

    gotoHome=function(){
        $.get('contentHome.jsp', function(data){
          $('#col3_content').html(data);
       });
    }

    $(function(){
       gotoHome();
    });
</script>
</body>
</html>

