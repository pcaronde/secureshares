<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head>

  <meta content="text/html; charset=UTF-8" http-equiv="content-type">
  <link rel="stylesheet" type="text/css" href="css/main.css"></link>
  <link rel="stylesheet" type="text/css" href="css/mod_layout.css"></link>
  <script type="text/javascript" src="js/jquery.js"></script>
  <title>Secure Shares</title></head><body>
<%
    String error = request.getParameter("error");
%>

<div align="center">
	<img src="images/title6-sm.png" alt="secure shares" height="266" width="966" vspace="10" width="750" style="width: 1004px">

<div id="maincontainer">

<div id="contentwrapper">
<div id="contentcolumn">


<form method="post" action="j_security_check" name="login">

<table class="login" border="0" cellpadding="0" cellspacing="0">
    <tbody><tr>
        <td colspan="2" align="center">&nbsp;<%=error != null && error.length() > 0 ? "<span>Login failed !!</span>" : ""%></td>
    </tr>
    <tr>
        <td>Login: 
</td>
        <td><input id="username" name="j_username" value="" size="20" maxlength="45" type="text"></td>
    </tr>
    <tr>
        <td>Password:</td>
        <td><input name="j_password" size="20" maxlength="20" type="password"></td>
    </tr>
    <tr>
        <td colspan="2" align="right"><input name="login" value="Login" align="right" type="submit"></td>
    </tr>
</tbody></table>

</form>
</div>
</div>


<div id="footer">

	<br>A product of <b>PCConsultants Ltd &amp; Co KG</b> in cooperation with <b>E Majuscule S.A.R.L.</b>
	<br>For more information, please contact us at <i>secure(at)pcconsultants.de</i>
	<br>or visit: <a href="http://www.pcconsultants.de">www.pcconsultants.de</a><br/>
		<img src="images/PCFinal_200x113.jpg" alt="www.pcconsultants.de" height="113" width="200">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<img src="images/logo-emaj.png" alt="www.e-majuscule.fr" height="109" width="105">

</div>
</div>

</div>
<br>

</body></html>