<%
    String language = request.getParameter("l");
    if(language != null && language.length() > 0){
        session.setAttribute("lang", language);
    }
%>