import sys, getopt

opts, args = getopt.getopt(sys.argv[1:], "hi:o:")

beanFilePath = ""
outPutPath = ""
for op, value in opts:
	if op == "-i":
		beanFilePath = value
	elif op == "-o":
		outPutPath = value
	else:
		sys.exit()

import xml.etree.ElementTree as ET

from os.path import sep

doc = ET.parse(beanFilePath)

root = doc.getroot()

package = root.get("package")


outPutPath = outPutPath + sep + package.replace(".", sep)

import os

if not os.path.exists(outPutPath):
	os.makedirs(outPutPath)

outPutPath = outPutPath + sep


mapBeans = root.iter("MapBean")

def createRoot(name):
	javaFile.write("public abstract class %s<T extends %s<T, V>, V> implements java.util.Map<String, V>{\n" %(name, name))
	javaFile.write("\tprivate static final int DEFAULT_INITIAL_CAPACITY = 16;\n")
	javaFile.write("\tprivate final java.util.Map<String, V> m;\n")

	javaFile.write("\tpublic MapBean() {\n")
	javaFile.write("\t\tthis(true);\n")
	javaFile.write("\t}\n")
	javaFile.write("\tprivate MapBean(boolean ordered){\n")
	javaFile.write("\t\tif(ordered){\n")
	javaFile.write("\t\t\tthis.m = new java.util.LinkedHashMap<String, V>(DEFAULT_INITIAL_CAPACITY);\n")
	javaFile.write("\t\t}else{\n")
	javaFile.write("\t\t\tthis.m = new java.util.HashMap<String, V>(DEFAULT_INITIAL_CAPACITY);\n")
	javaFile.write("\t\t}\n")
	javaFile.write("\t}\n")

	javaFile.write("\t@Override\n" +
		"\tpublic String toString() {\n" + 
		"\t\treturn com.alibaba.fastjson.JSONObject.toJSONString(this.m, true);\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic int size() {\n" + 
		"\t\treturn this.m.size();\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic boolean isEmpty() {\n" + 
		"\t\treturn this.m.isEmpty();\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic boolean containsKey(Object key) {\n" + 
		"\t\treturn this.m.containsKey(key);\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic boolean containsValue(Object value) {\n" + 
		"\t\treturn this.m.containsValue(value);\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic V get(Object key) {\n" + 
		"\t\treturn this.m.get(key);\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic V put(String key, V value) {\n" + 
		"\t\treturn this.m.put(key, value);\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic V remove(Object key) {\n" + 
		"\t\treturn this.m.remove(key);\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic void putAll(java.util.Map<? extends String, ? extends V> m) {\n" + 
		"\t\tthis.m.putAll(m);\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic void clear() {\n" + 
		"\t\tthis.m.clear();\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic java.util.Set<String> keySet() {\n" + 
		"\t\treturn this.m.keySet();\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic java.util.Collection<V> values() {\n" + 
		"\t\treturn this.m.values();\n"+
		"\t}\n")
	javaFile.write("\t@Override\n" +
		"\tpublic java.util.Set<java.util.Map.Entry<String, V>> entrySet() {\n" + 
		"\t\treturn this.m.entrySet();\n"+
		"\t}\n")
	javaFile.write("}")


def upperFirst(str):
	return str[0].upper() + str[1:]

def writeMap(attribute, name, classType):
	valueType = attribute.get("valueType")
	keyType = attribute.get("keyType")
	type = "java.util.Map"
	if classType != "final" and valueType == name:
		type = type + "<%s, T>" % (keyType)
		valueType = "T"
	else:
		type = type + "<%s, %s>" % (keyType, valueType)
	attrName = attribute.get("name");
	javaFile.write("\tprivate %s %s;\n" % (type, attrName))

	javaFile.write("\tpublic %s get%s(){\n\t\treturn this.%s;\n\t}\n"\
		% (type, upperFirst(attrName), attrName))
	returnType = name
	returnStr = "this"
	if classType != "final":
		returnType = "T"
		returnStr = "(T) this"
	javaFile.write("\tpublic %s set%s(%s %s){\n\t\tthis.%s=%s;\n\t\treturn %s;\n\t}\n"\
		%(returnType, upperFirst(attrName), type, attrName, attrName, attrName, returnStr))

	javaFile.write("\tpublic %s putIn%s(%s key, %s value){\n" % (returnType, upperFirst(attrName), keyType, valueType) +
		"\t\tif(this.%s == null) {\n" % (attrName)+
			"\t\t\tthis.%s = new java.util.HashMap<%s, %s>(16);\n" % (attrName, keyType, valueType)+
		"\t\t}\n"+
		"\t\tthis.%s.put(key, value);\n" % (attrName)+
		"\t\treturn %s;\n" % (returnStr)+
	"\t}\n")


