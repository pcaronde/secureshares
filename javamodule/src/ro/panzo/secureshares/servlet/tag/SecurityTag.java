package ro.panzo.secureshares.servlet.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SecurityTag extends TagSupport {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int doStartTag() throws JspException
    {
        return ((HttpServletRequest)pageContext.getRequest()).isUserInRole(this.getRole()) ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    public int doEndTag() {
        return EVAL_PAGE;
    }
}
