package days;

import java.util.*;
import java.util.Map.*;

public class Day2 {

	public static void main(String[] args) {
		String[] input = { "cvfueihajytpmrdkgsxfqplbxn", "cbzueihajytnmrdkgtxfqplbwn", "cvzucihajytomrdkgstfqplqwn",
				"cvzueilajytomrdkgsxfqwnbwn", "cvzueihajytomrdkgsgwqphbwn", "wuzuerhajytomrdkgsxfqplbwn",
				"cyzueifajybomrdkgsxfqplbwn", "cvzueihajxtomrdkgpxfqplmwn", "ivzfevhajytomrdkgsxfqplbwn",
				"cvzueihajytomrdlgsxfqphbbn", "uvzueihajjtomrdkgsxfqpobwn", "cvzupihajytomrdkgsxfqplpwe",
				"cvzueihajyvomrdkgsxfqplbrl", "cczueihajytomrdkgsnfqpxbwn", "cvzueigajytdmrdkgsxyqplbwn",
				"cvzujihljytomrdkgsxuqplbwn", "cvzueisajytomrddgsxkqplbwn", "cvzneihajytomrdkgsgaqplbwn",
				"cvzueihajytomrdkgsinmplbwn", "cveueihajyromrdkgsxfqplown", "cypueihajytotrdkgzxfqplbwn",
				"cvzuoihajytomvdqgsxfqplbwn", "cvzuekhejytwmrdkgsxfqplbwn", "cvzseihajytomrdkgsxfqgmbwn",
				"cvfuhihajytomrdkgsxfqplbwi", "cvzueihujxtomrdkgsufqplbwn", "cvzueihdjytomrdogsxfqplbwh",
				"cvzueihdjyfohrdkgsxfqplbwn", "cvtudihajytolrdkgsxfqplbwn", "cvzueihajytymrdkgshzqplbwn",
				"cvzuebhajytomxdkgsxfqplbwt", "cvzulihajyxomrdkgsbfqplbwn", "cvzueihajywomrdkgsxfqplbts",
				"cvzueihajytouodkdsxfqplbwn", "cvzueihajytomgdkgqxfqklbwn", "cvzubihajytomvdkgsxfqplmwn",
				"cvhueihajyyocrdkgsxfqplbwn", "zvzueihajytourdkgsxflplbwn", "cvzbeihajytomadkgsxfoplbwn",
				"cvzueihajytomrdkgnxfqplbsl", "cvfueihajftkmrdkgsxfqplbwn", "cvzuexhajytomryugsxfqplbwn",
				"cvzueihajytomsckgsxfqalbwn", "cvzuexhajytomrdkbsxfqpluwn", "cvzueihajytbmrtkgsxwqplbwn",
				"cvzueihajytomrdigsxfqqlbsn", "cvzweihajytomydkgsxfmplbwn", "bvzteihajytimrdkgsxfqplbwn",
				"cvzueihajytpmrdkgsxfcpbbwn", "cvzueigsjltomrdkgsxfqplbwn", "cvzueihajytomrikgsxfopldwn",
				"cvzueihajstomrdkgsxfqplgon", "cvzueimajytomrnkxsxfqplbwn", "cvzleihagatomrdkgsxfqplbwn",
				"cvbueihajotomrdkgsxfqjlbwn", "cvzueihajytomrdkgsxfqppnvn", "hvzueihajytomrdkghxfkplbwn",
				"cvzueigajytxmrdkgsxfqplbjn", "cvzueihaaxtokrdkgsxfqplbwn", "cvzueihajyeomrdkgujfqplbwn",
				"cvzueiwajpoomrdkgsxfqplbwn", "cvzieidtjytomrdkgsxfqplbwn", "cvzueihalytomrakbsxfqplbwn",
				"wtzueihajytomrdkgsxfqplbwq", "cvzuelhaiytomrdkgsxfqplcwn", "cvzueihajytomrdkgsxfqslswd",
				"cvzueihajytomrykgssfqplbon", "cvzueihfjytovrdegsxfqplbwn", "cvzueihajytomldqgsxfqplbwy",
				"cvzleihjjytomrtkgsxfqplbwn", "cvzueihaldtomrdtgsxfqplbwn", "cvzueihajytzmrdkgsxfeplqwn",
				"cvzueihrjytomddkgsxfqpgbwn", "cyzulihajytokrdkgsxfqplbwn", "cvsueihajytoordfgsxfqplbwn",
				"fvzueyhajytomrdkgaxfqplbwn", "cczueihajytobrdkgsefqplbwn", "cvzueihajytomcdrgscfqplbwn",
				"cvzuexhajyvomrdkgssfqplbwn", "cvzsmihajyiomrdkgsxfqplbwn", "cvzzeihajttomrdkgsxzqplbwn",
				"cvzseihajytomrdkgsxfqpebvn", "cvzueihajgthmrdkgsbfqplbwn", "ruzueihajytomrdkgsxfqphbwn",
				"cvzueihajytofrdkgsnfrplbwn", "cvzuetdajytojrdkgsxfqplbwn", "fvzueihajytomrdkghxfqpobwn",
				"cvzueihsjytomrdkgsxfqglbxn", "cvzueihajytowrdkgsxfqpsbun", "cvzteihaiytomrdkfsxfqplbwn",
				"cvzueihajytkmrdkrsxfqplvwn", "cvzueihajyoomrdkasxfqjlbwn", "lvzurihajytkmrdkgsxfqplbwn",
				"cvzueihajyyomrdagsxfqelbwn", "cvfueihajytomrdkgsxfqplbbx", "cvwueihajytommdkgkxfqplbwn",
				"cvzucicajytomrdkgsxcqplbwn", "dvzueihahytgmrdkgsxfqplbwn", "cvzuechajytomrdkgsxfqelwwn",
				"cvzuekhajytomrdkgsxknplbwn", "cvtueihajytomphkgsxfqplbwn", "cvzueihabytnzrdkgsxfqplbwn",
				"cvzusihajytomrdkgfxfqplban", "cvfueihajytomcdfgsxfqplbwn", "mvzueihapytomrdkgsxfdplbwn",
				"cvzueihajytomhdkgsxmqppbwn", "jvsueihajytomrdkgsxfqplbln", "cvzujihajybomrdkgsxtqplbwn",
				"cvzuekhawytomrdkgsxfqplbwc", "svzueihanytomrdogsxfqplbwn", "cvzujihajytodrdkgslfqplbwn",
				"cvgdeihajytorrdkgsxfqplbwn", "cvzbeihajytoprdkgsxfqplbyn", "cvzueihkyytomjdkgsxfqplbwn",
				"cvzuelhojytomrdkgsxfqjlbwn", "evzueihajytimrdkgsxfqpsbwn", "cvzueihajydomrdkjsxfqplbjn",
				"ovzteihajytosrdkgsxfqplbwn", "cvzueihajyaomrdzgsxfqplbgn", "cvzuewhajmtomrdkgsufqplbwn",
				"cvzueihajqtomhukgsxfqplbwn", "cvzueihajytomzqkgsxfqplbwk", "cazuewhakytomrdkgsxfqplbwn",
				"clzueihatytomrdkgzxfqplbwn", "dvzueihajytomqdkgsxfqpnbwn", "cvzueidajdtomrdkgsxtqplbwn",
				"cvzueihabytowrdkgsxoqplbwn", "cvzujihwjytomrdkgsxeqplbwn", "cvtuedhajytomrdkgsxfqplbbn",
				"cvzueihajcgomrdkgsxfqplswn", "cvzuephajyiomrdngsxfqplbwn", "cvzueihajythmqdkgsxfqplbwf",
				"cvzueitajytomrdkgsxfepvbwn", "cvzueihajytomydkgsxfqplvwb", "dvzueshajytomrddgsxfqplbwn",
				"cvzueihajytomrdkgvxfqpwben", "cvzueihajytomrdkgvxfpplwwn", "cvzuefhajftomrdkgsxfqrlbwn",
				"cvzueihajytpmrvkgsxfqplbcn", "cvzueihajytohrdkgsxfqxnbwn", "cvzueihajytomrdposxfqulbwn",
				"cozueihajytomrpkgsxfqrlbwn", "cvzuuihaxytomrdkgsxfqplbtn", "cvzueihajytomrbzgsxyqplbwn",
				"cveueihajyxoqrdkgsxfqplbwn", "cvzueihajytomrkkgsxfqptbrn", "cvzuezhajatomrdkssxfqplbwn",
				"cpzueihajytomrdkgsxfhplbwo", "lviueihajytomrekgsxfqplbwn", "cvzueihwjytomrdkusxfyplbwn",
				"cvzgeihajytomwdkgsxfrplbwn", "cvzsejhzjytomrdkgsxfqplbwn", "cvzuuihajytomrdkgsxfqdlbwz",
				"cvzjeihajytomrdugsxftplbwn", "cvzueihaxytomrrkgsxfmplbwn", "cvzueihajgtomrdhgsxfqplwwn",
				"cvzulihajytomedkgsxfqplewn", "cvzueivajytomrdkmsxfqplbwc", "cvzuervajytomrdkgsxfwplbwn",
				"cvzuemhcjytomrdkgslfqplbwn", "cvzyerhauytomrdkgsxfqplbwn", "cvzueihaoytomrdkgsyfqplewn",
				"cvzueihanytomrdkgsafkplbwn", "cvzueihajvtomrdugsxfqpcbwn", "chzueihajytamrdxgsxfqplbwn",
				"cvzueihalytomrdsgsxfqplbln", "cvzueihajytoyaykgsxfqplbwn", "tlzueihajyeomrdkgsxfqplbwn",
				"cvpueihajytbmrdkgsxfxplbwn", "cvzueihajytomjdkgsxuqplkwn", "cvzueihajygomrdkgkxfqplbwg",
				"cvzueihajhtomrdkgbxsqplbwn", "cvzurihajytomrdkgsafqplbwx", "cdzuezhajytomrdkgsxrqplbwn",
				"cvbueihajytotrwkgsxfqplbwn", "cwzkeihajytomrdkgsxfqplbwh", "cvzheihajytolrikgsxfqplbwn",
				"cozuevhajytomrdkgkxfqplbwn", "chzueihajytomrjkgsxfqulbwn", "cvzueihkjyromrdkgsxvqplbwn",
				"cvzveihajytomrdkgsxpqplnwn", "cvzueihajytoirdkgsxfqihbwn", "cvoueihajytomrdkgsxfqpdawn",
				"pvzueihajytomrdkgnxfqplbfn", "cvzueihakytomxdkgssfqplbwn", "cvzueivajytomrdbgsxaqplbwn",
				"cvzueihajytokrdkgszrqplbwn", "cvzuevhajytomrdkgsxgqplbwi", "cvzueihajylomrdkgsxflplbpn",
				"hvzueihajytomvdkgsxfqplgwn", "cvzleihajytymrrkgsxfqplbwn", "crzueieajytomrdkgsxfqplbon",
				"cszueihajytomrdlgqxfqplbwn", "cvzueihacytomrdkgsxfjblbwn", "cvzreihajytomrdkgsxfqplzun",
				"cvzurihajytomrdkgsxiqplawn", "uvzueihajyhovrdkgsxfqplbwn", "cvzueihajyqodrdkgssfqplbwn",
				"cvzwiihrjytomrdkgsxfqplbwn", "cqzueihajytomrdkgjxfqplban", "cvmueihajytoordkgsxfqplbyn",
				"cypueihajytomrdkgzxfqplbwn", "cvzueihajykomrdkgsmfqplbtn", "cvzueidajytimrdkgsxfqpdbwn",
				"cvzheihajytomrdkgsxfqpfewn", "dvzueihajytumrdzgsxfqplbwn", "cvzueixajytomrdkgsvfqplgwn",
				"cvzuevhzjyzomrdkgsxfqplbwn", "cvyeeihajytomrdkgsxnqplbwn", "cvzueihajytomrdkggtpqplbwn",
				"cvzceiyajytomrdkgexfqplbwn", "cvzuelhajyyomrdkzsxfqplbwn", "cvzhzihajygomrdkgsxfqplbwn",
				"cvzueihwjytomrdkgsgfqplbrn", "cvzsevhajytomrdkgqxfqplbwn", "cvzueiuajytomrdkgsxfppebwn",
				"nvzueihajytemrdkgsxwqplbwn", "cvzueihajytocgdkgsxfqvlbwn", "cczusihajytomrdkgsxfqplbpn",
				"cmzueihajytomrdkbsxwqplbwn", "cvzumfdajytomrdkgsxfqplbwn", "cvzueihcjytomrdkgsxfqplbkl",
				"cvzueihajytomawknsxfqplbwn", "kvzueihijytomrdkgsxdqplbwn", "cdzutihajytomrdkgsxfkplbwn",
				"cvzufihadylomrdkgsxfqplbwn", "cvzueihajytomrgkxsxfqphbwn", "cvzuewhajyzomrdkgsxfqelbwn",
				"cvzueihajytomrdkgqxfqelbwc", "cvzueshajyoomrdkgsxfqflbwn", "cvzueihajyromrekgixfqplbwn",
				"chzugihajytomrdkgsxfqplawn", "cvzueihajytomrdkgsxfhpmbwy", "cvzueihacytodxdkgsxfqplbwn",
				"cvzurihajytourdkgsdfqplbwn", "cvzzeihmjytomrddgsxfqplbwn", "cvzucyhajygomrdkgsxfqplbwn",
				"ckzueihzjytomrdkgsxwqplbwn", "cvlueihajmtozrdkgsxfqplbwn", "cvzkeihajytomrdkgsxfqclbwc",
				"cvzueihajytomrdkgsxgdplbwa", "cvzueihyjytoxrdkgcxfqplbwn", "cvzueizavytomfdkgsxfqplbwn",
				"cvzueihajwtosrdkgsxfqllbwn", "cvzueihajytomrdaksxfqpllwn", "cvzuuihojytombdkgsxfqplbwn",
				"cvzuiibajytpmrdkgsxfqplbwn", "cvzueihajyuomydkgsxfqplzwn", "cvzueihajytimrmkgsxfqplfwn",
				"cvzueihajytomrdkgzxfqpljwo" };
		partOne(input);
		partTwo(input);
	}

