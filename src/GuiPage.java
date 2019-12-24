import javax.swing.*;

import jade.core.AID;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.*;
import java.io.File;

public class GuiPage implements ActionListener
{
    public String picture,path;
    
    int choose;
    JTextField tf1,tf2,tf3;
    JButton b1,b2,b3, b4, b5;
    GuiPage()
    {
        JFrame f= new JFrame();

        b1=new JButton("Learning");
        b1.setBounds(50,150,250,50);
        b2=new JButton("Testing");
        b2.setBounds(320,150,250,50);

        b1.addActionListener(this);
        b2.addActionListener(this);
        f.add(b1);f.add(b2);
        f.setSize(620,400);
        f.setLayout(null);
        f.setVisible(true);
    }
    
    String getPic() {
    	return picture;
    }
    String getPath() {
        return path;
    }
    
    int getChoose() {
    	return choose;
    }

    public void actionPerformed(ActionEvent e)
    {

        //if Learning Button in Clicked
        if(e.getSource()==b1)
        {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            int returnValue = jfc.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                String name = selectedFile.getPath();
                String pathName = selectedFile.getAbsolutePath();
                picture=name;
                choose=1;
                path=pathName;
                
            }

        }

        //if Testing Button is Clicked
        else if(e.getSource()==b2)
        {
        	 JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

             int returnValue = jfc.showOpenDialog(null);

             if (returnValue == JFileChooser.APPROVE_OPTION) {
                 File selectedFile = jfc.getSelectedFile();
                 String name = selectedFile.getPath();
                 picture=name;
                 choose=2;
                
             }
        }
    }

}