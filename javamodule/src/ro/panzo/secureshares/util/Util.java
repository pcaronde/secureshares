package ro.panzo.secureshares.util;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Util {
    private static Util ourInstance = new Util();
    private Logger log = Logger.getLogger(Util.class);

    public static Util getInstance() {
        return ourInstance;
    }

    private Util() {
    }

    public String getEnviromentValue(String env)
    {
        String rez = null;
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:/comp/env");
            rez = (String) envCtx.lookup(env);
        } catch (NamingException ex) {
            log.error(ex.getMessage(), ex);
        }
        return rez;
    }
}
