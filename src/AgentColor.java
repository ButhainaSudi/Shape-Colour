import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.exceptions.VectorSizeMismatchException;
import org.neuroph.imgrec.ImageRecognitionPlugin;

import java.util.HashMap;
import java.io.File;
import java.io.IOException;

public class AgentColor extends Agent
{
	
	
	protected void setup()
	{
		System.out.println(getAID().getLocalName() + " started.");
		
		addBehaviour(new GetMessage());
		
	}
	 
	private class GetMessage extends Behaviour
	{
		private boolean receiveLastMessage = false;
		private MessageTemplate mt;
		private String result;
		
		public void action()
		{
			mt = MessageTemplate.MatchConversationId("reqColor");
			ACLMessage reqC = myAgent.receive(mt);
			
			if(reqC != null)
			{	
				// load trained neural network saved with NeurophStudio (specify existing neural network file here)
		        NeuralNetwork nnet = NeuralNetwork.createFromFile("c.nnet");
		        // get the image recognition plugin from neural network
		        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin)nnet.getPlugin(ImageRecognitionPlugin.class);
		        try {
		              // image recognition is done here
		              HashMap<String, Double> output = imageRecognition.recognizeImage(new File(reqC.getContent())); // specify some existing image file here
		              
		              double orange = output.get("orange");
		              double red = output.get("red");
		              double green = output.get("green");
		              double blue = output.get("blue");
		              double yellow = output.get("yellow");
		              double purple = output.get("purple");
		              
		              double largest = orange;
		              String color = "orange";
		              

		              if(red>largest) {
		            	 largest = red;
			             color = "red";
		              }
		              if(green>largest) {
		            	 largest = green;
		            	 color = "green";
		              }
		              if(blue>largest) {
		             	 largest = blue;
		              	 color = "blue";
		              }
				      if(yellow>largest) {
			             largest = yellow;
			             color = "yellow";
				      }
					  if(purple>largest) {
				         largest = purple;
				         color = "purple";
					  }
		              
		              ACLMessage reply= reqC.createReply();
		  				reply.setPerformative(ACLMessage.INFORM);
		  				reply.setContent(color);
		  				myAgent.send(reply);
		              
		        } catch(IOException ioe) {
		            System.out.println("Error: could not read file!");
		        } catch (VectorSizeMismatchException vsme) {
		            System.out.println("Error: Image dimensions dont !");
		        }
		        
				
			}
			else
			{
				block();
			}
		}
		
		protected void takeDown()
		{
			System.out.println(getAID().getLocalName() + " terminated.");
		}
		
		public boolean done()
		{
			return receiveLastMessage;
		}
	}
}