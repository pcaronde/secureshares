package ro.panzo.secureshares.servlet.tag;

import ro.panzo.secureshares.util.Invoker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class InvokerTag extends TagSupport {

    private Invoker invoker;
    private String var;
    private String target;
    private String method;

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int doStartTag() throws JspException
    {
        invoker = new Invoker();
        invoker.setTarget(((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute(this.getTarget()));
        invoker.setMethodName(this.getMethod());
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() {
        pageContext.setAttribute(this.getVar(), invoker.getInvoke());
        return EVAL_PAGE;
    }

    public Invoker getInvoker() {
        return invoker;
    }
}
