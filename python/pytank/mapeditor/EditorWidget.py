#!/usr/bin/env python
####Python Tank######
#####地图编辑器######
##编辑区域组件##

from MapEditor.TileWidget import (ToggleToolBar)
from Properties import(tileWidth,tileHeight,tileTypes,tileImage,middle,large,hawkTypes,hawkId)
from PyQt5.QtWidgets import(QWidget,QVBoxLayout)
from PyQt5.QtCore import(Qt)
from PyQt5.QtGui import (QPainter,QPalette,QPixmap,QPen)
from math import (ceil,floor)
class Editor(QWidget):

	tiles = [] #地图区块表
	tilePixmaps = {}
	currentTile = 0
	showGrid = False
	mapArea = None
	mapSize = None
	def __init__(self,mapSize = middle):
		super(Editor,self).__init__()
		#self.setFixedSize(large["cols"]*tileWidth,large["rows"]*tileHeight)
		self.layout = QVBoxLayout()
		self.setLayout(self.layout)
		self.layout.setAlignment(Qt.AlignTop)
		self.initTilePixmaps()
		self.createTileWidget()
		self.createNewMap(mapSize)

	def createNewMap(self,mapSize,tiles = None):
		self.mapSize = mapSize
		self.rows = mapSize["rows"]
		self.cols = mapSize["cols"]
		self.width = tileWidth*self.cols
		self.height = tileHeight*self.rows
		self.initTiles(tiles)
		self.createMapArea()

	def initTiles(self,tiles):
		if tiles:
			self.tiles = tiles
			return
		self.tiles = []
		for x in range(self.cols):
			self.tiles.append([])
			for y in range (self.rows):	
				self.tiles[x].append(0)
		self.tiles[self.cols>>1][self.rows-1] = hawkId

	def initTilePixmaps(self):
		for typeId in tileTypes.keys():
			self.tilePixmaps[typeId] = QPixmap(tileImage.copy((typeId-1)*tileWidth,0,tileWidth,tileHeight))
		for typeId in hawkTypes.keys():	
			self.tilePixmaps[typeId] = QPixmap(tileImage.copy((typeId-1)*tileWidth,0,tileWidth,tileHeight))
				
	def createTileWidget(self):
		self.toggleBar = ToggleToolBar(self)
		self.layout.addWidget(self.toggleBar)
					
	def createMapArea(self):
		if self.mapArea != None:
			self.layout.removeWidget(self.mapArea)
			self.mapArea.close()
			self.mapArea.destroy()
		self.mapArea = MapArea(self)
		self.layout.addWidget(self.mapArea)
		self.layout.setAlignment(self.mapArea,Qt.AlignVCenter | Qt.AlignHCenter)

	def isShowGrid(self,checked):
		self.showGrid = checked
		self.mapArea.repaint()
		
	def tileSelected(self,tileId):
		self.currentTile = tileId

	def drawTiles(self,rect):
		#判断是否是鼠标单击
		drawType = self.currentTile
		if self.mapArea.isErase:
			drawType = 0
		if rect["x1"] == rect["x2"] and rect["y1"] == rect["y2"]:
			x = rect["x1"]
			y = rect["y1"]
			self.drawOne(x>>5,y>>5,drawType)
		else:
			x1 = max(0,floor(rect["x1"]/tileWidth)*tileWidth)
			x2 = min(self.width,ceil(rect["x2"]/tileWidth)*tileWidth)
			y1 = max(0,floor(rect["y1"]/tileHeight)*tileHeight)
			y2 = min(self.height,ceil(rect["y2"]/tileHeight)*tileHeight)
			self.drawArea(x1,y1,x2,y2,drawType)
		
	def drawArea(self,x1,y1,x2,y2,drawType):
		for x in range(x1,x2,tileWidth):
			for y in range(y1,y2,tileHeight):
				self.changeTile(x>>5,y>>5,drawType)
		self.mapArea.repaint()

	def drawOne(self,x,y,drawType):
		if x >= self.cols or y >= self.rows or x < 0 or y < 0:
			return
		self.changeTile(x,y,drawType)
		self.mapArea.repaint()

	def changeTile(self,x,y,drawType):
		if not (x == self.cols>>1 and y == self.rows-1):
			self.tiles[x][y] = drawType
			
class MapArea(QWidget):
	drawRect = False
	rect = {}
	isErase = False	
	def __init__(self,editor = None):
		super(MapArea, self).__init__()
		self.editor = editor
		self.setFixedSize(self.editor.width,self.editor.height)
		palette = QPalette()
		palette.setColor(QPalette.Background,Qt.black)
		self.setAutoFillBackground(True)
		self.setPalette(palette);
		self.rectPen = QPen(Qt.white,1,Qt.SolidLine)
		self.gridPen = QPen(Qt.gray,0.5,Qt.SolidLine)

	def paintEvent(self,event):
		mapPainter = QPainter(self)
		#画地图区块
		for x in range(self.editor.cols):
			for y in range(self.editor.rows):
				if self.editor.tiles[x][y] > 0:
					mapPainter.drawPixmap(x*tileWidth,y*tileHeight,self.editor.tilePixmaps[self.editor.tiles[x][y]])
		#画区域选择矩形
		if self.drawRect:
			mapPainter.setPen(self.rectPen)
			targetRect = self.rectFormat()
			mapPainter.drawRect(targetRect["x1"],targetRect["y1"],targetRect["x2"]-targetRect["x1"],targetRect["y2"]-targetRect["y1"])
		#画网格线	
		if self.editor.showGrid:
			mapPainter.setPen(self.gridPen)
			for x in range(tileWidth,self.editor.width,tileWidth):
				mapPainter.drawLine(x,0,x,self.editor.height)
			for y in range(tileHeight,self.editor.height,tileHeight):
				mapPainter.drawLine(0,y,self.editor.width,y)



	def mousePressEvent(self,event):
		x = event.x()
		y = event.y()
		self.rect["x1"] = x
		self.rect["y1"] = y
		self.drawRect = True

	def mouseMoveEvent(self,event):
		x = event.x()
		y = event.y()
		self.rect["x2"] = x
		self.rect["y2"] = y
		self.repaint()

	def mouseReleaseEvent(self,event):
		self.drawRect = False
		x = event.x()
		y = event.y()
		self.rect["x2"] = x
		self.rect["y2"] = y
		if event.button() == Qt.RightButton:
			self.isErase = True
		else:
			self.isErase = False
		self.editor.drawTiles(self.rectFormat())

	def rectFormat(self):
		x1 = min(self.rect["x1"],self.rect["x2"])
		x2 = max(self.rect["x1"],self.rect["x2"])
		y1 = min(self.rect["y1"],self.rect["y2"])
		y2 = max(self.rect["y1"],self.rect["y2"])	
		return {"x1":x1,"x2":x2,"y1":y1,"y2":y2}
		