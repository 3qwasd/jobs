#战场形象浏览器

from PyQt5.QtCore import (Qt)
from PyQt5.QtWidgets import (QWidget,QHBoxLayout,QVBoxLayout,QToolBar,QAction,QPushButton)
from PyQt5.QtGui import (QImage,QPixmap,QColor,QPalette)
from Widget.DirectoryView import (SImageFolderView)
from Widget.AnimationView import (AnimationView)
from Widget.FrameSelecter import (ToggleToolBar)
from Properties import (eliminateBackgroundColor,frameWidth,frameHeight)
import os
import re
class SImageBrower(QWidget):
	"""docstring for SImageBrower"""
	atkRegx = re.compile('.*[aA][tT][kK].*\.[(bmp)(BMP)]')
	movRegx = re.compile('.*[Mm][Oo][Vv].*\.[(bmp)(BMP)]')
	spcRegx = re.compile('.*[Ss][Pp][CcEe].*\.[(bmp)(BMP)]')
	def __init__(self, indexDir):
		super(SImageBrower, self).__init__()
		self.indexDir = indexDir
		self.container = QHBoxLayout()
		self.container.addStretch(0)
		self.container.setAlignment(Qt.AlignLeft)
		self.setLayout(self.container)
		self.folderBrower = SImageFolderView(indexDir)
		self.container.addWidget(self.folderBrower)
		self.folderBrower.setClickedHandle(self.folderSelectedHandle)
		self.animationPanel = AnimationPanel()
		self.frameSelecter = ToggleToolBar()
		middleLayout = QVBoxLayout()
		self.container.addLayout(middleLayout)
		self.initToolBar()
		middleLayout.addWidget(self.frameToolBar)
		middleLayout.addWidget(self.frameSelecter)
		self.container.addWidget(self.animationPanel)


	def resetImageDir(self, imageDir):
		self.indexDir = imageDir
		self.folderBrower.resetRootDir(imageDir)

	def initToolBar(self):
		self.frameToolBar = QToolBar()
		self.frameToolBar.setFixedSize(300,40)
		#button
		self.frameToolBar.addSeparator()
		newAnimationButton = QPushButton("创建新动画",self)
		newAnimationButton.setFixedSize(80,30)
		newAnimationButton.clicked.connect(self.createNewAnimationView)
		self.frameToolBar.addWidget(newAnimationButton)
		#button
		self.frameToolBar.addSeparator()
		saveAnimationButton = QPushButton("保存新动画为连续图片",self)
		saveAnimationButton.setFixedSize(140,30)
		saveAnimationButton.clicked.connect(self.saveNewAnimation)
		self.frameToolBar.addWidget(saveAnimationButton)
		#button
		self.frameToolBar.addSeparator()
		clearAll = QPushButton("清除",self)
		clearAll.setFixedSize(50,30)
		clearAll.clicked.connect(lambda :self.frameSelecter.clearAllSelected())
		self.frameToolBar.addWidget(clearAll)
		
	
	def createNewAnimationView(self):
		bmps = self.frameSelecter.getSelected()
		if bmps:
			self.animationPanel.addAnimationView(AnimationView(bmps))
			self.frameSelecter.clearAllSelected()

	def saveNewAnimation(self):
		pass

	def refreshAnimationPanel(self):
		if self.animationPanel:
			self.animationPanel.closeAll()

	def folderSelectedHandle(self,modelIndex):
		filePath =self.folderBrower.getFilePathByModelIndex(modelIndex)
		if not os.path.exists(filePath) : return
		if not os.path.isdir(filePath) : return
		files = os.listdir(filePath)
		atkImags = []
		movImags = []
		spcImags = []
		##形象文件分类
		for fileName in files:
			fileAbsPath = "".join([filePath,"\\",fileName])
			if self.atkRegx.match(fileName):
				atkImags.append(fileAbsPath)
			if self.movRegx.match(fileName):
				movImags.append(fileAbsPath)
			if self.spcRegx.match(fileName):
				spcImags.append(fileAbsPath)
		self.frameSelecter.removeAllButton()
		self.createImageButtons(atkImags,movImags,spcImags)
		self.frameSelecter.clearAllSelected()
		self.refreshAnimationPanel()
		self.createAtkAnimationView(atkImags)
		self.createMoveAnimationView(movImags)
		self.createDefAnimationView(movImags,spcImags)

	def createImageButtons(self,atk,mov,spc):
		if atk:
			self.frameSelecter.createImageButtons(atk[0])
		if mov:
			self.frameSelecter.createImageButtons(mov[0])
		if spc:
			self.frameSelecter.createImageButtons(spc[0])

	def createAnimationView(self,img,groupCount,frameCount,needTurn):
		frameSize = img.width()
		backColor = None
		if not backColor:
			backColor = QColor(img.pixel(0,0))
		self.animationPanel.setBackGround(backColor)
		#共groupCout组图片
		for i in range(0,groupCount):
			bmps = []
			#每组动画frameCount幀图片
			for j in range(0,frameCount):
				bmp = QPixmap(img.copy(0,(j+i*frameCount)*frameSize,frameSize,frameSize))
				eliminateBackgroundColor(bmp)
				bmps.append(bmp)
			#创建动画视图
			self.animationPanel.addAnimationView(AnimationView(bmps))
		if needTurn:
			bmps=[]
			for j in range(0,frameCount):
				bmp = QPixmap(img.copy(0,(j+(groupCount-1)*frameCount)*frameSize,frameSize,frameSize).mirrored(True,False))
				eliminateBackgroundColor(bmp)
				bmps.append(bmp)
			#创建动画视图
			self.animationPanel.addAnimationView(AnimationView(bmps))

	def createAtkAnimationView(self,atkImags):
		if not atkImags : return
		for imgPath in atkImags:
			atkImg = QImage(imgPath)
			self.createAnimationView(atkImg,3,4,True)

	def createMoveAnimationView(self,movImags):
		if not movImags : return
		for imgPath in movImags:
			movImg = QImage(imgPath)
			self.createAnimationView(movImg,3,2,True)


	def createDefAnimationView(self,movImags,spcImags):
		if not spcImags or not movImags : return
		spcImg,movImg = QImage(spcImags[0]),QImage(movImags[0])
		spcFrameSize,movFrameSize = spcImg.width(),movImg.width()
		for i in range(0,3):
			firstFrame = QPixmap(movImg.copy(0,(i+6)*movFrameSize,movFrameSize,movFrameSize))
			secondFrame = QPixmap(spcImg.copy(0,i*spcFrameSize,spcFrameSize,spcFrameSize))
			eliminateBackgroundColor(firstFrame)
			eliminateBackgroundColor(secondFrame)
			bmps = [firstFrame,secondFrame]
			self.animationPanel.addAnimationView(AnimationView(bmps))
			if i == 2:
				firstFrame = QPixmap(firstFrame.toImage().mirrored(True,False))
				secondFrame = QPixmap(secondFrame.toImage().mirrored(True,False))
				bmps = [firstFrame,secondFrame]
				self.animationPanel.addAnimationView(AnimationView(bmps))

	def createAwaitAnimationView(self,atkImags,movImags):
		if not atkImags : return
		if not movImags : return

