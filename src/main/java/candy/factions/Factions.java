package candy.factions;

import candy.factions.commands.CommandFaction;
import candy.factions.faction.Faction;
import candy.factions.management.FactionManager;
import candy.factions.management.UserManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;

//import net.minecraftforge.fml.SidedProxy;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import scala.reflect.io.Directory;

@Mod(modid = Factions.MODID, name = Factions.MODNAME, version = Factions.MODVER, acceptableRemoteVersions = "*")
public class Factions {
	public static final String MODID = "candyfactions";
	public static final String MODNAME = "CandyNinja's Factions";
	public static final String MODVER = "0.0.0";
	
	public static String directory;
	public static FactionManager factionManager;
	public static UserManager userManager;
	
	@Instance(value = Factions.MODID)
	public static Factions instance;
	
	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		directory = DimensionManager.getCurrentSaveRootDirectory() + "/factions";
		UserManager.saveDirectory = directory + "/players";
		Faction.saveDirectory = directory + "/factions";
		event.registerServerCommand(new CommandFaction());
		factionManager = new FactionManager();
		factionManager.loadAllFactionData();
		userManager = new UserManager();
		userManager.loadAllUserData();
		userManager.saveDirectory = directory + "/players";
		//FactionNBT.readData();
		//System.out.println("start");
	}

	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {
		//System.out.println("About to start");
	}

	@EventHandler
	public void serverStopping(final FMLServerStoppingEvent event) {
		//FactionNBT.writeData();
		factionManager.writeAllFactionData();
		userManager.writeAllUserData();
		//System.out.println("Stopping");
	}

	@EventHandler
	public void serverStopped(final FMLServerStoppedEvent event) {
		//System.out.println("About to stop");
	}
}