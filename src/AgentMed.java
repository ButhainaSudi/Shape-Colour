import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class AgentMed extends Agent
{
	private AID aColor = new AID("agentC", AID.ISLOCALNAME);
	private AID aObj = new AID("agentO", AID.ISLOCALNAME);
	
	GuiPage gui = new GuiPage();
	AnswerGui ansG;
	
	protected void setup()
	{
		System.out.println(getAID().getLocalName() + " started.");

	    addBehaviour(new MedBehaviour());
	    addBehaviour(new MedMessage());
	        	
	}
	
	protected void takeDown()
	{
		System.out.println(getAID().getLocalName() + " terminated.");
	}
	
	private class MedBehaviour extends Behaviour
	{
		private boolean exit = false;
		public void action()
		{
			
		    String pic = gui.getPic();
		    System.out.println(pic);
		    
		   
		    
			if(pic != null) {
				
				//For Agent Object
				ACLMessage aclO = new ACLMessage(ACLMessage.REQUEST);
				aclO.addReceiver(aObj);
				aclO.setConversationId("reqObject");
				aclO.setContent(pic);
				myAgent.send(aclO);
				
				//For Agent Color
				ACLMessage aclC = new ACLMessage(ACLMessage.REQUEST);
				aclC.addReceiver(aColor);
				aclC.setConversationId("reqColor");
				aclC.setContent(pic);
				myAgent.send(aclC);
				exit=true;
				
		    }
		}
		
		public boolean done()
		{
			return exit;
		}
	}
	
	private class MedMessage extends Behaviour
	{
		private boolean exit = false;
		private MessageTemplate mtC;
		private MessageTemplate mtO;
		private String object; 
		private String color; 
		
		public void action()
		{
			int choose = gui.getChoose();
			mtO = MessageTemplate.MatchConversationId("reqObject");
			ACLMessage oResult = myAgent.receive(mtO);
			
			mtC = MessageTemplate.MatchConversationId("reqColor");
			ACLMessage cResult = myAgent.receive(mtC);


			if (oResult != null) {
				object = oResult.getContent();
			} else {
				block();
			}
			if(cResult !=null) {
				color = cResult.getContent();
			} else {
				block();
			}
			
			if(color !=null && object != null) {
				if(choose==1) {
					try {
						DisplayImage abc=new DisplayImage(gui.getPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "This is a "+color+" "+object);
					exit=true;
				}else if(choose==2) {



					String ansC=null;
					String ansO=null;
					ansG = new AnswerGui();
					while(ansC==null || ansO==null)
			        {
					    ansC = ansG.getColor();
						ansO = ansG.getObject();
						System.out.println(ansC);
						System.out.println(ansO);
			        }

					if(ansC.equalsIgnoreCase(color) && ansO.equalsIgnoreCase(object)) {
						JOptionPane.showMessageDialog(null, "Correcto! This is a "+color+" "+object);
			    	}
					else{
						JOptionPane.showMessageDialog(null, "Not correct! This is a "+color+" "+object+". Keep Learning.");
					}

					}
			  }
		  }
				
		
		
		public boolean done()
		{
			return exit;
		}
	}
	
	
}

