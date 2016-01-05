package candy.factions.faction;

public enum EnumRank {
	FOREIGNER, CITIZEN, GUARD, ADVISER, LEADER;

	public int toInt() {
		switch (this) {
		case FOREIGNER:
			return 0;
		case CITIZEN:
			return 1;
		case GUARD:
			return 2;
		case ADVISER:
			return 3;
		case LEADER:
			return 4;
		default:
			System.out.println("Could not convert EnumRank to int. Assigning FOREIGNER.");
			return 0;
		}
	}

	public static EnumRank fromInt(int relation) {
		switch (relation) {
		case 0:
			return FOREIGNER;
		case 1:
			return CITIZEN;
		case 2:
			return GUARD;
		case 3:
			return ADVISER;
		case 4:
			return LEADER;
		default:
			System.out.println("Could not convert int to EnumRank. Assigning FOREIGNER.");
			return FOREIGNER;
		}
	}

}
