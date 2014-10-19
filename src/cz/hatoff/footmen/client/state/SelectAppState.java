package cz.hatoff.footmen.client.state;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import cz.hatoff.footmen.control.SelectableControl;
import java.util.ArrayList;
import java.util.List;

public class SelectAppState extends AbstractAppState implements ActionListener {

    private Application app;
    private static String LEFT_CLICK = "Left Click";
    private List<Spatial> selectables = new ArrayList<Spatial>();
    private Spatial selected;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = app;
        InputManager inputManager = app.getInputManager();
        inputManager.addMapping(LEFT_CLICK, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(this, LEFT_CLICK);
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            
            if (selected != null) {
                selected.getControl(SelectableControl.class).setSelected(false);
                selected = null;
            }
            
            Vector2f click2d = app.getInputManager().getCursorPosition();
            Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
            Vector3f direction = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();

            Ray ray = new Ray(click3d, direction);
            CollisionResults collisionResults = new CollisionResults();
            for (Spatial spatial : selectables) {
                spatial.collideWith(ray, collisionResults);
            }

            if (collisionResults.size() > 0) {
                CollisionResult closest = collisionResults.getClosestCollision();

                Spatial geometry = closest.getGeometry();
                SelectableControl control = geometry.getControl(SelectableControl.class);

                while (control == null) {
                    geometry = geometry.getParent();
                    if (geometry == null) {
                        break;
                    }
                    control = geometry.getControl(SelectableControl.class);
                }

                if (control != null) {
                    control.setSelected(true);
                    selected = geometry;
                }
            }

        }
    }

    public void addSelectable(Node node) {
        selectables.add(node);
    }

    public Spatial getSelected() {
        return selected;
    }
}
