import sftp.SExec;
import sftp.SFTP;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLDecoder;

public class F2Upload extends JApplet implements ActionListener,ch.ethz.ssh2.util.ProgressMonitor,Runnable
{
    private JButton browseButton;
    private JButton uploadButton;
    private SmilyeTextPane infoArea;
    private JLabel infoLabel;
    private JTextField userField;
    private JPasswordField passField;
    private JProgressBar progressBar;
    private JFileChooser fc;
    private File selectedFile;
    private JScrollPane sc;
    private boolean busy;
    private Thread uploadThred;
    private MessageFormat uploadProgressForm;
    private MessageFormat uploadDoneForm;

    private static final NumberFormat nf = NumberFormat.getInstance();
    private long count;
    private long max;
    private long start;
    private long resume;

    private String host;
    private long uploadMaxSize = 200 * 1024 * 1024;
    private boolean uploaded = false;

    private static ResourceBundle resources = ResourceBundle.getBundle("fis_fr");

    public void init()
    {
        // set system look and feel
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // init applet objects
        this.browseButton = new JButton(resources.getString("browse_button_label"));
        this.uploadButton = new JButton(resources.getString("upload_button_upload_label"));
        this.infoArea = new SmilyeTextPane();
        this.sc = new JScrollPane(this.infoArea);
        this.userField = new JTextField();
        this.passField = new JPasswordField();
        this.infoLabel = new JLabel("",JLabel.CENTER);
        this.progressBar = new JProgressBar(0,100);
        this.fc = new JFileChooser();
        this.fc.setDragEnabled(false);
        //this.fc.setAcceptAllFileFilterUsed(false);
        this.fc.setFileFilter(new FileFilter()
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
        });
        this.uploadProgressForm = new MessageFormat(resources.getString("upload_progress_message"));
        this.uploadDoneForm = new MessageFormat(resources.getString("upload_done"));

        // layout applet view
        this.layoutView();

