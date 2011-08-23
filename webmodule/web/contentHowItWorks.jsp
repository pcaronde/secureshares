<!--  The following line breaks utf-8 support! -->
<!-- %@ page contentType="text/html;charset=UTF-8" language="java" % -->
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="l" %>
<%
    session.setAttribute("lastAction", 2);
%>
<div style="text-align: left">
    <h3><l:text key="contentHowItWorksTitle"/></h3>
    <br/>
    <img src="images/process_flow-575x167-white.png" alt="process flow" height="167" width="575">
    <h4><l:text key="contentHowItWorksP1"/></h4>
    <l:text key="contentHowItWorksP2"/>
    <br/>
    <p><l:text key="contentHowItWorksP3"/></p>
</div>