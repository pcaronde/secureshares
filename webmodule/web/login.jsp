<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head>


  <meta content="text/html; charset=ISO-8859-1" http-equiv="content-type">
  <link rel="stylesheet" type="text/css" href="css/main.css">
  <script type="text/javascript" src="js/jquery.js"></script><title>secure shares</title></head><body>&lt;%
    String error = request.getParameter("error");
%&gt;

<br>
<br>
<div align="center"><img src="images/title3.png" alt="secure shares" height="166" vspace="10" width="750"></div>
<h1 style="text-align: center;"></h1>

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>

<form method="post" action="j_security_check" name="login">
<center>
<table class="login" border="0" cellpadding="0" cellspacing="0">
    <tbody><tr>
        <td colspan="2" align="center">&nbsp;&lt;%=error != null &amp;&amp; error.length() &gt; 0 ? "<span style="">Login failed !!</span>" : ""%&gt;</td>
    </tr>
    <tr>
        <td>Login <br>
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
</center>
</form>
<br>
<br>
<br>
<br>
<br>
<br>
<p>
</p><div style="" align="center">
<br>
<br>
<br>
<br>

<img src="images/PCFinal_200x113.jpg" alt="www.pcconsultants.de" height="113" width="200">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="images/logo-emaj.png" alt="www.e-majuscule.fr" height="109" width="105">

<br>A product of <b>PCConsultants Ltd &amp; Co KG</b> in cooperation with <b>E Majuscule S.A.R.L.</b>
<br>For more information, please contact us at 
<br><i>secure(at)pcconsultants.de</i>
<br>or visit: <a href="http://www.pcconsultants.de">www.pcconsultants.de</a>
</div>
<br>
<script type="text/javascript">
    $(function(){
        $('#username').focus();
    });
</script>
</body></html>