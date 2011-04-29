package ro.panzo.secureshares;

import com.jcraft.jsch.SftpProgressMonitor;
import netscape.javascript.JSObject;
import ro.panzo.secureshares.util.SFTPPutTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;

public class Upload  extends JApplet implements ActionListener, Runnable, SftpProgressMonitor{

    private JButton browseButton;
    private JButton uploadButton;
    private SmilyeTextPane infoArea;
    private JLabel infoLabel;
    private JProgressBar progressBar;
    private JFileChooser fc;
    private File selectedFile;
    private JScrollPane sc;
    private boolean busy;
    private Thread uploadThred;
    private SFTPPutTask task;
    private boolean uploadCanceled;

    private String host = "mobile3.pcconsultants.de";
    private int port = 22;
    private String username = "guesftp";
    private String password = "GuesFTP123!";
    private long uploadMaxSize = 20 * 1024 * 1024;

    private static final NumberFormat nf = NumberFormat.getInstance();
    private long count;
    private long max;
    private long start;
    private long resume;

    public void init() {
        // set system look and feel
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // init applet objects
        this.browseButton = new JButton("Browser");
        this.uploadButton = new JButton("Upload");
        this.infoArea = new SmilyeTextPane();
        this.sc = new JScrollPane(this.infoArea);
        this.infoLabel = new JLabel("",JLabel.CENTER);
        this.progressBar = new JProgressBar(0,100);
        this.fc = new JFileChooser();
        this.fc.setDragEnabled(false);
        //this.fc.setAcceptAllFileFilterUsed(false);
        /*this.fc.setFileFilter(new FileFilter()
        {
            public boolean accept(File f)
            {
                if (f.isDirectory())
                {
                    return true;
                }
                String extension = getExtension(f);
                return extension != null && ("mov".equals(extension) || "mp4".equals(extension));
            }

            public String getDescription()
            {
                return "mov & mp4 files";
            }
        });*/
        //this.uploadProgressForm = new MessageFormat("upload_progress_message");
        //this.uploadDoneForm = new MessageFormat("upload_done");

        // layout applet view
        this.layoutView();

        // add listeners for events
        this.browseButton.addActionListener( this );
        this.uploadButton.addActionListener( this );
        this.uploadCanceled = false;
    }

    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource() == this.browseButton ){
            if( this.fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
                this.selectedFile = this.fc.getSelectedFile();
                if( this.selectedFile != null ){
                    this.infoArea.setText("");
                    this.infoArea.append("Folder: ",true);
                    this.infoArea.append(selectedFile.getParent());
                    this.infoArea.appendEnter();
                    this.infoArea.append("File: ",true);
                    this.infoArea.append(selectedFile.getName());
                    this.infoArea.appendEnter();
                    this.infoArea.append("Size: ",true);
                    this.infoArea.append(String.valueOf(selectedFile.length()));
                    this.infoArea.append("bytes", true);
                    this.infoArea.appendEnter();
                    this.infoArea.append("Last Modified: ", true);
                    this.infoArea.append( new Date( selectedFile.lastModified() ).toString());
                }
                this.infoLabel.setText("");
                this.progressBar.setValue(0);
            }
        } else if( e.getSource() == this.uploadButton ){
            if( !this.isBusy() ) {
                if( this.selectedFile != null ) {
                    if(this.selectedFile.length() < uploadMaxSize) {
                        this.setBusy( true );
                        this.uploadThred = new Thread(this);
                        this.uploadThred.start();
                    } else {
                        JOptionPane.showMessageDialog(null, "File too big", "Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    this.showError("No file selected");
                }
            } else {
                if( JOptionPane.showConfirmDialog(null,"Are you sure you want to cancel?", "Upload Cancel", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION ) {
                    this.uploadCanceled = true;
                    if(this.task != null){
                        this.uploadButton.setEnabled(false);
                        task.cancel();
                    }
                    if(this.uploadThred != null){
                        this.uploadThred.interrupt();
                    }
                }
            }
        }
    }

    public void init(int op, String src, String dest, long max)
    {
        this.infoLabel.setText("INIT");

        this.max = max;
        this.count = 0;
        this.resume = -1;
        this.start = System.currentTimeMillis();
        nf.setGroupingUsed(true);
        nf.setMinimumFractionDigits(1);
        nf.setMaximumFractionDigits(1);
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
        String text = MessageFormat.format("{0} octets sent - speed {1} kb/s", String.valueOf(this.count), nf.format(speed));
        this.infoLabel.setText(text);
        this.progressBar.setValue((int)percent);

        return !(count >= max);
    }

    //count in kbit/sec
    public void end()
    {
        long time = System.currentTimeMillis() - this.start;
        double speed = (this.max - this.resume) * 1000.0 * 8.0/ (1024.0 * time);
        String text = MessageFormat.format("Finished. - {0} octets in {1} sec ({2}kb/s)", String.valueOf(max), nf.format(time / 1000.0), nf.format(speed));
        this.infoLabel.setText(text);
        this.progressBar.setValue(100);
        JOptionPane.showMessageDialog(null,"Success!");
        this.setBusy(false);
        this.reset();
    }

    public void run()
    {
        try {
            String fileName = this.selectedFile.getName();
            task = new SFTPPutTask(host, port, username, password, this);
            task.execute(this.selectedFile.getParent(), fileName, "secureshares");
            this.setBusy(false);
            this.reset();
            this.refreshRemoteBrowser();
        } catch (final Exception e) {
            if(!this.uploadCanceled){
                SwingUtilities.invokeLater( new Runnable() {
                    public void run()
                    {
                        if( e.getMessage().indexOf("Auth fail") != -1 ) {
                            JOptionPane.showMessageDialog(null, "Invalid user name or password!" , "Error", JOptionPane.ERROR_MESSAGE);
                        } else if( e.getMessage().indexOf("UnknownHostException") != -1 ){
                            JOptionPane.showMessageDialog(null, "Unknown host", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Upload file error", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        setBusy( false );
                    }
                });
            } else {
                this.uploadCanceled = false;
                setBusy( false );
                this.uploadButton.setEnabled(true);
            }
        }
    }

    private void refreshRemoteBrowser() {
        try{
            JSObject window = JSObject.getWindow(this);
            window.eval("showTree()");
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void showError(Object message){
        JOptionPane.showMessageDialog(null, message , "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void layoutView() {
        JPanel view = (JPanel)this.getContentPane();
        this.setBackground(Color.white);
        view.setBackground(Color.white);
        view.setLayout( new BorderLayout(2,5) );
        view.setBorder( BorderFactory.createEmptyBorder(5,5,5,5));
        JPanel browsePanel = new JPanel(new BorderLayout(2,2));
        browsePanel.setOpaque(false);
        browsePanel.add(this.browseButton, BorderLayout.WEST);
        view.add( browsePanel ,BorderLayout.NORTH);
        view.add( this.sc , BorderLayout.CENTER );
        JPanel bottomPanel = new JPanel( new BorderLayout(2,2) );
        JPanel uploadPanel = new JPanel( new BorderLayout(2,2));
        bottomPanel.setOpaque( false );
        uploadPanel.setOpaque(false);
        uploadPanel.add(this.uploadButton, BorderLayout.EAST);
        bottomPanel.add( uploadPanel , BorderLayout.SOUTH );
        JPanel statusPane = new JPanel(new GridLayout(2,1));
        statusPane.setOpaque(false);
        statusPane.add(this.infoLabel);
        statusPane.add(this.progressBar);
        bottomPanel.add(statusPane, BorderLayout.NORTH);
        statusPane.setBorder(BorderFactory.createTitledBorder("Status"));
        view.add(bottomPanel, BorderLayout.SOUTH);
        sc.setOpaque( false );
        sc.setBorder(BorderFactory.createTitledBorder("File Info"));
        this.infoArea.setEditable( false );
        this.progressBar.setStringPainted( true );
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;

        if( this.busy ) {
            this.browseButton.setEnabled(false);
            this.uploadButton.setText("Cancel");
            this.sc.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.sc.getViewport().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.sc.getViewport().getView().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.infoArea.setEnabled(false);
            this.infoArea.setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
        } else {
            this.browseButton.setEnabled(true);
            this.uploadButton.setText("Upload");
            this.getContentPane().setCursor(Cursor.getDefaultCursor());
            this.sc.setCursor(Cursor.getDefaultCursor());
            this.sc.getViewport().setCursor(Cursor.getDefaultCursor());
            this.sc.getViewport().getView().setCursor(Cursor.getDefaultCursor());
            this.infoArea.setEnabled(true);
        }
    }

    private void reset() {
        this.selectedFile = null;
        this.infoArea.setText("");
        this.infoLabel.setText("Done");
        this.progressBar.setValue(0);
    }
}
