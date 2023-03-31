
import os
from more_itertools import is_sorted, pairwise

for subdir, dirs, files in os.walk('.'):
    for file in files:
        if len(file) == 11:
            file_path = os.path.join(subdir, file)
            print(file_path)
            with open(file_path) as f:
                numbers = [ int(line.split(',')[0]) for line in f.readlines() ]
                print(("✅" if is_sorted(numbers) else "❌") + " - Numbers sorted")
                print(("✅" if max(b - a for a, b in pairwise(numbers)) <= 1 else "❌") + " - Max Gap <= 1")
