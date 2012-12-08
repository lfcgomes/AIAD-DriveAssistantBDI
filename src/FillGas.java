import jadex.bdi.runtime.IGoal;
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

import EDU.oswego.cs.dl.util.concurrent.TimeoutException;
import algorithms.ShortestPath;
import algorithms.ShortestPath.Node;

public class FillGas extends Plan {

	@Override
	public void body() {

		Grid2D env = (Grid2D)getBeliefbase().getBelief("env").getFact();

		ISpaceObject dummy_accident = (ISpaceObject) this.getParameter("target").getValue();
		//dummy_accident.setProperty("state", "avoid");

		ISpaceObject myself = (ISpaceObject)getBeliefbase().getBelief("myself").getFact();

		myself.setProperty("accident", "");
		if(dummy_accident!=null)
			dummy_accident.setProperty("dummy", "no");

		ISpaceObject[] acidentes = env.getSpaceObjectsByType("accident");
		ISpaceObject[] gas_stations = env.getSpaceObjectsByType("gas_station");

		HashMap <Integer,ISpaceObject> bombas = new HashMap <Integer,ISpaceObject>();

		//Verificar quais as bombas de gasolina mais próximas
		for(int x=0; x < gas_stations.length; x++){
			//if((Integer)gas_stations[x].getProperty("gas") > 0){

			IVector2 actual = (IVector2)gas_stations[x].getProperty("position");

			List<Node> postos = GetPath((IVector2) myself.getProperty(Space2D.PROPERTY_POSITION),actual);

			if(postos != null)
				bombas.put(postos.size(),gas_stations[x]);					
			//}
		}

		List bb = new LinkedList(); // Collections.sort() recebe como parametro um list  
		bb.addAll(bombas.keySet());// buscando os valores no Map  
		Collections.sort(bb);

		ISpaceObject closer_gas = null;
		IVector2 station_pos = null;
		if(!bb.isEmpty())
		{
			closer_gas = (ISpaceObject)bombas.get(bb.get(0)); //bombas mais próximas
			station_pos = (IVector2)closer_gas.getProperty("position");
		}		

		//vai abastecer


		System.out.println("Indo abastecer em "+station_pos);
		String dir=null;
		boolean acidente=false;

		while(!station_pos.equals(myself.getProperty(Space2D.PROPERTY_POSITION)))
		{

			IVector2 mypos = (IVector2)myself.getProperty(Space2D.PROPERTY_POSITION);

			List<Node> nodes = GetPath(mypos,station_pos);

			Node next = nodes.get(2);

			for(int x=0;x<acidentes.length;x++){
				//Só se o acidente não estiver evitado
				if(acidentes[x].getProperty("state").equals("notavoid")){// && !acidente){ 

					IVector2 aci_pos = (IVector2)acidentes[x].getProperty("position");
					Node aci = new Node(aci_pos.getXAsInteger(),aci_pos.getYAsInteger());

					if(GoPlanEnv.equal(aci,next))
					{
						//criar dropcondition no fill

						System.out.println("Encontrei acidente");
						myself.setProperty("accident", acidentes[x]);
						IGoal check = createGoal("check");
						myself.setProperty("tipo","gasolina");
						check.getParameter("target").setValue(acidentes[x]);
						acidente=true;

					}
				}
			}
		
			
			if(!acidente){
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

		myself.setProperty("gas", (Integer)myself.getProperty("gas")+(Integer)closer_gas.getProperty("gas"));
		System.out.println("Abasteci: "+closer_gas.getProperty("gas")+"; tenho: "+myself.getProperty("gas"));
		IGoal go_target = createGoal("go");
		go_target.getParameter("pos").setValue(myself.getProperty(Space2D.PROPERTY_POSITION));

		dispatchTopLevelGoal(go_target);

	}
	public  List<Node> GetPath(IVector2 start,IVector2 end){

		ShortestPath sp = new ShortestPath(Utils.map);
		List<Node> nodes = sp.compute(new ShortestPath.Node(start.getXAsInteger(),start.getYAsInteger()),
				new ShortestPath.Node(end.getXAsInteger(),end.getYAsInteger())
				);
		return nodes;		
	}
}
