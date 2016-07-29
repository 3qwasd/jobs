#!/usr/bin/env python
####Python Tank######
#####地图编辑器######
from PyQt5.QtCore import (Qt)
from PyQt5.QtGui import (QIcon)
from PyQt5.QtWidgets import (QMainWindow,QVBoxLayout,QWidget,QAction,QDialog,QGridLayout,
	QButtonGroup,QRadioButton,QHBoxLayout,QPushButton,QFileDialog)
from MapEditor.EditorWidget import (Editor)
from Properties import (small,middle,large,mapTypes)
#地图编辑器主界面
class MainUI(QMainWindow):
	currFile = None
	def __init__(self):
		super(MainUI,self).__init__()
		self.setWindowTitle("PyTank Map Editor V1.0")
		self.setWindowIcon(QIcon("resource/tank.ico"))
		self.editArea = QWidget()
		self.editAreaContainer = QVBoxLayout()
		self.setCentralWidget(self.editArea)
		self.editArea.setLayout(self.editAreaContainer)
		self.editor = Editor(small)
		self.editAreaContainer.addWidget(self.editor)
		self.createToolBars()

	def createToolBars(self):
		self.mapToolBar = self.addToolBar("Map")
		newMapAct = QAction(QIcon("MapEditor/images/new.png"),"创建新地图",self,
			statusTip="创建新地图",triggered = self.newMapConfig)
		self.mapToolBar.addAction(newMapAct)
		loadMapAct = QAction(QIcon("MapEditor/images/open.png"),"载入地图",self,
			statusTip="载入地图",triggered = self.loadMap)
		self.mapToolBar.addAction(loadMapAct)
		saveMapAct = QAction(QIcon("MapEditor/images/save.png"),"保存地图",self,
			statusTip="保存地图",triggered = self.saveMap)
		self.mapToolBar.addAction(saveMapAct)
		self.editToolBar = self.addToolBar("Edit")

	def saveMap(self):
		if self.currFile:
			self.saveMapFile(self.currFile)
		else:
			self.saveMapAs()

	def saveMapAs(self):
		options = QFileDialog.Options()
		options |= QFileDialog.DontUseNativeDialog
		fileName , _ = QFileDialog.getSaveFileName(self,"保存地图文件","maps","PyTank Map Files (*.ptm)",options=options)
		fileName = fileName if fileName.endswith(".ptm") else fileName+".ptm"
		self.saveMapFile(fileName)

	def saveMapFile(self,fileName):
		mapFile = open(fileName,'w')
		mapFile.write(str(self.editor.mapSize))
		mapFile.write("\n")
		mapFile.write(str(self.editor.tiles))
		mapFile.close()
		self.currFile = fileName

	def loadMap(self):
		options = QFileDialog.Options()
		options |= QFileDialog.DontUseNativeDialog
		fileName , _ = QFileDialog.getOpenFileName(self,"载入地图文件","maps","PyTank Map Files (*.ptm)",options=options)
		if fileName :
			mapFile = open(fileName,'r')
			self.currFile = fileName
			mapSize = eval(mapFile.readline())
			tiles = eval(mapFile.readline())
			self.editor.createNewMap(mapSize,tiles)
			self.editor.adjustSize()
			self.editArea.adjustSize()
			self.adjustSize()
			mapFile.close()

	def newMapConfig(self):
		dialog = MapConfigPanel()
		dialog.okButton.clicked.connect(lambda:self.createNewMap(dialog))
		dialog.exec_()
		dialog.destroy()	

	def createNewMap(self,config):
		self.currFile = None
		selectId = config.sizeGroup.checkedId()
		self.editor.createNewMap(mapTypes[selectId])
		config.cancelConfig()
		#注意内部控件由大变小，想要窗口自适应，必须依次由内而外调用各控件的
		#的adjustSize()方法
		self.editor.adjustSize()
		self.editArea.adjustSize()
		self.adjustSize()

class MapConfigPanel(QDialog):

	def __init__(self):
		super(QDialog,self).__init__()
		self.setWindowTitle("New map")
		self.layout = QVBoxLayout()
		self.setLayout(self.layout)
		self.sizeGroup = QButtonGroup()
		smallDescribe = "小型地图：%d * %d" %(small["rows"],small["cols"])
		smallSize = QRadioButton(smallDescribe,checked=True)
		self.sizeGroup.addButton(smallSize,1)
		self.layout.addWidget(smallSize)
		middleDescribe = "中型地图：%d * %d" %(middle["rows"],middle["cols"])
		middleSize = QRadioButton(middleDescribe)
		self.sizeGroup.addButton(middleSize,2)
		self.layout.addWidget(middleSize)
		largeDescribe = "大型地图：%d * %d" %(large["rows"],large["cols"])
		largeSize = QRadioButton(largeDescribe)
		self.sizeGroup.addButton(largeSize,3)
		self.layout.addWidget(largeSize)
		self.buttonLayout = QHBoxLayout()
		self.layout.addLayout(self.buttonLayout)
		self.okButton = QPushButton("ok")
		self.buttonLayout.addWidget(self.okButton)
		cancelButton = QPushButton("cancel")
		self.buttonLayout.addWidget(cancelButton)
		cancelButton.clicked.connect(self.cancelConfig)

	def cancelConfig(self):
		self.destroy()


		



