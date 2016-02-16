from sys import argv

f = open(argv[1], "r")
read = True
tblname = ""
ind = 0

tbllist = []

for line in f:
	larr = line.strip().split(" ")
	if not len(larr) or not len(larr[0]):
		continue
	if larr[0] == "CREATE":
		tbllist.append([larr[-1], []])
		continue
	elif larr[0] == "(":
		read = True
		continue
	elif larr[0][0] == ")":
		read = False
		continue
	
	if read:
		attr = larr[0]
		var_type = larr[1][:1].upper() + larr[1][1:]
		var_type = var_type.strip(",")
		if var_type== "Text":
			var_type = "String"
		tbllist[-1][1].append((attr, var_type))
		
def cap_first_let(s):
	return s[0].upper()+s[1:]
		
def print_select(tbl):
	attrs = [el[0] for el in tbl[1]]
	print "final String SQL = \"select "+", ".join(attrs)+" from "+tbl[0]
	

def print_update(tbl):
	print "ContentValues values = new ContentValues();"
	name = tbl[0]
	for el in tbl[1]:
		print "values.put(\""+el[0]+"\", "+name+"."+cap_first_let(el[1])+");"
	
	print "\nlong id = db.insert(\""+name+"\", null, values);"
	print "if (id >= 0) {\n\treturn true;\n}\nelse return false;"


def print_sets(tbl):
	for i in xrange(len(tbl[1])):
		print "%s.%s = cursor.get%s(%d);" % (tbl[0], tbl[1][i][0], tbl[1][i][1],i)
	
		
for tbl in tbllist:
	print
	print_select(tbl)
	print
	print_sets(tbl)
	print
	print_update(tbl)