class AnimationPanel(QWidget):
	"""docstring for AnimationPanel"""
	rows = []
	views = []
	currRow = 0
	currCol = 0
	maxRow = 4
	maxCol = 4
	isSetBackground = False
	def __init__(self):
		super(AnimationPanel, self).__init__()
		self.setFixedSize(480,600)
		self.container = QVBoxLayout()
		self.setLayout(self.container)
		self.container.setSpacing(32)
		self.container.setAlignment(Qt.AlignTop)
		for i in range(0,self.maxRow):
			self.addRow()

	def addRow(self):
		row = QHBoxLayout()
		row.setSpacing(64)
		row.setAlignment(Qt.AlignLeft)
		self.rows.append(row)
		self.container.addLayout(row)

	def setBackGround(self,backColor):
		if self.isSetBackground : return
		palette = QPalette()
		palette.setColor(QPalette.Background,backColor)
		self.setAutoFillBackground(True)
		self.setPalette(palette);
		self.isSetBackground = True

	def closeAll(self):
		for i in range(0,self.maxRow):
			row = self.rows[i]
			for j in range(i*self.maxCol,min(i*self.maxCol+4,len(self.views))):
				row.removeWidget(self.views[j])
				self.views[j].close()
		self.views = []
		self.isSetBackground = False
		self.currRow = 0
		self.currCol = 0
			

	def addAnimationView(self,animationView):
		self.views.append(animationView)
		if self.currRow == self.maxRow:
			self.addRow()
			self.maxRow += 1
		self.rows[self.currRow].addWidget(animationView)
		animationView.createAnimation()
		self.currRow = self.currRow+1 if self.currCol == self.maxCol-1 else self.currRow
		self.currCol = 0 if self.currCol == self.maxCol-1 else self.currCol+1
		