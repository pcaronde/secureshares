<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
<title>Secure Shares</title>
    <link rel="stylesheet" type="text/css" href="css/layout.css" />
    <link rel="stylesheet" type="text/css" href="css/jquery.filetree.css">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.filetree.js"></script>
    <script type="text/javascript" src="js/secureshares.js"></script>
    <script type="text/javascript" src="http://www.java.com/js/deployJava.js"></script>

    <style type="text/css">
    .panel {
        width: 400px;
        height: 400px;
        border-top: solid 1px #BBB;
        border-left: solid 1px #BBB;
        border-bottom: solid 1px #FFF;
        border-right: solid 1px #FFF;
        background: #FFF;
        overflow: scroll;
        padding: 5px;
    }
</style>
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
        <center>
        <IMG src="images/title2-sm.png" alt="secure shares" width="450" height="100" vspace="10">
	    </center>
      </div>
      <div id="nav">
        <div class="hlist">
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
              <li><a href="#" onclick="goToUsers();">User Administration</a></li>
              <li><a href="#" onclick="goToFiles();">Files Management</a></li>
              <li>Reporting</li>
              <li>Preferences</li>
              <li>Help</li>
            </ul>
          </div>
        </div>
        <div id="col2">
          <div id="col2_content" class="clearfix">

          </div>
        </div>
        <div id="col3">
          <div id="col3_content" class="clearfix">

          </div>
          <!-- IE Column Clearing -->
          <div id="ie_clearing"> &#160; </div>
        </div>
      </div>
      <!-- begin: #footer -->
      <div id="footer">
        <h5>PCConsultants Ltd &amp; Co KG</h5>
      </div>
    </div>
  </div>
<script type="text/javascript">
    $(function(){
       goToHome();
    });
</script>
</body>
</html>

