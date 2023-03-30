
import sys

o = open(sys.argv[1][:7] + ".txt", "w")

with open(sys.argv[1]) as f:
    s, done = "", False
    for line in f.readlines():
        l = line.strip()
        if l[0] in ['-', '+']:
            continue
        s += l
        if not done:
            s += ","
        else:
            o.write(s + "\n")
            s = ""
        done = not done

o.close()
