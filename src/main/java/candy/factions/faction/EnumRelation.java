package candy.factions.faction;

public enum EnumRelation {
	ALLIED, NEUTRAL, HOSTILE, PARENT, CHILD, UNMET;
	
	public int toInt() {
		switch (this) {
		case UNMET:
			return 0;
		case NEUTRAL:
			return 1;
		case ALLIED:
			return 2;
		case HOSTILE:
			return 3;
		case PARENT:
			return 4;
		case CHILD:
			return 5;
		default:
			System.out.println("Could not convert EnumRelation to int. Assigning UNMET.");
			return 0;
		}
	}

	public static EnumRelation fromInt(int relation) {
		switch (relation) {
		case 0:
			return UNMET;
		case 1:
			return NEUTRAL;
		case 2:
			return ALLIED;
		case 3:
			return HOSTILE;
		case 4:
			return PARENT;
		case 5:
			return CHILD;
		default:
			System.out.println("Could not convert int to EnumRelation. Assigning UNMET.");
			return UNMET;
		}
	}
}
