#!/usr/bin/env python
####Python Tank######
#####地图编辑器######
##编辑区域组件##

from PyQt5.QtWidgets import(QPushButton,QButtonGroup,QToolBar)
from PyQt5.QtGui import (QPen,QPainter,QBrush,QPixmap)
from PyQt5.QtCore import (Qt)
from Properties import(tileWidth,tileHeight,tileTypes,tileImage)



class ToggleToolBar(QToolBar):

	def __init__(self,editor=None):
		super(ToggleToolBar, self).__init__()
		self.editor = editor		
		self.createTileButtons()
		self.addSeparator()
		self.createGridButton()
		self.setFixedHeight(tileHeight+16)

	def createTileButtons(self):
		self.tileGroup = QButtonGroup()
		for tileTypeId,tileName in tileTypes.items():
			tileButton = TileToggleButton(tileTypeId,self.editor.tilePixmaps[tileTypeId])
			tileButton.setToolTip(tileName)
			self.tileGroup.addButton(tileButton,tileTypeId)
			self.addWidget(tileButton)
		self.tileGroup.buttonClicked[int].connect(self.editor.tileSelected)

	def createGridButton(self):
		self.gridButton = GridToggleButton()
		self.addWidget(self.gridButton)
		self.gridButton.toggled.connect(self.editor.isShowGrid)



class ToggleButton(QPushButton):
	
	def __init__(self):
		super(ToggleButton, self).__init__()
		self.setFixedSize(tileWidth+8,tileHeight+8)
		self.setCheckable(True)
		self.setChecked(False)
		self.checkedPen = QPen(Qt.red,2,Qt.SolidLine)

class TileToggleButton(ToggleButton):

	def __init__(self,tileType,pixMap):
		super(TileToggleButton, self).__init__()
		self.tileType = tileType
		self.tilePixMap = pixMap

	def paintEvent(self,event):
		super().paintEvent(event)
		painter = QPainter(self)
		painter.drawPixmap(4,4,self.tilePixMap)
		

class GridToggleButton(ToggleButton):

	def __init__(self):
		super(GridToggleButton, self).__init__()
		self.gridPen = QPen(Qt.white,1,Qt.SolidLine)
		self.backBrush = QBrush(Qt.black)
		self.setToolTip("网格")

	def paintEvent(self,event):
		super().paintEvent(event)
		gridPainter = QPainter(self)
		gridPainter.fillRect(4,4,tileWidth,tileHeight,self.backBrush)
		gridPainter.setPen(self.gridPen)
		for i in range(12,36,8):
			gridPainter.drawLine(i,4,i,tileHeight+4)
			gridPainter.drawLine(4,i,tileWidth+4,i)