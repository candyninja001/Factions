package candy.factions.management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import candy.factions.Factions;
import candy.factions.faction.Faction;
import candy.factions.faction.User;
import candy.factions.utility.Utility;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class UserManager {
	/** A list containing all loaded factions */
	public List<User> users = new ArrayList<User>();
	
	public static String saveDirectory;
	
	public UserManager(){
		
	}
	
	public User getUserFromName(String name){
		for (User user : users) {
			if(user.username.equals(name)){
				return user;
			}
		}
		return null;
	}
	
	public void updateUser(User user){
		for (User userEntry : users) {
			if(userEntry.username.equals(user.username)){
				users.remove(userEntry);
				users.add(user);
				return;
			}
		}
		users.add(user);
	}
	
	public void loadAllUserData(){
		File fileUsers = new File(saveDirectory+"players.dat");
		NBTTagCompound tag = new NBTTagCompound();
		try {
			tag = CompressedStreamTools.readCompressed(new FileInputStream(fileUsers));
		} catch (Exception exception)
	    {
	        System.out.println("Failed to read players.dat properly.");
	    }
		NBTTagList list = tag.getTagList("players", 10);
		if(!list.hasNoTags()){
			for (int i = 0; i < list.tagCount(); ++i)
			{
				users.add(User.loadFromNBT((NBTTagCompound) list.get(i)));
			}
		}
	}
	
	public void writeAllUserData(){
		File fileUsers = new File(saveDirectory+"players.dat");
		NBTTagCompound tag = new NBTTagCompound();
		
		NBTTagList playerTagList = new NBTTagList();
		
		int i = 0;
		for (User user : users) {
			playerTagList.appendTag(user.writeToNBT());
		}
		
		try
        {
            File filenew = new File(saveDirectory, "players.dat.tmp");
            File fileold = new File(saveDirectory, "players.dat");
            CompressedStreamTools.writeCompressed(tag, new FileOutputStream(filenew));

            if (fileold.exists())
            {
                fileold.delete();
            }

            filenew.renameTo(fileold);
            }
        catch (Exception exception)
        {
        	System.out.println("Failed to save players.dat properly.");
        }
	}
}
