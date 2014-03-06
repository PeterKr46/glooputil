package com.gmail.pkr4mer.glooputil.object;

import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.collider.Collider;
import com.gmail.pkr4mer.glooputil.object.scripting.BehaviourScript;
import com.gmail.pkr4mer.glooputil.position.Vector;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonas on 24.01.14.
 */
public abstract class Transform implements ObjectHolder, ScriptHolder, Rotatable
{
    private Vector position;
    private Vector forward;

    protected Scene scene;

    protected String tag;
    protected String name;

    private Transform parent;
    private final List<Transform> children;

    protected final double[] defaultScale;
    protected final double[] scale;

    protected final CaseInsensitiveMap<BehaviourScript> scripts;
    protected boolean valid = true;
    protected Collider collider;

    public Transform(Vector position, Scene scene, String tag, String name) throws Exception
    {
        forward = Vector.forward();
        this.position = position;
        defaultScale = new double[]{1,1,1};    // Create Default Scale Array
        scale = new double[]{1,1,1};
        this.scene = scene;                              // The scene the Transform is in
        this.tag = tag;                                // The tag of the Transform
        this.name = name;                               // The Transform's name
        scripts = new CaseInsensitiveMap<>();    // The Scripts of the Transform
        children = new ArrayList<>();
        registerInScene();
    }

    public Transform(Vector positition, Scene scene, Transform parent, String tag, String name) throws Exception
    {
        forward = Vector.forward();
        this.position = positition;
        defaultScale = new double[]{1,1,1};    // Create Default Scale Array
        scale = new double[]{1,1,1};
        this.parent = parent;
        this.scene = scene;                              // The scene the Transform is in
        this.tag = tag;                                // The tag of the Transform
        this.name = name;                               // The Transform's name
        scripts = new CaseInsensitiveMap<>();    // The Scripts of the Transform
        children = new ArrayList<>();
        registerInParent();
    }

    private void registerInScene() throws Exception
    {
        if(this.name == null)
        {
            name = scene.getAvailableName(this.getClass().getSimpleName().toLowerCase());
        }
        scene.register(this);
    }

    private boolean registerInParent() throws Exception {
        if(parent == null)
        {
            registerInScene();
            return true;
        }
        registerInScene();
        return parent.addChild(this);
    }



    public final Transform getParent()
    {
        return parent;
    }

    public final boolean setParent(ObjectHolder t) throws Exception {
        if( parent != null && t == null)
        {
            ObjectHolder p = parent;
            parent = null;
            p.removeChild(this);
            return true;
        }
        if(!(t instanceof Transform) || this.isParentOf(t)) return false;
        parent = (Transform) t;
        registerInParent();
        return true;
    }

    public final List<ObjectHolder> getChildren()
    {
        return new ArrayList<ObjectHolder>(children);
    }

    public final List<Collider> getAllColliders()
    {
        ArrayList<Collider> colls = new ArrayList<>();
        for( Transform child : children )
        {
            colls.addAll(child.getAllColliders());
        }
        if(hasCollider()) colls.add(this.collider);
        return colls;
    }

    public Collider getCollider()
    {
        return collider;
    }

    public boolean hasCollider()
    {
        return collider != null;
    }

    public abstract void createCollider();

