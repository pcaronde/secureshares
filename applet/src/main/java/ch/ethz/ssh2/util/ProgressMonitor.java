package ch.ethz.ssh2.util;

public interface ProgressMonitor {

    public abstract void init(int op, String src, String dest, long max);

    public abstract boolean count(long coun);

    public abstract void end();

    public static final int PUT = 0;
    public static final int GET = 1;
}
