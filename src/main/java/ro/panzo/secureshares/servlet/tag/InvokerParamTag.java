package ro.panzo.secureshares.servlet.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class InvokerParamTag extends TagSupport {

    private String type;
    private Object value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int doStartTag() throws JspException{
        return SKIP_BODY;
    }

    public int doEndTag() {
        InvokerTag invkTag = (InvokerTag)findAncestorWithClass(this, InvokerTag.class);
        if(invkTag != null){
            invkTag.getInvoker().setAddParamClasses(type);
            invkTag.getInvoker().setAddParamObjects(value);
        }
        return EVAL_PAGE;
    }
}
