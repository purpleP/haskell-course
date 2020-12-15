{-# LANGUAGE ScopedTypeVariables #-}
{-# LANGUAGE BangPatterns #-}
module Main where

import System.CPUTime
import Data.List.NonEmpty (fromList)
import Data.List
import SimpleFunctions
import System.Random
import System.Environment (getArgs)
import Control.Monad
import Text.Printf


main :: IO ()
main = do
    seed <- newStdGen
    (iterations:sizes) <- getArgs
    forM_ sizes $ \size -> do
        putStr size
        forM_ [1..read iterations] $ \i -> do
            let { xs :: [Int] = randomList (read size) seed }
            start <- getCPUTime
            let !result = minmax xs
            end <- getCPUTime
            printf "\t%d" $ end - start
        putStr "\n"

randomList n = take n . unfoldr (Just . random)
