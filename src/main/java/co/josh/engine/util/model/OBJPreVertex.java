package co.josh.engine.util.model;

public class OBJPreVertex {
    public boolean texcoord = false, normal = false;
    public int positionID, texcoordID, normalID;

    public OBJPreVertex(int positionID){
        this.positionID = positionID;
    }

    public OBJPreVertex(int positionID, int ID, boolean isTexcoord){
        this.positionID = positionID;

        if (isTexcoord) this.texcoordID = ID;
        else this.normalID = ID;

        texcoord = isTexcoord;
        normal = !isTexcoord;
    }

    public OBJPreVertex(int positionID, int texcoordID, int normalID){
        this.positionID = positionID;
        this.texcoordID = texcoordID;
        this.normalID = normalID;
        texcoord = true;
        normal = true;
    }
}
