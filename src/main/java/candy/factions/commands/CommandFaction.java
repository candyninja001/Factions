package candy.factions.commands;

import java.util.ArrayList;
import java.util.List;

import candy.factions.Factions;
import candy.factions.faction.EnumRank;
import candy.factions.faction.Faction;
import candy.factions.faction.Member;
import candy.factions.faction.User;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

public class CommandFaction implements ICommand {
	public CommandFaction() {

	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "Use /f help.";
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i) {
		return false;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String getName() {
		return "factions";
	}

	@Override
	public List getAliases() {
		List<String> aliases = new ArrayList<String>();
		aliases.add("factions");
		aliases.add("faction");
		aliases.add("f");
		return aliases;
	}

	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		if (sender.getCommandSenderEntity() != null) {
			if (args == null || args.length == 0) {
				sender.addChatMessage(new ChatComponentText("Use /f help."));
			} else {
				switch (args[0]) {
				case "help":
					this.help(sender, args);
					break;
				case "create":
					if (args.length < 2) {
						sender.addChatMessage(new ChatComponentText("Format: /f create <name>"));
						break;
					}
//					if (0==1){
//						sender.addChatMessage(new ChatComponentText("Leave your current faction (" + "" + ") before creating this faction"));
//						break;
//					}
					Faction faction = new Faction(args[1], 1, 2);
					faction.updateMember(new Member(sender.getName(), EnumRank.LEADER));
					Factions.factionManager.updateFaction(faction);
					break;
				case "manage":
				case "m":
					this.manage(sender, args, 1, 1);
					break;
					
				case "admin":
				case "a":
					this.admin(sender, args, 1, 1);
					break;
					
				case "list":
					for(Faction factionListEntry : Factions.factionManager.factions )
						sender.addChatMessage(new ChatComponentText(factionListEntry.saveName));
					
					//sender.addChatMessage(new ChatComponentText("The Faction is called " + factionEntry.textName + " with the save name " + factionEntry.saveName + "The Faction's type is " + factionEntry.type + " and is level " + factionEntry.level));
					break;
//				case "family":
//				case "f":
//					this.manage(sender, args, 1, 2);
//					break;
//				case "village":
//				case "v":
//					this.manage(sender, args, 2, 2);
//					break;
//				case "guild":
//				case "g":
//					this.manage(sender, args, 1, 3);
//					break;
//				case "town":
//				case "t":
//					this.manage(sender, args, 2, 3);
//					break;
//				case "province":
//				case "p":
//					this.manage(sender, args, 3, 3);
//					break;
				default:
					sender.addChatMessage(new ChatComponentText("Use /f help."));
					break;
				}
			}
			return;
		} else {
			sender.addChatMessage(new ChatComponentText("Silly admin, don't use the console!"));
		}
	}

	private void admin(ICommandSender sender, String[] args, int i, int j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender sender) {
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		List<String> options = new ArrayList<String>();
		if (args.length>0){
			switch (args[0]) {
			case "help":
				options.add("");
				break;
			}
		}
		return options;
	}

	// ---------------------------------------------------------------

	public void help(ICommandSender sender, String[] args) {
		sender.addChatMessage(new ChatComponentText("There is no help."));
	}

	public void manage(ICommandSender sender, String[] args, int type, int level) {
		if (args.length < 3) {
			sender.addChatMessage(new ChatComponentText("Use /f m <faction name> <command>"));
		} else if(Factions.factionManager.getFactionFromName(args[1])==null){
			sender.addChatMessage(new ChatComponentText("This is not a faction"));
		}else {
			switch (args[2]) {
//			case "create":
//				if (args.length < 3) {
//					sender.addChatMessage(new ChatComponentText("Use /f help."));
//					break;
//				}
//				Faction faction = new Faction(args[2], type, level);
//				faction.updateMember(new Member(sender.getName(), EnumRank.LEADER));
//				Factions.factionManager.updateFaction(faction);
//				break;

			case "delete":
				if (args.length < 3) {
					sender.addChatMessage(new ChatComponentText("Use /f help."));
					break;
				}
				Factions.factionManager.deleteFaction(args[2]);
				break;

			case "lookup":
				if (args.length < 3) {
					sender.addChatMessage(new ChatComponentText("Use /f help."));
					break;
				}
				Faction factionEntry = Factions.factionManager.getFactionFromName(args[2]);
				sender.addChatMessage(new ChatComponentText("The Faction is called " + factionEntry.textName + " with the save name " + factionEntry.saveName + "The Faction's type is " + factionEntry.type + " and is level " + factionEntry.level));
				break;
				
			case "invite":
				if (args.length < 4) {
					sender.addChatMessage(new ChatComponentText("Use /f m <faction name> add <player name>"));
					return;
				}
				Factions.factionManager.getFactionFromName(args[1]).addInvite(args[3]);
				break;
				
			case "join":
				Factions.factionManager.getFactionFromName(args[1]).acceptInvite(sender.getName());
				break;
				
			case "decline":
				Factions.factionManager.getFactionFromName(args[1]).deleteInvite(sender.getName());
				break;
				
			default:
				sender.addChatMessage(new ChatComponentText("Use /f help."));
				break;
			}
		}
	}
}