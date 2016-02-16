from sys import argv

if len(argv) < 2:
			print "usage: [sqlfilename]"

s=""
tblname = ""
f = open(argv[1], "r")
for line in f:
	l = line.strip()
	a = l.split()
	if len(a) and a[0] == "CREATE":
		if len(s):
			print s+';'
			print "db.execSQL(create_"+tblname+");"
		tblname = a[-1]
		s = "final String create_"+tblname+" = "

	if len(l):
		s += '"'+l+'" + \n'

if len(tblname):
			print s+';'
			print "db.execSQL(create_"+tblname+");"
			s = "final String create_"+tblname+" = "
