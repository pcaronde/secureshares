package ro.panzo.secureshares.util;

import com.jcraft.jsch.SftpProgressMonitor;

import java.text.NumberFormat;

public class UploadProgressMonitor implements SftpProgressMonitor
{
    private static final NumberFormat nf = NumberFormat.getInstance();
    private long count;
    private long max;
    private long start;
    private long resume;

    public void init(int op, String src, String dest, long max)
    {
        this.max = max;
        this.count = 0;
        this.resume = -1;
        this.start = System.currentTimeMillis();
        nf.setGroupingUsed(true);
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(1);
        System.out.println((op == SftpProgressMonitor.GET ? "get: " : "put: ") + src + " (" + max + ") bytes");
    }

    //count in kbit/sec
    public boolean count(long count)
    {
        if(resume == -1)
        {
            resume = count;
        }
        this.count += count;
        long time = System.currentTimeMillis() - this.start;
        double speed = (time != 0) ? (this.count - this.resume) * 1000.0 * 8.0 / (1024.0 * time) : 0.0;
        double percent = this.count * 100.0 / max;
        System.out.println("Completed " + this.count +
                "(" + nf.format(percent) + "%) bytes out of " + max + " bytes - " +
                "(" + nf.format(speed) + " kb/s).");
        return !(count >= max);
    }

    //count in kbit/sec
    public void end()
    {
        long time = System.currentTimeMillis() - this.start;
        double speed = (this.max - this.resume) * 1000.0 * 8.0 / (1024.0 * time);
        System.out.println("DONE - " + max + " bytes in " +
                nf.format(time / 1000.0) + " sec. - " +
                "(" + nf.format(speed) + "KB/s)");
    }
}
