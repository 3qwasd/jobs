##文件目录浏览器

from PyQt5.QtWidgets import (QFileSystemModel, QTreeView)

class SImageFolderView(QTreeView):
	"""S形象文件浏览器"""
	def __init__(self, indexDir):
		super(SImageFolderView, self).__init__()
		self.setFixedSize(300,630)
		if indexDir != None:
			self.initRootDir(indexDir)
	
	def resetRootDir(self, imageDir):
		self.initRootDir(imageDir)


	def setModel(self,model):
		self.fsModel = model
		super().setModel(model)

	#初始化显示的根目录
	def initRootDir(self,indexDir):
		if not indexDir:
			return
		self.indexDir = indexDir
		model = QFileSystemModel()
		model.setRootPath('')
		self.setModel(model)
		self.setAnimated(False)
		self.setIndentation(20)
		self.setSortingEnabled(True)
		self.setRootIndex(model.index(self.indexDir))
		for i in range(1,model.columnCount()):
			self.hideColumn(i)

	def getFilePathByModelIndex(self,modelIndex):
		return self.fsModel.filePath(modelIndex)

	#设置文件单击事件处理器
	def setClickedHandle(self,handle):
		self.clicked.connect(handle)
