<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html><head>

  <meta content="text/html; charset=UTF-8" http-equiv="content-type">
  <link rel="stylesheet" type="text/css" href="css/ss.css"/>
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <title>Secure Shares</title></head>
<body>
<%
    String error = request.getParameter("error");
%>

<div id="page">
    <div id="header"></div>
    <div id="content">
        <div id="loginTop"></div>
        <div id="loginMiddle">
            <form method="post" action="j_security_check" name="login" id="frmlogin">
                <table class="login" border="0" cellpadding="3" cellspacing="0">
                    <tbody>
                        <tr>
                            <td colspan="3" align="center" style="color: #ebb84b">&nbsp;<%=error != null && error.length() > 0 ? "<span>Login failed !!</span>" : ""%></td>
                        </tr>
                        <tr>
                            <td align="right">Login</td>
                            <td align="left"><input id="username" name="j_username" value="" size="20" maxlength="45" type="text"></td>
                            <td>&nbsp;</td>
                            
                        </tr>
                        <tr>
                            <td align="right">Password</td>
                            <td align="left"><input name="j_password" size="20" maxlength="20" type="password"></td>
                            <td align="center"><input type="image" src="images/btLogin.png" alt="Login" /></td>
                            
                        </tr>
                    </tbody>
                </table>
            </form>

        </div>
        
        <div id="loginBottom"></div>
    </div>
    <div id="bottomBar"><span>imprint | agb</span></div>
    <div id="footer">
        <br>An opensource porject sponsored by <b>E Majuscule S.A.R.L.</b>
        <br>For more information, please visit us on <a href="https://sourceforge.net/projects/secureshares/">SourceForge</a>
        <img src="images/emajuscule-logo.png" alt="www.e-majuscule.fr" height="53" width="51">
    </div>
</div>
<script type="text/javascript">
    $('#username').focus();
</script>
</body>
</html>
