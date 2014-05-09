module Main_2 where
import qualified Data.List as List
import qualified Data.Char as Char
import qualified Data.Set as Set
import qualified Data.Map as Map
import qualified Data.Maybe as Maybe

main = do
    -- load file to be indexed and stop words
    text <- readFile "test.txt"
    stopWords <- readFile "stopWords.txt"

    -- normalise text by converting all chars to lower case and removing any non letters or spaces
    let word = map words (lines (normalise text))
    let stopWord = map words (lines (map Char.toLower stopWords))
    
    -- cast both lists to a set to remove duplicates and remove any instances of stopWord from word
    let index = Set.toAscList (Set.difference (Set.fromList (concat word)) (Set.fromList (concat stopWord)))
    -- get list of line numbers for each word in index and print result 
    mapM print (generateIndex index word)
    
normalise :: String -> String
normalise = filter (\x -> Char.isLetter x || Char.isSpace x) . map Char.toLower

generateIndex :: [String] -> [[String]] -> [(String, [Int])]
generateIndex [] y = []
generateIndex (x:xs) y = [(x, getLineNumbers x y)] ++ (generateIndex xs y)

getLineNumbers' :: String -> [[String]] -> Int -> [Int]
getLineNumbers' x [] n = []
getLineNumbers' x (y:ys) n
    | x `elem` y = n : getLineNumbers' x ys (n + 1)
    | otherwise = getLineNumbers' x ys (n + 1)
    
getLineNumbers :: String -> [[String]] -> [Int]
getLineNumbers x y = getLineNumbers' x y 1