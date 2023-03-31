
import sys
from more_itertools import chunked

with open(sys.argv[1]) as f, open(sys.argv[1][:7] + ".txt", "w") as o:
    o.write('\n'.join(','.join(c) for c in chunked(
        [l.strip() for l in f.readlines() if l[0] not in ['-', '+']], 2)))
