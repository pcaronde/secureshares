package ro.panzo.secureshares.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface Service {
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public String getName();
}