def writeList(attribute, name, classType):
	valueType = attribute.get("valueType")
	type = "java.util.List"
	if classType != "final" and valueType == name:
		type = type + "<T>"
		valueType = "T"
	else:
		type = type + "<%s>" % (valueType)
	attrName = attribute.get("name")
	javaFile.write("\tprivate %s %s;\n" % (type, attrName));
	javaFile.write("\tpublic %s get%s(){\n\t\treturn this.%s;\n\t}\n"\
		% (type, upperFirst(attrName), attrName))
	returnType = name
	returnStr = "this"
	if classType != "final":
		returnType = "T"
		returnStr = "(T) this"
	javaFile.write("\tpublic %s set%s(%s %s){\n\t\tthis.%s=%s;\n\t\treturn %s;\n\t}\n"\
		%(returnType, upperFirst(attrName), type, attrName, attrName, attrName, returnStr))
	javaFile.write("\tpublic %s addIn%s(%s value){\n" % (returnType, upperFirst(attrName), valueType) +
		"\t\tif(this.%s == null) {\n" % (attrName)+
			"\t\t\tthis.%s = new java.util.ArrayList<%s>(16);\n" % (attrName,valueType)+
		"\t\t}\n"+
		"\t\tthis.%s.add(value);\n" % (attrName)+
		"\t\treturn %s;\n" % (returnStr)+
	"\t}\n")


def writeObj(attribute, name, classType):
	type = attribute.get("type")
	if type == name and classType != "final":
		type = "T";
	attrName = attribute.get("name")
	javaFile.write("\tprivate %s %s;\n" % (type, attrName))
	javaFile.write("\tpublic %s get%s(){\n\t\treturn this.%s;\n\t}\n"\
		% (type, upperFirst(attrName), attrName))
	returnType = name
	returnStr = "this"
	if classType != "final":
		returnType = "T"
		returnStr = "(T) this"
	javaFile.write("\tpublic %s set%s(%s %s){\n\t\tthis.%s=%s;\n\t\treturn %s;\n\t}\n"\
		%(returnType, upperFirst(attrName), type, attrName, attrName, attrName, returnStr))

def writeProList(property, name, classType):
	valueType = property.get("valueType")
	type = "java.util.List"
	type = type + "<%s>" % (valueType)
	proName = property.get("name");
	javaFile.write("\tpublic %s get%s(){\n\t\treturn (%s)this.get(\"%s\");\n\t}\n"\
		% (type, upperFirst(proName),type, proName))
	returnType = name
	returnStr = "this"
	if classType != "final":
		returnType = "T"
		returnStr = "(T) this"
	javaFile.write("\tpublic %s set%s(%s %s){\n\t\tthis.put(\"%s\",%s);\n\t\treturn %s;\n\t}\n"\
		%(returnType, upperFirst(proName), type, proName, proName, proName,returnStr))

	javaFile.write("\tpublic %s addIn%s(%s value){\n" % (returnType, upperFirst(proName), valueType) +
		"\t\tjava.util.List<%s> %s = this.get%s();\n" % (valueType, proName, upperFirst(proName))+
		"\t\tif(%s == null) {\n" % (proName)+
			"\t\t\t%s = new java.util.ArrayList<%s>(16);\n" % (proName, valueType)+
			"\t\t\tthis.put(\"%s\", %s);\n" % (proName, proName)+
		"\t\t}\n"+
		"\t\t%s.add(value);\n" % (proName)+
		"\t\treturn %s;\n" % (returnStr)+
	"\t}\n")

def writeProMap(property, name, classType):
	valueType = property.get("valueType")
	keyType = property.get("keyType")
	type = "java.util.Map"
	type = type + "<%s, %s>" % (keyType, valueType)
	proName = property.get("name");
	javaFile.write("\tpublic %s get%s(){\n\t\treturn (%s)this.get(\"%s\");\n\t}\n"\
		% (type, upperFirst(proName),type, proName))
	returnType = name
	returnStr = "this"
	if classType != "final":
		returnType = "T"
		returnStr = "(T) this"
	javaFile.write("\tpublic %s set%s(%s %s){\n\t\tthis.put(\"%s\",%s);\n\t\treturn %s;\n\t}\n"\
		%(returnType, upperFirst(proName), type, proName, proName, proName,returnStr))

	javaFile.write("\tpublic %s putIn%s(%s key, %s value){\n" % (returnType, upperFirst(proName), keyType, valueType) +
		"\t\tjava.util.Map<%s, %s> %s = this.get%s();\n" % (keyType, valueType, proName, upperFirst(proName))+
		"\t\tif(%s == null) {\n" % (proName)+
			"\t\t\t%s = new java.util.HashMap<%s, %s>(16);\n" % (proName, keyType, valueType)+
			"\t\t\tthis.put(\"%s\", %s);\n" % (proName, proName)+
		"\t\t}\n"+
		"\t\t%s.put(key, value);\n" % (proName)+
		"\t\treturn %s;\n" % (returnStr)+
	"\t}\n")


