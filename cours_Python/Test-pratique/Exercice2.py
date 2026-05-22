# Trouver le mot le plus court et le plus long dans une phrase


def get_min_and_max_words(sentense):
    words = sentense.split(" ")
    
    sentence_min = min(words, key=len)
    sentence_max = max(words, key=len)
    
    return (sentence_min, sentence_max) 
    

s = "Un chasseur sachant Nsengimanafranck chasser sait chasser sans son chien"

min_word, max_word = get_min_and_max_words(s)
print("Mot le plus petit", min_word)
print("Mot le plus long", max_word)