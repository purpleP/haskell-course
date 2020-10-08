module SimpleFunctions
    ( merge
    , minmax
    ) where

import Data.List.NonEmpty (NonEmpty, NonEmpty((:|)))

merge :: (Ord a) => [a] -> [a] -> [a]
merge xs [] = xs
merge [] ys = ys
merge left@(l:ls) right@(r:rs) = if l <= r
                                    then l : merge ls right
                                    else r : merge left rs

minmax :: (Ord a) => NonEmpty a -> (a, a)
minmax (x :| xs) = minmax' (x, x) xs where
    minmax' (lo, hi) [] = (lo, hi)
    minmax' (lo, hi) [y] = if y < lo
                              then (y, hi)
                              else if y > hi then (lo, y) else (lo, hi)
    minmax' (lo, hi) (y1:y2:ys) = minmax' (lo', hi') ys where
        (l, h) = if y1 <= y2 then (y1, y2) else (y2, y1)
        lo' = if l < lo then l else lo
        hi' = if h > hi then h else hi

