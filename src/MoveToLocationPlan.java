import jadex.bdi.planlib.PlanFinishedTaskCondition;
import jadex.bdi.runtime.Plan;
import jadex.extension.envsupport.environment.AbstractTask;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceObject;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.IVector2;
import jadex.extension.envsupport.math.Vector1Double;
import jadex.extension.envsupport.math.Vector2Int;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import algorithms.ShortestPath;
import algorithms.ShortestPath.Node;

import java.util.Iterator;
import java.util.Set;


//FIXME para j� n�o � usado

public class MoveToLocationPlan extends Plan {

   
    public void body() {
    	
        ISpaceObject myself = (ISpaceObject) getBeliefbase().getBelief("myself").getFact();
        IVector2 dest = (IVector2) getParameter("destination").getValue();
        getBeliefbase().getBelief("poi").setFact(getParameter("destination").getValue());
        
    }
}
