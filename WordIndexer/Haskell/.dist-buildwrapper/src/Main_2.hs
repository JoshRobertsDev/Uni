module Main_2 where
import qualified Data.List as List
import qualified Data.Char as Char
import qualified Data.Set as Set

main = do
    -- load file to be indexed and stop words
    text <- readFile "test.txt"
    stopWords <- readFile "stopWords.txt"

    -- normalise text by converting all chars to lower case and removing any non letters or spaces
    let word = map words (lines (normalise text))
    let stopWord = map words (lines (map Char.toLower stopWords))
    
    -- cast both lists to a set to remove duplicates and remove any instances of stopWord from word
    let index = Set.toList (Set.difference (Set.fromList (concat word)) (Set.fromList (concat stopWord)))
    
    -- zip list of words and their line numbers into a tuple and print
    mapM print (List.zip index (generateIndex index word))
    
normalise :: String -> String
normalise = filter (\x -> Char.isLetter x || Char.isSpace x) . map Char.toLower
    
generateIndex :: [String] -> [[String]] -> [[Int]]
generateIndex xs y = foldr (\ x -> (++) [getLineNumbers x y]) [] xs

getLineNumbers' :: String -> [[String]] -> Int -> [Int]
getLineNumbers' x [] n = []
getLineNumbers' x (y:ys) n
    | x `elem` y = n : getLineNumbers' x ys (n + 1)
    | otherwise = getLineNumbers' x ys (n + 1)
    
getLineNumbers :: String -> [[String]] -> [Int]
getLineNumbers x y = getLineNumbers' x y 1