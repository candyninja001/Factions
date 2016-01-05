package candy.factions.faction;

import net.minecraft.nbt.NBTTagCompound;

public class Relation {
	/** The name of the faction */
	public String name;
	/** The state of the relationship */
	public EnumRelation relation;
	
	public Relation(String factionName, EnumRelation relation){
		this.name = factionName;
		this.relation = relation;
	}
	
	public void writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setString("name", name);
        tagCompound.setInteger("relation", relation.toInt());
    }
	
	public void readFromNBT(NBTTagCompound tagCompound)
    {
        name = tagCompound.getString("name");
        relation = EnumRelation.fromInt(tagCompound.getInteger("relation"));
    }
}
