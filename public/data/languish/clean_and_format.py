
import sys
from more_itertools import chunked

o = open(sys.argv[1][:7] + ".txt", "w")

with open(sys.argv[1]) as f:
    lines = [','.join(c) for c in chunked(
        [l.strip() for l in f.readlines() if l[0] not in ['-', '+']], 2)]
    for line in lines:
        o.write(line + "\n")

o.close()