def writeTreeAttribute(name, classType):
	type = classType
	returnType = name
	returnStr = "this"
	if classType != "final":
		type = "T"
		returnType = "T"
		returnStr = "(T) this"

	childRenType = "java.util.Map<String,%s>" % (type)
	parentType = type;

	javaFile.write("\tprivate %s %s;\n" % (parentType, "parent"))
	javaFile.write("\tprivate %s %s;\n" % (childRenType, "children"))

	

	javaFile.write("\tpublic %s get%s(){\n\t\treturn this.%s;\n\t}\n"\
		% (parentType, upperFirst("parent"), "parent"))
	javaFile.write("\tpublic %s get%s(){\n\t\treturn this.%s;\n\t}\n"\
		% (childRenType, upperFirst("children"), "children"))

	javaFile.write("\tpublic %s set%s(%s %s){\n\t\tthis.%s=%s;\n\t\treturn %s;\n\t}\n"\
		%(returnType, upperFirst("parent"), parentType, "parent", "parent", "parent", returnStr))
	javaFile.write("\tpublic %s set%s(%s %s){\n\t\tthis.%s=%s;\n\t\treturn %s;\n\t}\n"\
		%(returnType, upperFirst("children"), childRenType, "children", "children", "children", returnStr))

	javaFile.write("\tpublic %s putChild(String key, %s value){\n" % (returnType, type) +
		"\t\tif(this.%s == null) {\n" % ("children")+
			"\t\t\tthis.%s = new java.util.HashMap<String, %s>(16);\n" % ("children",type)+
		"\t\t}\n"+
		"\t\tthis.%s.put(key, value);\n" % ("children")+
		"\t\tvalue.setParent((%s) this);;\n" % (parentType)+
		"\t\treturn %s;\n" % (returnStr)+
	"\t}\n")



def writeTreeProperty():
	pass

def writeProObject(property, name, classType):
	type = property.get("type")
	proName = property.get("name")
	javaFile.write("\tpublic %s get%s(){\n\t\treturn (%s)this.get(\"%s\");\n\t}\n"\
		% (type, upperFirst(proName),type, proName))
	returnType = name
	returnStr = "this"
	if classType != "final":
		returnType = "T"
		returnStr = "(T) this"
	javaFile.write("\tpublic %s set%s(%s %s){\n\t\tthis.put(\"%s\",%s);\n\t\treturn %s;\n\t}\n"\
		%(returnType, upperFirst(proName), type, proName, proName, proName,returnStr))

for mapBean in mapBeans:
	name = mapBean.get("name")
	javaFilePath = outPutPath + name + ".java"
	javaFile= open(javaFilePath, 'w')
	#写入包名称
	javaFile.write("package %s;\n" %(package))
	if mapBean.get("isRoot"):
		createRoot(name)
		javaFile.close()
		continue
	classType = mapBean.get("classType")
	

	if classType is None:
		classType = ""
	valueType = mapBean.get("valueType")
	extendClass = mapBean.get("extend")

	classStr = "public %s class %s<T extends %s, V> extends %s<%s, V>" % \
	(classType, name, name+"<T>", extendClass, name+"<T>")
	if valueType is None:
		if classType != "final":
			classStr = "public %s class %s<T extends %s> extends %s<%s>" % \
			(classType, name, name+"<T>", extendClass, name+"<T>")
		else:
			classStr = "public %s class %s extends %s<%s>" % \
			(classType, name, extendClass, name)
	else:
		if classType != "final":
			classStr = "public %s class %s<T extends %s> extends %s<%s, %s>" % \
			(classType, name, name+"<T>", extendClass, name+"<T>", valueType)
		else:
			classStr = "public %s class %s extends %s<%s, %s>" % \
			(classType, name, extendClass, name, valueType)

	javaFile.write(classStr+"{\n")

	if mapBean.get("isTree"):
		if mapBean.get("elementType") == "attribute":
			writeTreeAttribute(name, classType)


	attributes = mapBean.iter("attribute")
	for attribute in attributes:
		if attribute.get("type") == "List":
			writeList(attribute, name, classType)
		elif attribute.get("type") == "Map":
			writeMap(attribute, name, classType)
		else:
			writeObj(attribute, name, classType)

	properties = mapBean.iter("property")

	for property in properties:
		if property.get("type") == "List":
			writeProList(property, name, classType)
		elif property.get("type") == "Map":
			writeProMap(property, name, classType)
		else:
			writeProObject(property, name, classType)

	javaFile.write("}")
