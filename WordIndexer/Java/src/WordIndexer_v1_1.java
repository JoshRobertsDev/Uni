import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class WordIndexer_v1_1 {
	public static void main(String[] args) {
		String text =	"The cat cat sat sat on the mat\n" + "The dog chased the cat\n" + "The cat ran from the dog";
		String[] omittedWords = {"the","on","from"}; 
		
		for(Iterator<Entry<String, List<Integer>>> index = generateIndex(text, omittedWords).entrySet().iterator(); index.hasNext();) {
			Entry<String, List<Integer>> entry = index.next();
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
	
	public static SortedMap<String, List<Integer>> generateIndex(String text, String[] omittedWordsArray) {
		List<String> omittedWords = new ArrayList<String>(Arrays.asList(omittedWordsArray));
		SortedMap<String, List<Integer>> indexMap = new TreeMap<String, List<Integer>>();		
		List<String> lines = Arrays.asList(text.split("\n"));
		
		int lineNumber = 1;
		for(Iterator<String> line = lines.iterator(); line.hasNext(); lineNumber++) {
			List<String> words = Arrays.asList(line.next().toLowerCase().replaceAll("[^A-Za-z0-9 ]+", "").split("\\s+"));
			for(Iterator<String> word = words.iterator(); word.hasNext();) {
				String key = word.next();
				if(!omittedWords.contains(key)) {
					List<Integer> tempSet = new ArrayList<Integer>();
					if(indexMap.containsKey(key)) {
						tempSet = indexMap.get(key);
					}
					tempSet.add(lineNumber);
					indexMap.put(key, tempSet);
				}
			}
		}
		return indexMap;
	}
	
	public static SortedMap<String, List<Integer>> generateIndex(String text) {
		String[] omittedWords = new String[0];
		return generateIndex(text, omittedWords);
	}
}