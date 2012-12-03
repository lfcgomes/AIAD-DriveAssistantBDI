import jadex.bdi.runtime.IGoal;
import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import algorithms.ShortestPath.Node;

public class CheckAccident extends Plan {

	@Override
	public void body() {

		ISpaceObject acidente = (ISpaceObject) this.getParameter("target").getValue();
		IVector2 pos_acidente = (IVector2) acidente.getProperty(Space2D.PROPERTY_POSITION);
		ISpaceObject myself = (ISpaceObject)getBeliefbase().getBelief("myself").getFact();
		
		
		System.out.println("Encontrei acidente em "+pos_acidente);
		acidente.setProperty("state", "avoid");
		
		IGoal go_target = createGoal("go");
		
		go_target.getParameter("pos").setValue(myself.getProperty("position"));
		
		setAccident(new Node(pos_acidente.getXAsInteger(),pos_acidente.getYAsInteger()));
		
		dispatchTopLevelGoal(go_target);
		
	}
	
	protected void setAccident(Node n) {
        Utils.map[n.y][n.x] = 0;
    }
}
