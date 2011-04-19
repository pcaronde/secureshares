import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class SmilyeTextPane extends JTextPane implements MouseMotionListener,MouseListener
{
    private ActionListener clickOnWebLinkAction;
    private boolean antialiasing;

    public SmilyeTextPane( boolean antialiasing )
    {
        this.setEditable( false );
        this.setAutoscrolls( true );
        this.addMouseMotionListener( this );
        this.addMouseListener( this );
        this.setLocale( null );
        this.antialiasing = antialiasing;
    }

    public SmilyeTextPane()
    {
        this( false );
    }

    /**
     * Adauga un \n la sfarsitul documentului
     */
    public void appendEnter()
    {
        Document doc = this.getDocument();

        try
        {
            doc.insertString( doc.getLength() , "\n", null );
            this.setCaretPosition( doc.getLength() );
        }
        catch (BadLocationException ex)
        {
        }
    }

    /**
     * Adauga la continut parametru text.
     * Deasemenea schimba codurile pentru smiles cu iconurile corespunzatoare pe care le ia di clasa ro.obs.gui.Util
     * @param text
     */
    public void append( String text , boolean bold, boolean italic , Color color , String fontName , int size )
    {
        MutableAttributeSet atr = this.getInputAttributes();
        Document            doc = this.getDocument();

        if ( bold )
            StyleConstants.setBold( atr , true );
        if ( color != null )
            StyleConstants.setForeground( atr , color );
        if ( fontName != null )
            StyleConstants.setFontFamily( atr , fontName );
        if ( italic )
            StyleConstants.setItalic( atr , true );
        if ( size > 0 )
            StyleConstants.setFontSize( atr , size );

        this.replace( text , doc , atr );
        this.setCaretPosition( doc.getLength() );
        atr.removeAttributes( atr );
    }

    public void append( String text , boolean bold , boolean italic , Color color  )
    {
        this.append( text , bold , italic  , color , null , 0  );
    }

    public void append( String text , boolean bold , Color color  )
    {
        this.append( text , bold , false  , color , null , 0 );
    }

    public void append( String text , boolean bold , boolean italic  )
    {
        this.append( text , bold , italic  , null , null , 0 );
    }

    public void append( String text , boolean bold   )
    {
        this.append( text , bold  ,false , null  , null , 0 );
    }

    public void append( String text   )
    {
        this.append( text , false , false  , null , null , 0 );
    }

    /**
     * Adauga la coada documentului componentu primit ca parametru
     * @param componenet
     */
    public void appendComponent( JComponent componenet )
    {
        MutableAttributeSet atr = this.getInputAttributes();
        Document            doc = this.getDocument();

        try
        {
            atr.removeAttributes(atr);
            StyleConstants.setComponent(atr, componenet);
            doc.insertString(doc.getLength(), " ", atr);
            atr.removeAttributes(atr);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void paintComponent( Graphics g )
    {
        if( this.antialiasing )
        {
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        else
        {
            ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }

        super.paintComponent(g);
    }

    // metoda insereaza iconuri in documentul primit ca argument
    private void replace( String text ,Document doc , MutableAttributeSet atr)
    {
        StringTokenizer st      = new java.util.StringTokenizer(text, " ");
        String          str     = null;
        Icon            icon    = null;
        AttributeSet    copy    = atr.copyAttributes();

        while (st.hasMoreTokens())
        {
            str     = st.nextToken();
            icon    = null;//Util.getSmileyIcon( str );

            if ( icon != null )
            {
                try
                {
                    atr.removeAttributes(atr);
                    StyleConstants.setIcon(atr, icon);
                    doc.insertString(doc.getLength(), str , atr);
                    atr.removeAttributes(atr);
                    doc.insertString(doc.getLength(), " ", copy);
                }
                catch (Exception ex) {}
            }
            else
            {
                if (doc != null)
                {
                    try
                    {
                        if ( isWebAddress( str ) )
                        {
                            MutableAttributeSet  aux  = (MutableAttributeSet)atr.copyAttributes();
                            StyleConstants.setForeground( aux , Color.blue );
                            StyleConstants.setUnderline( aux , true );
                            StyleConstants.setBold( aux , true );
                            StyleConstants.setItalic( aux , false );
                            doc.insertString( doc.getLength(), str , aux );
                            doc.insertString( doc.getLength(), " " , copy );

                            aux.removeAttributes( aux );
                        }
                        else
                            doc.insertString( doc.getLength(), str+" ", copy );
                    }
                    catch (BadLocationException e) {}
                }
            }
        }
    }

    public void clear()
    {
        Document doc = this.getDocument();
        this.setText("");
    }

    public void mouseMoved(MouseEvent e)
    {
        this.updateCursor( e.getPoint() );
    }

    public void mouseReleased( MouseEvent e )
    {
        int offset  = this.viewToModel( e.getPoint() );
        String text = this.getWordAtOffset( offset );

        if ( text == null )
            return;

        if ( isWebAddress( text ) )
        {
            if ( this.clickOnWebLinkAction != null )
            {
                this.clickOnWebLinkAction.actionPerformed( new ActionEvent( this , 0 , text ));
            }
        }

        MutableAttributeSet atr = this.getInputAttributes();
        StyleConstants.setUnderline( atr , false );
    }

    public void mouseDragged(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    private void updateCursor( Point p)
    {
        int offset = this.viewToModel(p);
        String text = this.getWordAtOffset(offset);

        if (text == null)
            return;

        Cursor c = this.getCursor();

        if (text != null)
        {
            if (isWebAddress(text))
            {
                if (c.getType() != Cursor.HAND_CURSOR)
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            else
            {
                if (c.getType() != Cursor.DEFAULT_CURSOR)
                    this.setCursor(Cursor.getDefaultCursor());
            }
        }
        else
        if (c.getType() != Cursor.DEFAULT_CURSOR)
            this.setCursor( Cursor.getDefaultCursor() );

    }

    private String getWordAtOffset( int position )
    {
        try
        {
            String          word = null;
            StyledDocument  doc  = (StyledDocument)this.getDocument();
            Element         el   = doc.getParagraphElement( position );
            String          text = doc.getText( el.getStartOffset() , el.getEndOffset()-el.getStartOffset());
            StringTokenizer st   = new StringTokenizer(  text , " ");
            int             l    = 0;
            int             s    = el.getStartOffset();
            int             i    = 0;

            while ( st.hasMoreTokens() )
            {
                String token =  st.nextToken();

                if ( ( i == 0 ) || ( i == st.countTokens() -1 ) )
                    s += l;
                else
                    s +=(l+1);

                l            =  token.length();

                if ( ( position >= s ) &&  ( position <= ( s + l ) )  )
                    return token;

                i++;
            }

            return word;
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    private boolean isWebAddress( String address )
    {
        if ( address.startsWith("http://") )
        {
            return true;
        }
        else
        if ( address.startsWith("www") )
        {
            return Pattern.matches( "^(www\\.).*\\..*", address );
        }
        else
            return false;
    }

    public void setClickOnWebLinkAction(ActionListener clickOnWebLinkAction)
    {
        this.clickOnWebLinkAction = clickOnWebLinkAction;
    }

    public ActionListener getClickOnWebLinkAction()
    {
        return clickOnWebLinkAction;
    }

    public static void main(String[] args)
    {
        SmilyeTextPane st = new SmilyeTextPane( true );
        JFrame fr = new JFrame();
        fr.getContentPane().add( new JScrollPane( st ));
        fr.pack();
        st.append("asde d :) dnmawsida");
        fr.setDefaultCloseOperation(3);
        fr.setVisible( true );
    }
}
