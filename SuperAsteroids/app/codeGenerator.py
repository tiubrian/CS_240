import sys
#print sys.argv
if len(sys.argv) < 2:
    print "usage: [sqlfilename]"

class field:
    def __init__(self,name, t):
        self.name = name
        self.type = t
    def __str__(self):
        return self.name+": "+self.type
    def __repr__(self):
        return self.name+": "+self.type


def cap_first_let(s):
    return s[0].upper()+s[1:]


class sqlParser:
    def __init__(self, fname):
        self.tables = {}
        f = open(fname, "r")
        tname = ""
        bad_chars = set(["(",")",";"])
        for line in f:
            l = line.strip().split(" ")
            if not len(l) or not len(l[0]):
                continue
            elif l[0] == "CREATE":
                tname = l[-1]
                self.tables[tname] = []
            elif l[0][0] in bad_chars:
             continue
            else:
                self.tables[tname].append(self.parse_tuple(l))
        f.close()

    def parse_tuple(self,l):
        l[1] = l[1].strip(",")
        if l[1].lower() == "text":
            l[1] = "String"
        return field(l[0],l[1])

    def put(self):
        res = ""
        for n in self.tables:
            tbl = self.tables[n]
            res += "ContentValues values = new ContentValues();\n"
            for f in tbl:
                res += "values.put(\""+f.name+"\", "+n+"."+f.name+");\n"
            res += "\n long id = db.insert\""+n+"\", null, values);\n"
            res += "if (id >= 0) {\n\treturn true;\n}\n"
            res += "else {\n\treturn false;\n}\n\n"
        return res

    def selects(self):
        res = ""
        for n in self.tables:
             tbl = self.tables[n]
             res += "final String SQL = \"select " +", ".join([f.name for f in tbl])+" from "+n+"\";"
             res += "\n"
        return res

s = sqlParser(sys.argv[1])
res = s.selects()
if len(sys.argv) > 2:
    f = open(sys.argv[2],"w")
    f.write(res)
    f.close()
else:
    print res
