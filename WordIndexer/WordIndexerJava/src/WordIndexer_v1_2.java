import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class WordIndexer_v1_2 {
	public static void main(String[] args) {
		String text =	"The cat ' cat , cat - sat + on the mat\n" + "The dog chased the cat\n" + "The cat ran from the dog";
		String[] omittedWords = {"the","on","from"}; 
		
		for(Iterator<Entry<String, Set<Integer>>> index = generateIndex(text, omittedWords).entrySet().iterator(); index.hasNext();) {
			Entry<String, Set<Integer>> entry = index.next();
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}
	
	public static SortedMap<String, Set<Integer>> generateIndex(String text, String[] omittedWordsArray) {
		Set<String> omittedWords = new HashSet<String>(Arrays.asList(omittedWordsArray));
		SortedMap<String, Set<Integer>> indexMap = new TreeMap<String, Set<Integer>>();		
		List<String> lines = Arrays.asList(text.split("\n"));
		
		int lineNumber = 1;
		for(Iterator<String> line = lines.iterator(); line.hasNext(); lineNumber++) {
			List<String> words = Arrays.asList(line.next().toLowerCase().replaceAll("[^A-Za-z0-9 ]+", "").split("\\s+"));
			for(Iterator<String> word = words.iterator(); word.hasNext();) {
				String key = word.next();
				if(!omittedWords.contains(key)) {
					Set<Integer> tempSet = new HashSet<Integer>();
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
	
	public static SortedMap<String, Set<Integer>> generateIndex(String text) {
		String[] omittedWords = new String[0];
		return generateIndex(text, omittedWords);
	}
}