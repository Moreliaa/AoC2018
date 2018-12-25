package days;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Day24 {
    private static ArrayList<Group> system = new ArrayList<>();
    private static ArrayList<Group> infection = new ArrayList<>();
    private static ArrayList<Group> groups;
    private static int boost = 42;    

	public static void main(String[] args) {
        makeGroups();
        while(system.size() > 0 && infection.size() > 0) {
            selectTargets();
            fight();
        }
        int remaining = 0;
        for (Group g: groups) {
            remaining+=g.units;
        }
        System.out.println(remaining);
    }

    private static void selectTargets() {
        Collections.sort(groups, new GroupComparator());
        ArrayList<Group> selected = new ArrayList<Group>();
        for (Group g: groups) {
            int mostDamage = 0;
            Group target = null;
            for (Group t: groups) {
                if (selected.contains(t))
                    continue;
                if (t.isEnemyTo(g)) {
                    int dam = t.damageDealtBy(g.effPower(), g.damageType);
                    if (dam > mostDamage) {
                        mostDamage = dam;
                        target = t;
                    } else if (target != null)
                        if(dam == mostDamage && t.effPower() > target.effPower()) {
                            target = t;
                        } else if (dam == mostDamage && t.effPower() == target.effPower() && t.init > target.init)
                            target = t;
                }
            }
            g.target = target;
            selected.add(target);
        }
    }

	private static class GroupComparator implements Comparator<Group> {
		public int compare(Group c1, Group c2) {
            if(c1.effPower() > c2.effPower())
                return -1;
            else if(c1.effPower() == c2.effPower() && c1.init > c2.init)
                return -1;
            else
                return 1;
		}
    }

    private static void fight() {
        Collections.sort(groups, new InitComparator());
        ArrayList<Group> dead = new ArrayList<Group>();
        for (Group attacker:groups){
            if (attacker.target != null && attacker.units > 0){
                int dam = attacker.target.damageDealtBy(attacker.effPower(), attacker.damageType);
                attacker.target.units -= Math.floor(dam / attacker.target.hp);
                if (attacker.target.units <= 0)
                    dead.add(attacker.target);
                attacker.target = null;
            }
        }
        for (Group d: dead) {
            groups.remove(d);
            if (system.contains(d))
            {
                system.remove(d);
                System.out.println("slain sys");
            }
            else {
                infection.remove(d);
                System.out.println("slain inf");
            }
        }
        int totalU = 0;
        for (Group g: groups) {
            totalU += g.units;
        }
    }

    private static class InitComparator implements Comparator<Group> {
		public int compare(Group c1, Group c2) {
            if(c1.init > c2.init)
                return -1;
            else
                return 1;
		}
	}

    private static class Group {
        int units, hp, atk, init;
        String damageType;
        String immunities;
        String weaknesses;
        Group target = null;
        public Group(int u, int h, String immunities, String weaknesses, int a, String damType, int i) {
            units = u;
            hp = h;
            atk = a;
            init = i;
            damageType = damType;
            this.immunities = immunities;
            this.weaknesses = weaknesses;
        }

        public int effPower() {
            if(system.contains(this))
                return units * (atk + boost);
            return units * atk;
        }

        public boolean isEnemyTo(Group g) {
            return system.contains(this) == infection.contains(g);
        }

        public int damageDealtBy(int atk, String type) {
            boolean weak = weaknesses.contains(type);
            if (weak)
                return atk * 2;
            boolean immune = immunities.contains(type);
            if (immune)
                return 0;
            return atk;
        }
    }

    private static void makeGroups() {
        //public Group(int u, int h, int a, int i, String damType, String props) {
        system.add(new Group(2749,8712,"radiation, cold","fire",30,"radiation",18));
        system.add(new Group(704, 1890, "","",26,"fire",17));
        system.add(new Group(1466,7198,"bludgeoning","slashing, cold",44,"bludgeoning",6));
        system.add(new Group(6779,11207,"","",13,"cold",4));
        system.add(new Group(1275,11747,"","",66,"cold",2));
        system.add(new Group(947,5442,"","",49,"radiation",3));
        system.add(new Group(4319,2144,"","bludgeoning, fire",4,"fire",9));
        system.add(new Group(6315,5705,"","",7,"cold",16));
        system.add(new Group(8790,10312,"","",10,"fire",5));
        system.add(new Group(3242,4188,"radiation", "cold", 11,"bludgeoning",14));

        infection.add(new Group(1230,11944,"","cold",17,"bludgeoning",1));
        infection.add(new Group(7588,53223,"bludgeoning","",13,"cold",12));
        infection.add(new Group(1887,40790,"radiation, slashing, cold","",43,"fire",15));
        infection.add(new Group(285,8703,"slashing","",60,"slashing",7));
        infection.add(new Group(1505,29297,"","",38,"fire",8));
        infection.add(new Group(191,24260,"bludgeoning","slashing",173,"cold",20));
        infection.add(new Group(1854,12648,"","fire, cold",13,"bludgeoning",13));
        infection.add(new Group(1541,49751,"","cold, bludgeoning", 62,"slashing",19));
        infection.add(new Group(3270,22736,"","",13,"slashing",10));
        infection.add(new Group(1211,56258,"slashing, cold","",73,"bludgeoning",11));

        groups = new ArrayList<Group>(system);
        groups.addAll(infection);
    }

    private static void readInput() {
		BufferedReader br;
        //2749 units each with 8712 hit points (immune to radiation, cold; 
        //weak to fire) with an attack that does 30 radiation damage at initiative 18
		Pattern p = Pattern.compile("(?<units>^\\d+).+(?<hp>\\d+).+\\((?<properties>.+)\\).+(?<atk>\\d+)\\s(?<damType>.+) damage at initiative (?<init>\\d+)$");

		try {
            br = new BufferedReader(new FileReader("src/input/inputday24"));
            String line = br.readLine();
            boolean next = false;

			while (line != null) {
                if(line == "Infection:"){
                    next = true;
                }
                Matcher m = p.matcher(line);
                if(m.find()){
                    int units = Integer.valueOf(m.group("units"));
                    int hp = Integer.valueOf(m.group("hp"));
                    String props = m.group("properties");
                    int atk = Integer.valueOf(m.group("atk"));
                    String damType = m.group("damType");
                    int init = Integer.valueOf(m.group("init"));
                    /*Group g = new Group(units,hp,atk,init,damType,props);
                    if (!next)
                        system.add(g);
                    else
                        infection.add(g);*/
                }
                line = br.readLine();
                }
		} catch (Exception e) {
			e.printStackTrace();
        }
	}
}