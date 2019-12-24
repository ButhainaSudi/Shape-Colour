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

public class AgentObject extends Agent
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
            mt = MessageTemplate.MatchConversationId("reqObject");
            ACLMessage reqO = myAgent.receive(mt);

            if(reqO != null)
            {
                // load trained neural network saved with NeurophStudio (specify existing neural network file here)
                NeuralNetwork nnet = NeuralNetwork.createFromFile("objects_net.nnet");
                // get the image recognition plugin from neural network
                ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin)nnet.getPlugin(ImageRecognitionPlugin.class);
                try {
                    // image recognition is done here
                    HashMap<String, Double> output = imageRecognition.recognizeImage(new File(reqO.getContent())); // specify some existing image file here

                    double triangle = output.get("t1");
                    double circle = output.get("c1");
                    double rectangle = output.get("r1");

                    if(triangle>circle && triangle>rectangle) {
                        ACLMessage reply= reqO.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("triangle");
                        myAgent.send(reply);

                    }else if(circle>triangle && circle>rectangle) {
                        ACLMessage reply= reqO.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("circle");
                        myAgent.send(reply);

                    }else if(rectangle>triangle && rectangle>circle) {
                        ACLMessage reply= reqO.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent("rectangle");
                        myAgent.send(reply);
                    }
                    else {
                        block();
                    }

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