    @Override
    public boolean removeChild(ObjectHolder child)
    {
        children.remove(child);
        try {
            child.setParent(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public final boolean isParentOf(ObjectHolder t)
    {
        return t.isChildOf(this);
    }

    public final boolean isChildOf(ObjectHolder other)
    {
        if(!(other instanceof Transform)) return false;
        Transform possibleParent = (Transform) other;
        Transform parent = getParent();
        while(parent != null)
        {
            if(parent.equals(other)) return true;
            parent = parent.getParent();
        }
        return false;
    }

    public final Vector getParentPosition()
    {
        if(getParent() != null) return parent.getAbsolutePosition();
        return Vector.zero();
    }

    public final String getName() // Returns the Transform's name
    {
        return name;
    }

    public final boolean setName(String name)
    {
        if(name.equalsIgnoreCase(getName())) return true;
        if(scene.rename(getName(), name))
        {
            this.name = name;
            return true;
        }
        return false;
    }

    public final ArrayList<String> getScripts()
    {
        return new ArrayList<>(scripts.keySet());
    }

    public final String getTag()  // Returns the Transform's tag
    {
        return tag;
    }

    public final void setTag(String tag)
    {
        this.tag = tag;
    }

    public final Scene getScene() // Returns the Transform's scene
    {
        return scene;
    }

    public final Vector getAbsolutePosition()
    {
        return position.clone();
    }

    /*
     * @deprecated Use getAbsolutePosition() or getLocalPosition() instead.
     */
    @Deprecated
    public final Vector getPosition() { return getAbsolutePosition(); }

    /*
     * @return Offset from Parent location or from Vector.zero() if no parent present.
     */
    public final Vector getLocalPosition()
    {
        return getParentPosition().difference(getAbsolutePosition());
    }

    // Fwd(->setPosition(Vector).
    // Sets the Transform's position to the given coordinates
    public final void setPosition(double pX, double pY, double pZ)
    {
        setPosition(new Vector(pX, pY, pZ));
    }

    // Sets the Transform's position to the given vector
    public final void setPosition(Vector pPosition)
    {
        Vector offset = position.differenceClone(pPosition);
        for(ObjectHolder t : getChildren())
        {
            if( t instanceof  Transform )
            {
                ((Transform) t).move(offset);
            }
        }
        position = pPosition;
    }

    public final void setRelativePosition(Vector relativePosition)
    {
        setPosition(getParentPosition().add(relativePosition));
    }

    public final double getX(){   // Returns the Transform's x-position
        return position.getX();
    }

    public final double getY(){   // Returns the Transform's y-position
        return position.getY();
    }

    public final double getZ(){   // Returns the Transform's z-position
        return position.getZ();
    }

    public final boolean destroy(){   // Deletes the Transform
        if(!valid) return false;
        valid = false;
        scene.destroy(getName());
        if(parent != null) parent.removeChild(this);
        destroyBackend();
        return true;
    }

    protected abstract boolean destroyBackend();

    public final void setScale(double pS){    // Sets the Transform's scale to the given size
        scale[0] = pS;
        scale[1] = pS;
        scale[2] = pS;
    }

    public final void setScale(double pX, double pY, double pZ){  // Sets the Transform's scale to the given arguments
        scale[0] = pX;
        scale[1] = pY;
        scale[2] = pZ;
    }

    public final void scale(double pX, double pY, double pZ)
    { // Scales the Transform by the given arguments
        scale[0] = getScaleX() * pX;
        scale[1] = getScaleY() * pY;
        scale[2] = getScaleZ() * pZ;
    }

    public final void move(double pX, double pY, double pZ)
    {  // Moves the Transform  by the given lengths in each direction
        move(new Vector(pX, pY, pZ));
    }

    public final void move(Vector pVec)
    {  // Moves the Transform along the vector pVec
        position.add(pVec);
        for(Transform t : getChildrenAsTransforms() )
        {
            t.move(pVec);
        }
    }

    public List<Transform> getChildrenAsTransforms()
    {
        List<Transform> children = new ArrayList<>();
        for( ObjectHolder oh : getChildren() )
        {
            if( oh instanceof Transform ) children.add((Transform) oh);
        }
        return children;
    }

    public final void runScripts()   // Activates the scripts
    {
        Map<Integer,List<BehaviourScript>> ordered = new HashMap<>();
        for(BehaviourScript gus : scripts.values())
        {
            if(!ordered.containsKey(gus.getRunPriority().getValue()))
            {
                ordered.put(gus.getRunPriority().getValue(),new ArrayList<BehaviourScript>());
            }
            ordered.get(gus.getRunPriority().getValue()).add(gus);
        }
        for( int lvl : BehaviourScript.RunPriority.getValues() )
        {
            if(ordered.containsKey(lvl))
            {
                for( BehaviourScript gus : ordered.get(lvl) )
                {
                    gus.onUpdate();
                }
            }
        }
    }

    public final boolean addScript(BehaviourScript script)  // Adds a new script to the Transform
    {
        if(scripts.containsKey(script.getTypeName())) return false;
        scripts.put(script.getTypeName(),script);
        script.setTransform(this);
        return true;
    }

    @Override
    public final boolean removeScript(BehaviourScript script) {
        if(!scripts.containsKey(script.getTypeName())) return false;
        scripts.remove(script.getTypeName());
        return true;
    }

    public final boolean hasScript(String typeName)
    {
        return scripts.containsKey(typeName);
    }

    public final BehaviourScript getScript(String typeName)
    {
        return scripts.get(typeName);
    }

    public final double getScaleX()
    {
        return scale[0];
    }

    public final double getScaleY()
    {
        return scale[1];
    }

    public final double getScaleZ()
    {
        return scale[2];
    }

    public final double getDefaultScaleX()
    {
        return defaultScale[0];
    }

    public final double getDefaultScaleY()
    {
        return defaultScale[1];
    }

    public final double getDefaultScaleZ()
    {
        return defaultScale[2];
    }

    public final Vector getForward()
    {
        return forward;
    }

    public final void setForward( Vector pForward )
    {
        forward = pForward;
        forward.normalize();
    }

    @Override
    public void rotate(double x, double y, double z)
    {
        rotate(new Vector(x,y,z));
    }

    @Override
    public abstract void rotate(Vector degr);

    @Override
    public abstract void rotateAround(double x1, double y1, double z1, double x2, double y2, double z2, float degrees);

    @Override
    public abstract void rotateAround(Vector position, Vector direction, float degrees);

    @Override
    public boolean addChild(ObjectHolder child)
    {
        return !(child == null || (!(child instanceof Transform)) || child.equals(getParent())) && children.add((Transform) child);
    }

    public final void fixedUpdate()
    {
        updateBackend();
        if(collider != null) collider.fixedUpdate();
        runScripts();
        for(Transform t : children)
        {
            t.fixedUpdate();
        }
        forward.normalize();
    }

    protected abstract void updateBackend();

    public final void lookAt(Vector pPosition)
    {
        forward = position.difference(pPosition);
        forward.normalize();
    }

    public void debug()
    {
        scene.log(getName() + " | " + this.toString());
        for(Transform c : getChildrenAsTransforms() )
        {
            c.debug(" - ");
        }
    }

    public void debug(String prefix)
    {
        scene.log(prefix + getName() + " | " + this.toString());
        for(Transform c : getChildrenAsTransforms() )
        {
            c.debug(prefix +" - ");
        }
    }
}