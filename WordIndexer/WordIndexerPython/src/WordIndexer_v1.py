import re

def generateIndex(text, commonWords):
    index = dict()
    for i,line in enumerate(text):
        words = line.split()
        for word in words:
            if commonWords is None or word not in commonWords:
                if word not in index: index[word] = [i+1]     
                elif (i+1) not in index[word]: index[word].extend([i+1])
    return index

text =  "The cat;   sat on the mat\n" + "The dog .,'chased the cat\n" + "The cat ran from the dog"
textArray = re.sub('[^\w\n\s]+', '', text.lower()).split("\n")
commonWords = ["the","on","from"]

index = generateIndex(textArray, commonWords)
for word in sorted(index): print(word + " " + str(index[word]))