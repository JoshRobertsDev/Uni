import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class WordIndexer_v1 {

	public static void main(String[] args) {
		String text =	"The cat sat ';,./   on the mat\n" +
						"The dog chas/.,?ed the cat\n" +
						"The cat ran from!@ the dog";
		
		String omittedWords = "the,on,from"; 
		String[] commonWordArray = omittedWords.toLowerCase().split(",");
		String[] textArray= text.toLowerCase().replaceAll("[^A-Za-z0-9\n ]+", "").replaceAll(" +", " ").split("\n");
		
		//System.out.println(generateIndex(mapWords(text), null));
		System.out.println(generateIndex(mapWords(textArray), commonWordArray));
	}
	
	public static SortedMap<String, List<Integer>> mapWords(String[] text) {
		HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		
		for(int i = 0; i < text.length; i++) {
			String[] sentance = text[i].split(" ");
			
			for(int j = 0; j < sentance.length; j++) {
				List<Integer> tempList = new ArrayList<Integer>();
				
				if(!map.containsKey(sentance[j])){		
					tempList.add(i+1);
				} else {
					tempList = map.get(sentance[j]);
					if(!tempList.contains(i+1)) {
						tempList.add(i+1);
					}
				}
				
				map.put(sentance[j], tempList);					
			}
		}
		return new TreeMap<String, List<Integer>>(map);
	}
	
	public static String generateIndex(Map<String, List<Integer>> map, String[] commonWords) {		
		if(commonWords != null) {
			for(int i = 0; i < commonWords.length; i++) {
				if(map.get(commonWords[i]) != null) {
					map.remove(commonWords[i]);
				}
			}
		}
		
		StringBuilder index = new StringBuilder();
		Iterator<String> iter = map.keySet().iterator();
		
		while (iter.hasNext()) {
			String key = iter.next();
			index.append(key + " " + map.get(key) + "\n");
		}		
		return index.toString();
	}
}