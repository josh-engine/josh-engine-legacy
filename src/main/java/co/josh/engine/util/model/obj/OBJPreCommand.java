package co.josh.engine.util.model.obj;

import co.josh.engine.util.model.obj.mtl.Material;

public class OBJPreCommand {
    public PreCommandType preCommandType;
    public boolean texcoord = false, normal = false;
    public int positionID, texcoordID, normalID;

    public Material mtl;

    public OBJPreCommand(Material mtl){
        this.preCommandType = PreCommandType.MATERIAL;
        this.mtl = mtl;
    }

    public OBJPreCommand(int positionID){
        this.preCommandType = PreCommandType.VERTEX;
        this.positionID = positionID;
    }

    public OBJPreCommand(int positionID, int ID, boolean isTexcoord){
        this.preCommandType = PreCommandType.VERTEX;
        this.positionID = positionID;

        if (isTexcoord) this.texcoordID = ID;
        else this.normalID = ID;

        texcoord = isTexcoord;
        normal = !isTexcoord;
    }

    public OBJPreCommand(int positionID, int texcoordID, int normalID){
        this.preCommandType = PreCommandType.VERTEX;
        this.positionID = positionID;
        this.texcoordID = texcoordID;
        this.normalID = normalID;
        texcoord = true;
        normal = true;
    }
}