	private static void partOne(String[] input) {
		int twice = 0;
		int thrice = 0;
		for (int i = 0; i < input.length; i++) {
			HashMap<Character, Integer> map = new HashMap<Character, Integer>(30);
			for (int j = 0; j < input[i].length(); j++) {
				Character c = input[i].charAt(j);
				if (!map.containsKey(c)) {
					map.put(c, 1);
				} else {
					int value = map.get(c);
					map.put(c, value + 1);
				}
			}
			boolean seenTwice = false;
			boolean seenThrice = false;
			Iterator<Entry<Character, Integer>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Character, Integer> entry = it.next();
				if (!seenTwice && entry.getValue() == 2) {
					twice++;
					seenTwice = true;
				} else if (!seenThrice && entry.getValue() == 3) {
					thrice++;
					seenThrice = true;
				}
			}
		}
		int checksum = twice * thrice;
		System.out.println(checksum);
	}

	private static void partTwo(String[] input) {
		for (int i = 0; i < input.length; i++) {
			String s = input[i];
			for (int j = i + 1; j < input.length; j++) {
				String same = "";
				int diffCount = 0;
				for (int k = 0; k < s.length(); k++) {
					Character c = s.charAt(k);
					if (s.charAt(k) == input[j].charAt(k)) {
						same = same.concat(c.toString());
					} else {
						diffCount++;
					}
					if (diffCount > 1) {
						continue;
					}
				}
				if (diffCount <= 1) {
					System.out.println(same);
				}
			}
		}
	}

}
