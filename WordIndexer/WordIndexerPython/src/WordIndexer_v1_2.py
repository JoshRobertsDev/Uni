from collections import defaultdict, OrderedDict
import re
 
def generate_index(text, omitted_words=[]):
    index = defaultdict(set)
    for i, line in enumerate(text.split("\n")):
        for word in line.split():
            sanitised_word = re.sub("\W", "", word).lower()
            if sanitised_word and sanitised_word not in omitted_words:
                index[sanitised_word].add(i+1)
    return OrderedDict(sorted((word, sorted(lines)) for word, lines in index.items()))
 
text =  "The cat sat on the mat\n" + "The dog .,'chased the cat\n" + "The cat ran from the dog"
omitted_words = ["the","on","from"]
 
index = generate_index(text, omitted_words)
for word, appearances in index.items(): 
    print(word, appearances)