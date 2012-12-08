import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import jadex.bridge.service.types.clock.IClockService;
import jadex.commons.SimplePropertyObject;
import jadex.extension.envsupport.environment.IEnvironmentSpace;
import jadex.extension.envsupport.environment.ISpaceProcess;
import jadex.extension.envsupport.environment.space2d.Space2D;
import jadex.extension.envsupport.math.Vector2Int;

public class BDIMap extends SimplePropertyObject implements ISpaceProcess {

	public static int[][] map;
	
    @Override
    public Object getProperty(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set getPropertyNames() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasProperty(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setProperty(String arg0, Object arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void execute(IClockService arg0, IEnvironmentSpace arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void shutdown(IEnvironmentSpace arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void start(IClockService arg0, final IEnvironmentSpace space) {
        // TODO Auto-generated method stub
        // Initialize the field.
        Space2D grid = (Space2D) space;
        int sizex = grid.getAreaSize().getXAsInteger();
        int sizey = grid.getAreaSize().getYAsInteger();

        
        map = new int[][]{
                {1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1},
                {0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1},
                {0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                {0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                {0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                {0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                {0, 1, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                {1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1},
                {1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0}};

        for (int i = 0; i < sizex; i++) {
            for (int j = i + 1; j < sizey; j++) {
                int temp = map[i][j];
                map[i][j] = map[j][i];
                map[j][i] = temp;
            }
        }

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[y][x] == 1) {
                    Map props = new HashMap();
                    props.put("road", true);
                    props.put(Space2D.PROPERTY_POSITION, new Vector2Int(y, x));
                    grid.createSpaceObject("cell", props, null);
                }
            }
        }

        for (int i = 0; i < sizex; i++) {
            for (int j = i + 1; j < sizey; j++) {
                int temp = map[i][j];
               map[i][j] = map[j][i];
                map[j][i] = temp;
            }
        }

    }
}
