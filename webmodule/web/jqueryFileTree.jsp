<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.io.File,java.io.FilenameFilter,java.util.Arrays" %>
<%@ page import="ro.panzo.secureshares.util.Util" %>
<%
    String base = Util.getInstance().getEnviromentValue("REPOSITORY");
    String dir = request.getParameter("dir");
    if (dir == null) {
        return;
    }

    if (dir.charAt(dir.length() - 1) == '\\') {
        dir = dir.substring(0, dir.length() - 1) + "/";
    } else if (dir.charAt(dir.length() - 1) != '/') {
        dir += "/";
    }

    dir = base + java.net.URLDecoder.decode(dir, "UTF-8");

    if (new File(dir).exists()) {
        String[] files = new File(dir).list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.charAt(0) != '.';
            }
        });
        Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
        out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
        // All dirs
        for (String file : files) {
            if (new File(dir, file).isDirectory()) {
                out.print("<li class=\"directory collapsed\"><a href=\"#\" rel=\"" + dir.substring(base.length()) + file + "/\">"
                        + file + "</a></li>");
            }
        }
        // All files
        for (String file : files) {
            if (!new File(dir, file).isDirectory()) {
                int dotIndex = file.lastIndexOf('.');
                String ext = dotIndex > 0 ? file.substring(dotIndex + 1) : "";
                out.print("<li class=\"file ext_" + ext + "\"><a href=\"#\" rel=\"" + dir.substring(base.length()) + file + "\">"
                        + file + "</a></li>");
            }
        }
        out.print("</ul>");
    }
%>