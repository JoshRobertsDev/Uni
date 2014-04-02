from collections import defaultdict, OrderedDict
import re

def generate_index(text, omitted_words=[]):
    index = defaultdict(list)
    for i, line in enumerate(re.sub("[^\w\n\s]+", "", text.lower()).split("\n")):
        for word in line.split():
            if word not in omitted_words and (i+1) not in index[word]:
                index[word].append(i+1)
    return OrderedDict(sorted(index.items()))

text =  "The cat cat sat on the mat\n" + "The dog chased the cat\n" + "The cat ran from the dog"
omitted_words = ["the","on","from"]

index = generate_index(text, omitted_words)
for word, appearances in index.items(): 
    print(word, appearances)