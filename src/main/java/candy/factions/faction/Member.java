package candy.factions.faction;

import net.minecraft.nbt.NBTTagCompound;

public class Member {
	public String name;
	public EnumRank rank;
	
	public Member(String playerName, EnumRank rank){
		this.name = playerName;
		this.rank = rank;
	}
	
	public void writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setString("name", name);
        tagCompound.setInteger("rank", rank.toInt());
    }
	
	public void readFromNBT(NBTTagCompound tagCompound)
    {
        name = tagCompound.getString("name");
        rank = EnumRank.fromInt(tagCompound.getInteger("rank"));
    }
}
