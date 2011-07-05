<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <title>Secure Shares</title>
    <link rel="stylesheet" type="text/css" href="css/ss.css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.filetree.js"></script>
    <script type="text/javascript" src="js/secureshares.js"></script>
    <script type="text/javascript" src="http://www.java.com/js/deployJava.js"></script>
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
<div id="page">
    <div id="header"></div>
    <div id="content">
        <div id="indexTop">
            <div id="indexNavStart"></div>
            <div id="indexNav">
                <ul>
                    <li class="active"><a href="#">Home</a></li>
                    <li><a href="#">How it Works?</a></li>
                    <li><a href="#">Support</a></li>
                    <li><a href="index.jsp?logout=yes">Logout</a></li>
                </ul>
            </div>
        </div>
        <div id="indexMiddle">

        </div>
        <div id="indexBottom"></div>
    </div>
    <div id="bottomBar"><span>imprint | agb</span></div>
    <div id="footer">
        <br>A product of <b>PCConsultants Ltd &amp; Co KG</b> in cooperation with <b>E Majuscule S.A.R.L.</b>
        <br>For more information, please contact us at <i>secure(at)pcconsultants.de</i>
        <br>or visit: <a href="http://www.pcconsultants.de">www.pcconsultants.de</a><br/>
        <img src="images/pcconsultants-logo.png" alt="www.pcconsultants.de" height="53" width="88">&nbsp;&nbsp;&nbsp;
        <img src="images/emajuscule-logo.png" alt="www.e-majuscule.fr" height="53" width="51">
    </div>
</div>
</body>
</html>
<!--
<div class="page_margins">
    <div class="page">
      <div id="nav">
        <div class="hlist">
          <ul>
            <li class="active"><strong>Home</strong></li>
            <li><a href="#">How it Works</a></li>
            <li><a href="#">Support</a></li>
            <li><a href="index.jsp?logout=yes">Logout</a></li>
          </ul>
        </div>
      </div>
      <div id="main">
        <div id="col1">
          <div id="col1_content" class="clearfix">
            <ul TYPE="circle">
              <s:check role="admin">
                <li><a href="#" onclick="goToUsers();">User Administration</a></li>
              </s:check>
              <li><a href="#" onclick="goToFiles();">File Share Management</a></li>
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
          <div id="ie_clearing"> &#160; </div>
        </div>
      </div>

    </div>
  </div>
<script type="text/javascript">
    $(function(){
       goToHome();
    });
</script>
-->


