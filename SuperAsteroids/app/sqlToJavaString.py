from sys import argv

if len(argv) < 2:
			print "usage: [sqlfilename]"

s=""
f = open(argv[1], "r")
for line in f:
	l = line.strip()
	if len(l):
		s += '"'+l+'" + \n'

print s