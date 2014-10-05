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
        String roles[] = this.getRole().split(",");
        boolean allow = false;
        for(String role : roles) {
            allow = ((HttpServletRequest) pageContext.getRequest()).isUserInRole(role.trim());
            if(allow){
                break;
            }
        }
        return allow ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    public int doEndTag() {
        return EVAL_PAGE;
    }
}
