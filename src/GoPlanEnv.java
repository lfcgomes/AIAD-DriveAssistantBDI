import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.ISpaceAction;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector1;
import jadex.extension.envsupport.math.IVector2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import algorithms.PathFinder;
import algorithms.PathFinder.Node;

/**
 *  Go to a specified position.
 */
public class GoPlanEnv extends Plan
{
	/**
	 *  The plan body.
	 */
	public void body()
	{
		Grid2D env = (Grid2D)getBeliefbase().getBelief("env").getFact();

		//Get the position of the final destination, specified in the XML
		ISpaceObject fd = env.getSpaceObjectsByType("finaldestination")[0];
		ISpaceObject hm = env.getSpaceObjectsByType("homebase")[0];

		IVector2 target = (IVector2)fd.getProperty("position");

		IVector2 aux = (IVector2)hm.getProperty("position");

		ISpaceObject myself = (ISpaceObject)getBeliefbase().getBelief("myself").getFact();

		myself.setProperty("position", aux);

		System.out.println("MINHA POSICAO "+myself.getProperty("position"));;
		System.out.println("TARGET "+target.toString());



		//Calculate de the new direction
		String dir = null;
		


			

		while(!target.equals(myself.getProperty(Space2D.PROPERTY_POSITION)))
		{

			IVector2 mypos = (IVector2)myself.getProperty(Space2D.PROPERTY_POSITION);
			
			PathFinder pf = new PathFinder(Utils.map);
			List<Node> nodes = pf.compute(new PathFinder.Node(((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger(),
					((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger()),
					new PathFinder.Node(target.getXAsInteger(),
							target.getYAsInteger()));
			
			System.out.println("\nPOSICAO "+mypos+ " NODE:" + nodes.get(2));
			Node next = nodes.get(2);
			
			//IVector1 md = env.getShortestDirection(mypos.getX(), target.getX(), true);
			int md= 0;
			if(mypos.getYAsInteger() == next.y){
				if(mypos.getXAsInteger() < next.x)
					md = 1;
			}
			else
				md = 0;
					
			System.out.println("DIRECCAO" +md);



			System.out.println("NODES "+nodes);
			/*switch(md.getAsInteger()){
			case 1:
				dir = GoAction.RIGHT;
				break;
			case -1:
				dir = GoAction.LEFT;
				break;
			default:
				md = env.getShortestDirection(mypos.getY(), target.getY(), false);
				switch(md.getAsInteger()){
				case 1:
					dir = GoAction.DOWN;
					break;
				case -1:
					dir = GoAction.UP;
					break;
				}
			}*/
			
			switch(md){
			case 1:
				dir = GoAction.RIGHT;
				break;
			case -1:
				dir = GoAction.LEFT;
				break;
			default:
				if(mypos.getXAsInteger() == next.x){
					if(mypos.getYAsInteger() < next.y)
						md = 1;
				}
				switch(md){
				case 1:
					dir = GoAction.DOWN;
					break;

				}
			}

			//Inform what is the new direction and execute space action
			Map params = new HashMap();
			params.put(GoAction.DIRECTION, dir);
			params.put(ISpaceAction.OBJECT_ID, env.getAvatar(getComponentDescription()).getId());
			SyncResultListener srl	= new SyncResultListener();
			env.performSpaceAction("go", params, srl); 
			srl.waitForResult();
		}
	}
}

