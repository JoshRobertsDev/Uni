module Main where
import qualified Data.List as List
import qualified Data.Char as Char
import qualified Data.Set as Set
import qualified Data.Maybe as Maybe

main = do
    -- load file to be indexed and stop words
    text <- readFile "test.txt"
    stopWords <- readFile "stopWords.txt"

    -- convert all chars to lower case, split by lines, then split by words
    let word = map words (lines (map Char.toLower text))
    let stopWord = map words (lines (map Char.toLower stopWords))
    
    -- concatenate lists, remove non letters, then remove any empty list elements
    let word' = filter (not.null) (map (filter Char.isLetter) (concat word))
    let stopWord' = filter (not.null) (map (filter Char.isLetter) (concat stopWord))
    
    -- cast both lists to a set to remove duplicates and remove any instances of stopWord' from word'
    let index = Set.toList (Set.difference (Set.fromList word') (Set.fromList stopWord'))
    
    -- get line numbers from indexed words
    let lineNumbers = generateIndex index word
    
    -- zip
    mapM print (List.zip index lineNumbers)
    
generateIndex :: [String] -> [[String]] -> [[Int]]
generateIndex xs y = foldr (\ x -> (++) [getLineNumbers x y]) [] xs

getLineNumbers' :: String -> [[String]] -> Int -> [Int]
getLineNumbers' x [] n = []
getLineNumbers' x (y:ys) n
    | x `elem` y = n : getLineNumbers' x ys (n + 1)
    | otherwise = getLineNumbers' x ys (n + 1)
    
getLineNumbers :: String -> [[String]] -> [Int]
getLineNumbers x y = getLineNumbers' x y 1