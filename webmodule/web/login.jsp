<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head>

  <meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
  <link rel="stylesheet" type="text/css" href="css/main.css" />
  <script type="text/javascript" src="js/jquery.js"></script>
  <title>secure shares</title>

</head>
<%
    String error = request.getParameter("error");
%>
<body>
<br/>
<br/>
<DIV align="center"><IMG src="images/title2.png" alt="secure shares" width="750" height="166" vspace="10"></DIV>
<h1 style="text-align: center;"></h1>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

<form method="post" action="j_security_check" name="login">
<center>
<table border="0" cellpadding="0" cellspacing="0" class="login">
    <tr>
        <td colspan="2" align="center">&nbsp;<%=error != null && error.length() > 0 ? "<span style=\"color:red;\">Login failed !!</span>" : ""%></td>
    </tr>
    <tr>
        <td>Your email address:</td>
        <td><input type="text" id="username" name="j_username" value="" size="20" maxlength="45"></td>
    </tr>
    <tr>
        <td>Your password:</td>
        <td><input type="password" name="j_password" size="20" maxlength="20"></td>
    </tr>
    <tr>
        <td colspan="2" align="right"><input align="right" type="submit" name="login" value="Login"></td>
    </tr>
</table>
</center>
</form>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<P>
<DIV style="text-color: grey;" align="center">
<br/>
<br/>
<br/>
<br/>

<IMG src="images/PCFinal_200x113.jpg" alt="www.pcconsultants.de" width="200" height="113">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<IMG src="images/logo-emaj.png" alt="www.e-majuscule.fr" width="105" height="109"></TD>

<br/>A product of <b>PCConsultants Ltd &amp; Co KG</b> in cooperation with <b>E Majuscule S.A.R.L.</b>
<br/>For more information, please contact us at 
<br/><i>secure(at)pcconsultants.de</i>
<br/>or visit: <A href="http://www.pcconsultants.de">www.pcconsultants.de</A>
</DIV>
<br>
<script type="text/javascript">
    $(function(){
        $('#username').focus();
    });
</script>
</body></html>