import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.ISpaceAction;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Grid2D;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector1;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector1Double;
import jadex.extension.envsupport.math.Vector2Int;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import algorithms.ShortestPath;
import algorithms.ShortestPath.Node;

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

		//Get the position of the final destination and homebase, specified in the XML
		ISpaceObject fd = env.getSpaceObjectsByType("finaldestination")[0];
		ISpaceObject hm = env.getSpaceObjectsByType("homebase")[0];

		IVector2 target = (IVector2)fd.getProperty("position");

		IVector2 ini = (IVector2)hm.getProperty("position");

		ISpaceObject[] locations = env.getSpaceObjectsByType("pointofinterest");
		ISpaceObject myself = (ISpaceObject)getBeliefbase().getBelief("myself").getFact();

		myself.setProperty("position", ini); //posição inicial

		//Calculate de the new direction
		String dir = null;

		//TEM DE PASSAR EM TODOS OS PONTOS ANTES DE TERMINAR
		while(!target.equals(myself.getProperty(Space2D.PROPERTY_POSITION))){

			HashMap <Integer,ISpaceObject> shorter = new HashMap <Integer,ISpaceObject>();
			/*VERIFICAR QUAL A PRÓXIMA VISITA 
			QUE SERÁ O PONTO DE INTERESSE MAIS PROXIMO DO PONTO INICIAL*/
			for(int x=0; x < locations.length; x++){
				if(locations[x].getProperty("status").equals("notvisited")){
					ShortestPath sp = new ShortestPath(Utils.map);
					IVector2 actual = (IVector2)locations[x].getProperty("position");
					
					List<Node> nodes = sp.compute(
							new ShortestPath.Node(((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger(),
							((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger()),
							new ShortestPath.Node(actual.getXAsInteger(),
									actual.getYAsInteger())
							);
					shorter.put(nodes.size(),locations[x]);
				}
			}
			
			//ORDERNAR HASHMAP
			List ll = new LinkedList(); // Collections.sort() recebe como parametro um list  
			ll.addAll(shorter.keySet());// buscando os valores no Map  
			Collections.sort(ll);

			
			ISpaceObject next_visit = null;
			//VE SE AINDA TEM PONTOS PARA VISITAR
			if(!ll.isEmpty()){
				
				ISpaceObject aux = (ISpaceObject)shorter.get(ll.get(0));
				IVector2 posicao = (IVector2)aux.getProperty("position");
				
				//Caminho entre o ponto de interesse a visitar
				//e o destino
				ShortestPath sp = new ShortestPath(Utils.map);
				List<Node> nodes = sp.compute(
						new ShortestPath.Node(posicao.getXAsInteger(),
						posicao.getYAsInteger()),
						new ShortestPath.Node(target.getXAsInteger(),
								target.getYAsInteger())
						);
				

				
				int total = nodes.size()+(Integer)ll.get(0);
				
				myself.setProperty("time", 200);
				
				
				System.out.println(myself.getProperty("time"));
	
				
				
				
				next_visit = (ISpaceObject)shorter.get(ll.get(0));
				System.out.println("PROXIMA VISITA "+aux.getProperty("position"));
			}
			else //SENAO VAI PARA FINAL DESTINATION
				next_visit = fd;
			
			
					
			
			
			if(!next_visit.equals(fd)){ //AINDA TEM PONTOS PARA VISITAR
				if(next_visit.getProperty("status").equals("notvisited")){

					System.out.println("Indo para "+next_visit.getProperty("type"));

					while(!next_visit.getProperty("position").equals(myself.getProperty(Space2D.PROPERTY_POSITION)))
					{
						IVector2 mypos = (IVector2)myself.getProperty(Space2D.PROPERTY_POSITION);
						IVector2 actualtarget = (IVector2)next_visit.getProperty("position");


						ShortestPath sp = new ShortestPath(Utils.map);
						List<Node> nodes = sp.compute(
								new ShortestPath.Node(((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger(),
								((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger()),
								new ShortestPath.Node(actualtarget.getXAsInteger(),
										actualtarget.getYAsInteger())
								);

						Node next = nodes.get(2);

						int md= 0;
						if(mypos.getYAsInteger() == next.y){ //HORIZONTAL
							if(mypos.getXAsInteger() < next.x)
								md = 1; // DIREITA
							else if(mypos.getXAsInteger() > next.x)
								md = -1; //ESQUERDA
						}
						else
							md = 0;


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
									md = 1; //BAIXO
								else if(mypos.getYAsInteger() > next.y)
									md = -1; //CIMA
							}
							switch(md){
							case 1:
								dir = GoAction.DOWN;
								break;
							case -1:
								dir = GoAction.UP;
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

					next_visit.setProperty("status", "visited");
					System.out.println("Visitei: "+ next_visit.getProperty("type"));
				}
			}
			else //FINAL
			{
				System.out.println("Indo para o fim");
				while(!target.equals(myself.getProperty(Space2D.PROPERTY_POSITION)))
				{

					IVector2 mypos = (IVector2)myself.getProperty(Space2D.PROPERTY_POSITION);

					ShortestPath sp = new ShortestPath(Utils.map);
					List<Node> nodes = sp.compute(
							new ShortestPath.Node(((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getXAsInteger(),
									((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION)).getYAsInteger()),
							new ShortestPath.Node(target.getXAsInteger(),
									target.getYAsInteger())
							);

					Node next = nodes.get(2);

					int md= 0;
					if(mypos.getYAsInteger() == next.y){ //HORIZONTAL
						if(mypos.getXAsInteger() < next.x)
							md = 1; // DIREITA
						else if(mypos.getXAsInteger() > next.x)
							md = -1; //ESQUERDA
					}
					else
						md = 0;

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
								md = 1; //BAIXO
							else if(mypos.getYAsInteger() > next.y)
								md = -1; //CIMA
						}
						switch(md){
						case 1:
							dir = GoAction.DOWN;
							break;
						case -1:
							dir = GoAction.UP;
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

		System.out.println("\n\nAcabou percurso\n\n");
		for(int x=0;x<locations.length;x++)
			System.out.println(locations[x].getProperty("type")+ " "+ locations[x].getProperty("status"));
	}
}