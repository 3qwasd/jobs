#!/usr/bin/env python
####Python Tank######
########属性#########

from PyQt5.QtGui import (QImage,QPixmap)

tileWidth = 32
tileHeight = 32
tileImage = QImage(".\\resource\\tile.bmp")
tileTypes = {1:"城墙",2:"混凝土",3:"草地",4:"湖泊",5:"湖泊",8:"雪原",9:"沙漠"}
hawkTypes = {6:"完整",7:"废墟"}
hawkId = 6
small = {"rows":11,"cols":15}
middle = {"rows":13,"cols":19}
large = {"rows":15,"cols":23}
mapTypes = {1:small,2:middle,3:large}