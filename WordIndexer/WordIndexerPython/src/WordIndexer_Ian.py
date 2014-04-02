from collections import defaultdict, OrderedDict
import re

def generate_index(text, omitted_words=[]):
    index = defaultdict(list)
    for i, line in enumerate(text.split("\n")):
        for word in line.split():
            sanitised_word = re.sub("\W", "", word).lower()
            if sanitised_word and sanitised_word not in omitted_words and i+1 not in index[sanitised_word]:
                index[sanitised_word].append(i+1)
    return OrderedDict(sorted(index.items()))

text =  "The cat sat ' sat on the mat\n" + "The dog chased the cat\n" + "The cat ran from the dog"
omitted_words = ["the","on","from"]

index = generate_index(text, omitted_words)
longest_word_length = max(len(word) for word in index)
for word, appearances in index.items():
    print(word.ljust(longest_word_length), str(appearances))