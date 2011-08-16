package ro.panzo.secureshares.tld;

import org.apache.log4j.Logger;
import ro.panzo.secureshares.db.DBManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class I18NTag extends TagSupport {
    private Logger log = Logger.getLogger(I18NTag.class);

    protected String key;

    /**
     *
     */
    public int doStartTag() throws JspException {
        try {
            String language = (String)pageContext.getSession().getAttribute("lang");
            String value = DBManager.getInstance().getI18NFor(language, this.getKey());
            pageContext.getOut().print(value);
        } catch (Exception ex) {
            log.debug(ex.getMessage(), ex);
        }
        return SKIP_BODY;
    }

    public int doEndTag() {
        return EVAL_PAGE;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}