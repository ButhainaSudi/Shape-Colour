import javax.swing.*;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.*;
import java.io.File;

public class AnswerGui implements ActionListener
{
	JTextField tf1,tf2,tf3;
    JButton b;
    JLabel t1,t2;
    String color,object;
    AnswerGui()
    {
        JFrame f= new JFrame();


        //For Entering Testing Answers
        t1=new JLabel("What is this color : ");
        t1.setBounds(100,50,250,50);
        tf1=new JTextField();
        tf1.setBounds(250,60,250,40);
        t2=new JLabel("What is this object : ");
        t2.setBounds(100,150,250,50);
        tf2=new JTextField();
        tf2.setBounds(250,160,250,40);
        b=new JButton("Insert");
        b.setBounds(250,250,100,50);

        b.addActionListener(this);
        f.add(tf1);f.add(tf2);f.add(t1);f.add(t2);f.add(b);
        f.setSize(620,400);
        f.setLayout(null);
        f.setVisible(true);
    }

    
    String getColor() {
    	return color;
    }
    
    String getObject() {
    	return object;
    }
    
    
    public void actionPerformed(ActionEvent e)
    {
    	
        //if Learning Button in Clicked
        if(e.getSource()==b)
        {
        	   color=tf1.getText();
        	   object=tf2.getText();
        	   

        }
    }
    
    
    
}