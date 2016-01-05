package candy.factions.faction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import candy.factions.Factions;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class Faction {
	/** The save name of this faction */
	public String saveName;
	/** The user friendly name of this faction */
	public String textName;
	/** The type of factions this is */
	public int type;
	/** The level of order this faction is a part of */
	public int level;
	
	/** If true then users do not need invited to join the faction */
	public boolean isPublic;
	
	/** The relations of other factions */
	public List<Relation> relations = new ArrayList<Relation>();
	
	/** The ranks of all members of the faction */
	public List<Member> members = new ArrayList<Member>();
	
	/** A list of all players invited to the faction */
	public List<String> invites = new ArrayList<String>();
	
	/** A list of all players blocked from joining the faction */
	public List<String> blockedPlayers = new ArrayList<String>();
	
	/** The directory where the nbt data is stored for all faction */
	public static String saveDirectory;
	
	/**
	 * Creates a new faction.
	 */
	public Faction(String name, int type, int level){
		this.textName = name;
		this.type = type;
		this.level = level;
		this.isPublic = false;
		this.saveName = name.toLowerCase().replaceAll("\\P{L}+", "").replaceAll("[^A-Za-z0-9 ]", "");
	}
	
	/**
	 * Creates a faction for loading.
	 */
	private Faction(String name){
		this.saveName = name.toLowerCase().replaceAll("\\P{L}+", "").replaceAll("[^A-Za-z0-9 ]", "");
	}
	
	/**
	 *	Returns a Faction loaded from the saveName provided.
	 */
	public static Faction load(String name){
		Faction faction = new Faction(name);
		faction.readNBT();
		return faction;
	}
	
	/**
	 * Adds a user to the faction or update their rank.
	 */
	public void updateMember(Member member){
		for(Member memberEntry : this.members){
			if(memberEntry.name == member.name){
				this.members.remove(memberEntry);
				this.members.add(member);
				return;
			}
		}
		this.members.add(member);
	}
	
	/**
	 * Returns the Member for the specified username.
	 */
	public Member getMember(String username){
		for(Member memberEntry : this.members){
			if(memberEntry.name == username){
				return memberEntry;
			}
		}
		return null;
	}
	
	/**
	 * Removes a user from the faction.
	 */
	public void removeMember(Member member){
		for(Member memberEntry : this.members){
			if(memberEntry.name == member.name){
				this.members.remove(memberEntry);
				return;
			}
		}
	}
	
	/**
	 * Loads the data of this faction from the current saveName.
	 */
	public void readNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		try {
			File filenbt = new File(saveDirectory, this.saveName + ".dat");
			if (filenbt.exists() && filenbt.isFile()) {
				tag = CompressedStreamTools.readCompressed(new FileInputStream(filenbt));
			}else if (!filenbt.exists())
	        {
				System.out.println("Failed to load " + this.saveName + ".dat properly. Making new .dat file.");
				try
		        {
					tag = new NBTTagCompound();
					CompressedStreamTools.writeCompressed(tag, new FileOutputStream(filenbt));
		        }
				catch (Exception exception)
			    {
			        System.out.println("Failed to save " + this.saveName + ".dat properly.");
			    }
	        }
		} catch (Exception exception) { 
			System.out.println("Failed to load " + this.saveName + ".dat properly.");
		}
		
		this.textName = tag.getString("name");
		this.type = tag.getInteger("type");
		this.level = tag.getInteger("level");
		
		this.members.clear();
		NBTTagList memberTagList = tag.getTagList("members", 10);
		for (int i = 0; i < memberTagList.tagCount(); ++i)
        {
            NBTTagCompound memberTag = memberTagList.getCompoundTagAt(i);
            this.members.add(new Member(memberTag.getString("name"), EnumRank.fromInt(memberTag.getInteger("rank"))));
        }
		

		this.relations.clear();
		NBTTagList relationTagList = tag.getTagList("relations", 10);
		for (int i = 0; i < relationTagList.tagCount(); ++i)
        {
            NBTTagCompound relationTag = relationTagList.getCompoundTagAt(i);
            this.relations.add(new Relation(relationTag.getString("name"), EnumRelation.fromInt(relationTag.getInteger("relation"))));
        }
		
		
		this.blockedPlayers.clear();
		NBTTagList blockTagList = tag.getTagList("blocked", 10);
		for (int i = 0; i < blockTagList.tagCount(); ++i)
        {
            NBTTagCompound blockTag = blockTagList.getCompoundTagAt(i);
            this.blockedPlayers.add(blockTag.getString("name"));
        }
	}

	/**
	 * Saves the data of this faction.
	 */
	public void writeNBT()
    {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("name", textName);
		tag.setInteger("type", type);
		tag.setInteger("level", level);
		
		NBTTagCompound memberTag;
		NBTTagCompound relationsTag;
		NBTTagCompound blockTag;
		
		NBTTagList memberTagList = new NBTTagList();
		NBTTagList relationTagList = new NBTTagList();
		NBTTagList blockTagList = new NBTTagList();
		
		for (int i = 0; i < this.members.size(); ++i)
        {
            if (this.members.get(i) != null)
            {
            	memberTag = new NBTTagCompound();
            	memberTag.setString("name", this.members.get(i).name);
            	memberTag.setInteger("rank", this.members.get(i).rank.toInt());
                memberTagList.appendTag(memberTag);
            }
        }
		
		tag.setTag("members", memberTagList);
		
		for (int i = 0; i < this.relations.size(); ++i)
        {
            if (this.relations.get(i) != null)
            {
            	relationsTag = new NBTTagCompound();
            	relationsTag.setString("name", this.relations.get(i).name);
            	relationsTag.setInteger("relation", this.relations.get(i).relation.toInt());
            	relationTagList.appendTag(relationsTag);
            }
        }
		
		tag.setTag("relations", relationTagList);
		
		for (int i = 0; i < this.blockedPlayers.size(); ++i)
        {
            if (this.blockedPlayers.get(i) != null)
            {
            	blockTag = new NBTTagCompound();
            	blockTag.setString("name", this.blockedPlayers.get(i));
            	blockTagList.appendTag(blockTag);
            }
        }
		
		tag.setTag("blocked", blockTagList);
		
		try
        {
            File filenew = new File(this.saveDirectory, this.saveName + ".dat.tmp");
            File fileold = new File(this.saveDirectory, this.saveName + ".dat");
            CompressedStreamTools.writeCompressed(tag, new FileOutputStream(filenew));

            if (fileold.exists())
            {
                fileold.delete();
            }

            filenew.renameTo(fileold);
            }
        catch (Exception exception)
        {
        	System.out.println("Failed to save " + this.saveName + ".dat properly.");
        }
    }

	/**
	 * Called when deleting a faction.
	 */
	public void deleteNBT() {
		try
        {
            File filenbt = new File(this.saveDirectory, this.saveName + ".dat");
            if (filenbt.exists())
            {
                filenbt.delete();
            }
        }
        catch (Exception exception)
        {
        	System.out.println("Failed to delete " + this.saveName + ".dat properly.");
        }
	}

	/**
	 * Invites a player to the faction, returns false if they are blocked.
	 */
	public boolean addInvite(String username) {
		if(this.blockedPlayers.contains(username))
			return false;
		MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username).addChatComponentMessage(new ChatComponentText("You have been invited to " + this.textName + ". You can accept this invitation by using /f m " + this.saveName + " join."));
		if(this.isPublic)
			return true;
		if(this.invites.contains(username))
			return true;
		this.invites.add(username);
		return true;
	}
	
	/**
	 * Called when a user declines an invite or an admin revokes and invite.
	 */
	public void deleteInvite(String username) {
		this.invites.remove(username);
		MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username).addChatComponentMessage(new ChatComponentText("Your invitation to " + this.textName + " has been canceled"));
	}
	
	/**
	 * Called when a user accepts an invite.
	 */
	public void acceptInvite(String username) {
		if (this.blockedPlayers.contains(username)){
			MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username).addChatComponentMessage(new ChatComponentText("This faction has blocked you from joining the faction."));
			return;
		}
		if (this.isPublic || this.invites.contains(username)){
			this.members.add(new Member(username, EnumRank.CITIZEN));
			this.invites.remove(username);
			MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username).addChatComponentMessage(new ChatComponentText("You are now a part of " + this.textName + "!"));
			return;
		}
		MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(username).addChatComponentMessage(new ChatComponentText("You need to be invited to " + this.textName + " to join."));
	}
	
	/**
	 * Changes a users rank.
	 */
	public void changeRank(String username, EnumRank rank) {
		Member member = this.getMember(username);
		if(username==null)
			return;
		member.rank = rank;
		this.updateMember(member);
	}
}
