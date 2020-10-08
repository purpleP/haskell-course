{-# LANGUAGE ScopedTypeVariables #-}
module Main where


import Data.List.NonEmpty (fromList)
import Data.List
import SimpleFunctions
import System.Random
import System.Environment (getArgs)

main :: IO ()
main = do
    seed <- newStdGen
    size <- (read . head) <$> getArgs
    let xs :: [Int] = randomList size seed
    print $ minmax $ fromList xs

randomList n = take n . unfoldr (Just . random)
