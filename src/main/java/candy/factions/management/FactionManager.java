package candy.factions.management;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import candy.factions.faction.Faction;
import candy.factions.utility.Utility;

public class FactionManager {
	/** A list containing all loaded factions */
	public List<Faction> factions = new ArrayList<Faction>();
	
	public FactionManager(){
		
	}
	
	public Faction getFactionFromName(String name){
		for (Faction faction : factions) {
			if(Utility.doesNameMatchFaction(faction, name)){
				return faction;
			}
		}
		return null;
	}
	
	public void updateFaction(Faction faction){
		for (Faction factionEntry : factions) {
			if(Utility.doesNameMatchFaction(factionEntry, faction.saveName)){
				factions.remove(factionEntry);
				factions.add(faction);
				return;
			}
		}
		factions.add(faction);
	}
	
	public void loadAllFactionData(){
		File[] fileFactions = new File(Faction.saveDirectory).listFiles();
		for (File fileFaction : fileFactions) {
			factions.add(Faction.load(fileFaction.getName().replaceAll("\\b.dat\\b", "")));
		}
	}
	
	public void writeAllFactionData(){
		for (Faction faction : factions) {
			faction.writeNBT();
		}
	}
	
	public boolean deleteFaction(String name){
		Faction faction = getFactionFromName(name);
		if (faction==null)
			return false;
		faction.deleteNBT();
		factions.remove(getFactionFromName(name));
		return true;
	}
}
