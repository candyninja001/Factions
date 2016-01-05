package candy.factions.faction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class User {
	
	public String username;
	public List<String> memberships = new ArrayList<String>();
	
	public User(String username){
		this.username = username;
	}
	
	public void joinFaction(Faction faction){
		if (memberships.contains(faction.saveName))
		memberships.add(faction.saveName);
	}
	
	public void leaveFaction(Faction faction){
		memberships.add(faction.saveName);
	}
	
	public static User loadFromNBT(NBTTagCompound tag) {
		User user = new User(tag.getString("player"));
		NBTTagList listMemberships = tag.getTagList("memberships", 8);
		if (!listMemberships.hasNoTags()){
			for (int i = 0; i < listMemberships.tagCount(); ++i)
			{
				user.memberships.add(listMemberships.getStringTagAt(i));
			}
		}
		return user;
	}

	public NBTTagCompound writeToNBT()
    {
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setString("player", username);
		
		NBTTagList listMemberships = new NBTTagList();
		for (int i = 0; i < memberships.size(); ++i)
        {
            if (memberships.get(i) != null)
            {
            	listMemberships.appendTag(new NBTTagString(memberships.get(i)));
            }
        }
		tag.setTag("memberships", listMemberships);
		return tag;
    }
}