        // add listeners for events
        this.browseButton.addActionListener( this );
        this.uploadButton.addActionListener( this );
        this.host = "localhost";//this.getHost();
        this.uploadMaxSize = 10 * 1024 * 1024;//;Long.parseLong(this.getEnv("UPLOAD_MAX_SIZE")) * 1024 * 1024;
    }

    private String getExtension(File f)
    {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1)
        {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

    public void actionPerformed(ActionEvent e)
    {
        if( e.getSource() == this.browseButton )
        {
            if( this.fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION )
            {
                this.selectedFile = this.fc.getSelectedFile();

                if( this.selectedFile != null )
                {
                    this.infoArea.setText("");
                    this.infoArea.append(resources.getString("file_info_area_directory"),true);
                    this.infoArea.append(selectedFile.getParent());
                    this.infoArea.appendEnter();
                    this.infoArea.append(resources.getString("file_info_area_name"),true);
                    this.infoArea.append(selectedFile.getName());
                    this.infoArea.appendEnter();
                    this.infoArea.append(resources.getString("file_info_area_size"),true);
                    this.infoArea.append(selectedFile.length()+"");
                    this.infoArea.append(resources.getString("file_info_area_bytes"),true);
                    this.infoArea.appendEnter();
                    this.infoArea.append(resources.getString("file_info_area_last_modified"),true);
                    this.infoArea.append( new Date( selectedFile.lastModified() )+"");
                }

                this.infoLabel.setText("");
                this.progressBar.setValue(0);
            }
        }
        else
        if( e.getSource() == this.uploadButton )
        {
            if( !this.isBusy() )
            {
                if( this.selectedFile != null )
                {
                    if(this.selectedFile.length() < uploadMaxSize)
                    {
                        if( this.userField.getText().trim().length() > 0 )
                        {
                            this.setBusy( true );
                            this.uploadThred = new Thread(this);
                            this.uploadThred.start();
                            this.uploaded = false;
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, resources.getString("login_error_message"),resources.getString("error_message_dialog_title"),JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, MessageFormat.format(resources.getString("file_to_big"), new Object[]{new Long(uploadMaxSize / (1024 * 1024))}),resources.getString("error_message_dialog_title"),JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,resources.getString("no_selected_file_error_message"),resources.getString("error_message_dialog_title"),JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                if( JOptionPane.showConfirmDialog(null,resources.getString("cancel_question_message"),resources.getString("cancel_message_dialog_title"),JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION )
                {
                    this.uploadThred.interrupt();
                    this.setBusy(false);
                }
            }
        }
    }

    public void run()
    {
        try
        {
            int    port = Integer.parseInt(resources.getString("port"));
            String user = this.userField.getText();
            String pass = String.valueOf( this.passField.getPassword() );
            String fileName = this.selectedFile.getName();
            SFTP.getInstance().put(host, port, user, pass, this.selectedFile.getParent(), fileName, this);
            if(this.uploaded)
            {
                this.scaning(fileName);
                this.backgroundTask(fileName);
            }

            this.setBusy(false);
            this.reset();
        }
        catch (final Exception e)
        {
            e.printStackTrace();
            SwingUtilities.invokeLater( new Runnable()
            {
                public void run()
                {
                    if( e.getMessage().indexOf("Auth fail") != -1 )
                    {
                        JOptionPane.showMessageDialog(null,resources.getString("upload_login_error_message"),resources.getString("error_message_dialog_title"),JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    if( e.getMessage().indexOf("UnknownHostException") != -1 )
                    {
                        JOptionPane.showMessageDialog(null,resources.getString("upload_host_error_message"),resources.getString("error_message_dialog_title"),JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,resources.getString("upload_error"),resources.getString("error_message_dialog_title"),JOptionPane.ERROR_MESSAGE);
                    }

                    setBusy( false );
                }
            });
        }
    }

    private void scaning(String fileName)
    {
        this.progressBar.setIndeterminate(true);
        this.infoLabel.setText(resources.getString("scaning_messages"));
        int    port = Integer.parseInt(resources.getString("port"));
        String user = this.userField.getText();
        String pass = String.valueOf( this.passField.getPassword() );
        String cmd = resources.getString("scan_cmd");
        String result = SExec.getInstance().exec(host, port, user, pass, cmd + " \"/home/" + user + "/f2_upload/" + fileName + "\"");
        this.progressBar.setIndeterminate(false);
        JOptionPane.showMessageDialog(null,/*resources.getString("scaning_succes_messages")*/result);
    }

    private void backgroundTask(String fileName)
    {
        this.progressBar.setIndeterminate(true);
        this.infoLabel.setText(resources.getString("finishing_message"));
        //int    port = Integer.parseInt(resources.getString("port"));
        String user = this.userField.getText();
        //String pass = String.valueOf( this.passField.getPassword() );
        //this.task(user, fileName);
        //SExec.getInstance().exec(host, port, user, pass, "nohup nice /usr/local/bin/send_encoder.sh ~/f2_upload/" + fileName + " > ~/f2_upload/" + fileName + ".out &");
        this.progressBar.setIndeterminate(false);
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
        this.infoLabel.setText(this.uploadProgressForm.format(new Object[] {this.count+"",nf.format(speed)}));
        this.progressBar.setValue((int)percent);

        return !(count >= max);
    }

    //count in kbit/sec
    public void end()
    {
        long time = System.currentTimeMillis() - this.start;
        double speed = (this.max - this.resume) * 1000.0 * 8.0/ (1024.0 * time);
        this.infoLabel.setText( this.uploadDoneForm.format(new Object[] {max+"",nf.format(time / 1000.0),nf.format(speed)}));
        this.progressBar.setValue(100);
        JOptionPane.showMessageDialog(null,resources.getString("upload_success_message"));
        this.setBusy(false);
        this.reset();
        this.uploaded = true;
    }

    private void layoutView()
    {
        JPanel view = (JPanel)this.getContentPane();
        this.setBackground(Color.white);
        view.setBackground(Color.white);
        view.setLayout( new BorderLayout(2,5) );
        view.setBorder( BorderFactory.createEmptyBorder(5,5,5,5));
        JPanel topPanel = new JPanel(new BorderLayout(2,2));
        topPanel.setOpaque( false );
        topPanel.add( this.browseButton ,BorderLayout.WEST);
        view.add( topPanel ,BorderLayout.NORTH);
        view.add( this.sc , BorderLayout.CENTER );
        JPanel bottomPanel = new JPanel( new BorderLayout(2,2) );
        JPanel bottomTopPanel = new JPanel( new BorderLayout(2,2));
        JPanel bottomTopCenterPanel = new JPanel( new GridLayout(1,4));
        bottomPanel.setOpaque( false );
        bottomTopPanel.setOpaque( false );
        bottomTopCenterPanel.setOpaque( false );
        bottomTopCenterPanel.add( new JLabel(resources.getString("username_label"),JLabel.CENTER));
        bottomTopCenterPanel.add( this.userField);
        bottomTopCenterPanel.add( new JLabel(resources.getString("password_label"),JLabel.CENTER));
        bottomTopCenterPanel.add( this.passField );
        bottomTopPanel.add( bottomTopCenterPanel , BorderLayout.CENTER );
        bottomTopPanel.add( this.uploadButton , BorderLayout.EAST );
        bottomPanel.add( bottomTopPanel , BorderLayout.NORTH );
        JPanel bottomStatusPane = new JPanel(new GridLayout(2,1));
        bottomStatusPane.setOpaque( false );
        bottomStatusPane.add( this.infoLabel);
        bottomStatusPane.add( this.progressBar );
        bottomPanel.add( bottomStatusPane , BorderLayout.SOUTH );
        bottomStatusPane.setBorder(BorderFactory.createTitledBorder(resources.getString("status")));
        view.add( bottomPanel ,BorderLayout.SOUTH);
        this.userField.setBorder( this.passField.getBorder());
        sc.setOpaque( false );
        sc.setBorder(BorderFactory.createTitledBorder(resources.getString("info_area_title")));
        this.infoArea.setEditable( false );
        this.progressBar.setStringPainted( true );
    }

    public boolean isBusy()
    {
        return busy;
    }

    public void setBusy(boolean busy)
    {
        this.busy = busy;

        if( this.busy )
        {
            this.browseButton.setEnabled(false);
            this.uploadButton.setText( resources.getString("upload_button_cancel_label"));
            this.sc.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.sc.getViewport().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.sc.getViewport().getView().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            this.infoArea.setEnabled(false);
            this.infoArea.setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
        }
        else
        {
            this.browseButton.setEnabled(true);
            this.uploadButton.setText(resources.getString("upload_button_upload_label"));
            this.getContentPane().setCursor(Cursor.getDefaultCursor());
            this.sc.setCursor(Cursor.getDefaultCursor());
            this.sc.getViewport().setCursor(Cursor.getDefaultCursor());
            this.sc.getViewport().getView().setCursor(Cursor.getDefaultCursor());
            this.infoArea.setEnabled(true);
        }
    }

    /**
     *
     */
    private void reset()
    {
        this.selectedFile = null;
        this.infoArea.setText("");
        this.infoLabel.setText(resources.getString("done_message"));
        this.progressBar.setValue(0);
    }

    private String getEnv(String env)
    {
        String result = "";
        try
        {
            String codeBase = this.getCodeBase().toString();
            URL triggerURL = new URL(codeBase + "../env.jsp");
            HttpURLConnection connection = (HttpURLConnection) triggerURL.openConnection();
            String postBody = "username=" + URLEncoder.encode("changeme", "UTF-8") +
                "&password=" + URLEncoder.encode("motu", "UTF-8") +
                    "&env=" + URLEncoder.encode(env, "UTF-8");
            connection.setRequestMethod("POST");
            // post the parameters
            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(postBody);
            wr.flush();
            wr.close();
            // now let's get the results
            connection.connect(); // throws IOException
            int responseCode = connection.getResponseCode(); // 200, 404, etc
            String responseMsg = connection.getResponseMessage(); // OK, Forbidden, etc
            if (responseCode == 200 && "OK".equals(responseMsg))
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer results = new StringBuffer();
                String oneline;
                while ((oneline = br.readLine()) != null)
                {
                    results.append(oneline);
                }
                br.close();
                result = URLDecoder.decode(results.toString(), "UTF-8");
            }
            else
            {
                System.out.println((responseMsg != null ? responseMsg : "null msg") + " - " + responseCode);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    private String getHost()
    {
        String result = "";
        try
        {
            String codeBase = this.getCodeBase().toString();
            URL triggerURL = new URL(codeBase + "../host.jsp");
            HttpURLConnection connection = (HttpURLConnection) triggerURL.openConnection();
            String postBody = "username=" + URLEncoder.encode("changeme", "UTF-8") +
                "&password=" + URLEncoder.encode("motu", "UTF-8");
            connection.setRequestMethod("POST");
            // post the parameters
            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(postBody);
            wr.flush();
            wr.close();
            // now let's get the results
            connection.connect(); // throws IOException
            int responseCode = connection.getResponseCode(); // 200, 404, etc
            String responseMsg = connection.getResponseMessage(); // OK, Forbidden, etc
            if (responseCode == 200 && "OK".equals(responseMsg))
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer results = new StringBuffer();
                String oneline;
                while ((oneline = br.readLine()) != null)
                {
                    results.append(oneline);
                }
                br.close();
                result = URLDecoder.decode(results.toString(), "UTF-8");
            }
            else
            {
                System.out.println((responseMsg != null ? responseMsg : "null msg") + " - " + responseCode);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    private String task(String user, String fileName)
    {
        String result = "";
        try
        {
            String codeBase = this.getCodeBase().toString();
            URL triggerURL = new URL(codeBase + "../task.jsp");
            HttpURLConnection connection = (HttpURLConnection) triggerURL.openConnection();
            String postBody = "username=" + URLEncoder.encode("changeme", "UTF-8") +
                "&password=" + URLEncoder.encode("motu", "UTF-8") +
                "&user=" + URLEncoder.encode(user, "UTF-8") +
                "&filename=" + URLEncoder.encode(fileName, "UTF-8");
            connection.setRequestMethod("POST");
            // post the parameters
            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(postBody);
            wr.flush();
            wr.close();
            // now let's get the results
            connection.connect(); // throws IOException
            int responseCode = connection.getResponseCode(); // 200, 404, etc
            String responseMsg = connection.getResponseMessage(); // OK, Forbidden, etc
            if (responseCode == 200 && "OK".equals(responseMsg))
            {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer results = new StringBuffer();
                String oneline;
                while ((oneline = br.readLine()) != null)
                {
                    results.append(oneline);
                }
                br.close();
                result = URLDecoder.decode(results.toString(), "UTF-8");
            }
            else
            {
                System.out.println((responseMsg != null ? responseMsg : "null msg") + " - " + responseCode);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }
}
