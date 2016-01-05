package candy.factions.utility;

import candy.factions.faction.Faction;

public class Utility {
	public static boolean doesNameMatchFaction(Faction faction, String name){
		return faction.saveName.toLowerCase().replaceAll("\\P{L}+", "").replaceAll("[^A-Za-z0-9 ]", "").equals(name.toLowerCase().replaceAll("\\P{L}+", "").replaceAll("[^A-Za-z0-9 ]", ""));
	}
}
