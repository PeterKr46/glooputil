package com.gmail.pkr4mer.glooputil.object;

import com.gmail.pkr4mer.glooputil.Scene;
import com.gmail.pkr4mer.glooputil.object.scripting.GUScript;
import com.gmail.pkr4mer.glooputil.position.Axis;
import com.gmail.pkr4mer.glooputil.position.Vector;
import com.gmail.pkr4mer.util.CaseInsensitiveMap;
import com.gmail.pkr4mer.util.Pair;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jonas on 24.01.14.
 */
public abstract class Transform implements ObjectHolder, ScriptHolder, Rotatable
{     // This class contains all methods from GLObjekt so that GLObjekt isn't needed anymore. It also si able to interact with other glooputil classes.

    private Vector position;
    private Vector rotation;

    protected Scene scene;

    protected String tag;
    protected String name;

    private Transform parent;
    private final List<Transform> children;

    protected final double[] defaultScale;
    protected final double[] scale;

    protected final CaseInsensitiveMap<GUScript> scripts;
    protected boolean valid = true;

    public Transform(Vector position, Scene scene, String tag, String name) throws Exception
    {
        this.position = position;
        this.rotation = new Vector(0,0,0);
        defaultScale = new double[]{1,1,1};    // Create Default Scale Array
        scale = new double[]{1,1,1};
        this.scene = scene;                              // The scene the Transform is in
        this.tag = tag;                                // The tag of the Transform
        this.name = name;                               // The Transform's name
        scripts = new CaseInsensitiveMap<>();    // The Scripts of the Transform
        children = new ArrayList<>();
        registerInScene();
    }

    public Transform(Vector positition, Scene scene, Transform parent, String tag, String name)
    {
        this.position = positition;
        this.rotation = new Vector(0,0,0);
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
        scene.register(this);
    }

    private boolean registerInParent()
    {
        return parent.addChild(this);
    }

    public final void setRotation(@NotNull Vector v)
    {
        this.rotation = v;
        fixRotation();
    }

    public final Vector getRotation()
    {
        return rotation.clone();
    }

    private void fixRotation()
    {
        while(this.rotation.getX() >= 360) this.rotation.setX(this.rotation.getX() - 360);
        while(this.rotation.getY() >= 360) this.rotation.setY(this.rotation.getY() - 360);
        while(this.rotation.getZ() >= 360) this.rotation.setZ(this.rotation.getZ() - 360);

        while(this.rotation.getX() < 0) this.rotation.setX(this.rotation.getX() + 360);
        while(this.rotation.getY() < 0) this.rotation.setY(this.rotation.getY() + 360);
        while(this.rotation.getZ() < 0) this.rotation.setZ(this.rotation.getZ() + 360);
    }

    public final Transform getParent()
    {
        return parent;
    }

    public final boolean setParent(ObjectHolder t)
    {
        if( parent != null && t == null)
        {
            ObjectHolder p = parent;
            parent = null;
            p.removeChild(this);
            return true;
        }
        if(!(t instanceof Transform) || this.isParentOf(t)) return false;
        parent = (Transform) t;
        return true;
    }

    public final List<ObjectHolder> getChildren()
    {
        return new ArrayList<ObjectHolder>(children);
    }

    @Override
    public boolean removeChild(ObjectHolder child)
    {
        children.remove(child);
        child.setParent(null);
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
        return position;
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
        position = pPosition;
    }

    public final void setRelativePosition(Vector relativePosition)
    {
        position = getParentPosition().add(relativePosition);
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
        return scene.destroy(getName());
    }

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
    }

    public final void runScripts()   // Activates the scripts
    {
        Map<Integer,List<GUScript>> ordered = new HashMap<>();
        for(GUScript gus : scripts.values())
        {
            if(!ordered.containsKey(gus.getRunPriority().getValue()))
            {
                ordered.put(gus.getRunPriority().getValue(),new ArrayList<GUScript>());
            }
            ordered.get(gus.getRunPriority().getValue()).add(gus);
        }
        for( int lvl : GUScript.RunPriority.getValues() )
        {
            if(ordered.containsKey(lvl))
            {
                for( GUScript gus : ordered.get(lvl) )
                {
                    gus.fixedUpdate();
                }
            }
        }
    }

    public final boolean addScript(GUScript script)  // Adds a new script to the Transform
    {
        if(scripts.containsKey(script.getTypeName())) return false;
        scripts.put(script.getTypeName(),script);
        script.setGUObject(this);
        return true;
    }

    @Override
    public final boolean removeScript(GUScript script) {
        if(!scripts.containsKey(script.getTypeName())) return false;
        scripts.remove(script.getTypeName());
        return true;
    }

    public final boolean hasScript(String typeName)
    {
        return scripts.containsKey(typeName);
    }

    public final GUScript getScript(String typeName)
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
        return Vector.Rotation.getForward(rotation);
    }

    @Override
    public void rotate(double x, double y, double z)
    {
        rotate(new Vector(x,y,z));
    }

    @Override
    public void rotate(Vector degr)
    {
        rotation.add(degr);
        Vector hinge = getAbsolutePosition();
        for(Transform t : children)
        {
            //System.out.println(t.getName() + "@" + t.getAbsolutePosition());
            //System.out.println(getName() + "@" + getAbsolutePosition());
            //System.out.println("Hinge -> " + hinge.differenceClone(t.getAbsolutePosition()));
            t.rotateAround(hinge, Axis.X.toVector(),-(float)degr.getX());
            t.rotateAround(hinge, Axis.Y.toVector(),-(float)degr.getY());
            t.rotateAround(hinge, Axis.Z.toVector(),-(float)degr.getZ());
        }
        fixRotation();
    }

    @Override
    public void rotateAround(double x1, double y1, double z1, double x2, double y2, double z2, float degrees)
    {
        rotateAround(new Vector(x1,y1,z1),new Vector(x2,y2,z2),degrees);
    }

    @Override
    public void rotateAround(Vector position, Vector direction, float degrees)
    {
        Pair<Vector,Vector> result =
                Vector.Rotation.simulateRotateAround(
                        getRotation(),
                        getAbsolutePosition(),
                        position,
                        direction,
                        degrees
                );
        setPosition(result.getA());
        setRotation(result.getB());
        fixRotation();
        for( Transform child : children )
        {
            child.rotateAround(position,direction,degrees);
        }
    }

    @Override
    public final boolean addChild(ObjectHolder child)
    {
        return !(child == null || (!(child instanceof Transform)) || child.equals(getParent())) && children.add((Transform) child);
    }

    public final void fixedUpdate()
    {
        updateBackend();
        runScripts();
        for(Transform t : children)
        {
            t.fixedUpdate();
        }
    }

    protected abstract void updateBackend();
}