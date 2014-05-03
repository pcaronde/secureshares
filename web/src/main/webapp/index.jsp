<%@ page import="ro.panzo.secureshares.util.IpInfoUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/security.tld" prefix="s" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="l" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <title>Secure Shares</title>
    <link rel="stylesheet" type="text/css" href="css/ss.css" />
    <script type="text/javascript" src="js/jquery.js"></script>
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
    String countryCode = (String)session.getAttribute("lang");
    if(countryCode == null){
        countryCode = IpInfoUtil.getInstance().getCountryCodeFromIp(request.getRemoteAddr());
        session.setAttribute("lang", countryCode);
    }
    long lastAction = 1;
    if(session.getAttribute("lastAction") != null){
        lastAction = (Long)session.getAttribute("lastAction");
    }
%>
<body>
<div id="page">
    <div id="header">
        <div id="languages">
            <a href="#" id="lng_de"><img src="images/de.png" width="16" height="11" alt="de"/></a>
            <a href="#" id="lng_gb"><img src="images/gb.png" width="16" height="11" alt="gb"/></a>
            <a href="#" id="lng_fr"><img src="images/fr.png" width="16" height="11" alt="fr"/></a>
        </div>
    </div>
    <div id="content">
        <div id="indexTop">
            <div id="indexNavStart"></div>
            <div id="indexNav">
                <ul>
                    <li class="active"><a href="#" id="lnkhome"><l:text key="menuHome"/></a></li>
                    <li><a href="#" id="lnkhow"><l:text key="menuHowItWorks"/></a></li>
                    <li><a href="#" id="lnksupport"><l:text key="menuSupport"/></a></li>
                    <li><a href="index.jsp?logout=yes"><l:text key="menuLogout"/></a></li>
                </ul>
            </div>
        </div>
        <div id="indexMiddle">
            <div id="indexLeftMenu">
                <ul>
                    <s:check role="admin">
                        <li><a href="#" id="lnkusers"><l:text key="menuLeftUserManagement"/></a></li>
                        <li><a href="#" id="lnkfiles"><l:text key="menuLeftFileManagement"/></a></li>
                    </s:check>
                    <li><a href="#" id="lnkupload"><l:text key="menuLeftUploadFiles"/></a></li>
                    <li><a href="#" id="lnkprofile"><l:text key="menuLeftProfile"/></a></li>
                </ul>
            </div>
            <div id="indexContainer"></div>
            <div id="indexClear"></div>
        </div>
        <div id="indexBottom"></div>
    </div>
    <div id="bottomBar"><span><l:text key="footerImprint"/> | <l:text key="footerTerms"/></span></div>
    <div id="footer">
        <br>A product of <b>PCConsultants Ltd &amp; Co KG</b> in cooperation with <b>E Majuscule S.A.R.L.</b>
        <br>For more information, please contact us at <i>secure(at)pcconsultants.de</i>
        <br>or visit: <a href="http://www.pcconsultants.de">www.pcconsultants.de</a><br/>
        <img src="images/pcconsultants-logo.png" alt="www.pcconsultants.de" height="53" width="88">&nbsp;&nbsp;&nbsp;
        <img src="images/emajuscule-logo.png" alt="www.e-majuscule.fr" height="53" width="51">
    </div>
</div>
<script type="text/javascript">
    $(function(){
        var lastAction = <%=lastAction%>;
        switch(lastAction){
            case 1 : goToHome(); break;
            case 2 : goToHowTo(); break;
            case 3 : goToSupport(); break;
            case 4 : goToUsers(); break;
            case 5 : goToFiles(); break;
            case 6 : goToUpload(); break;
            case 7 : goToProfile(); break;
        }
    });
    $('#lnkusers').bind('click', function(){
       goToUsers();
    });
    $('#lnkupload').bind('click', function(){
       goToUpload();
    });
    $('#lnkfiles').bind('click', function(){
       goToFiles();
    });
    $('#lnkhome').bind('click', function(){
       goToHome();
    });
    $('#lnkhow').bind('click', function(){
       goToHowTo();
    });
    $('#lnksupport').bind('click', function(){
       goToSupport();
    });
    $('#lnkprofile').bind('click', function(){
       goToProfile();
    });
    $('#lng_de').bind('click', function(){
        changeLanguage("de");
    });
    $('#lng_gb').bind('click', function(){
        changeLanguage("gb");
    });
    $('#lng_fr').bind('click', function(){
        changeLanguage("fr");
    });
</script>
</body>
</html>