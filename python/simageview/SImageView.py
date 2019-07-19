from Widget.SImageBrower import (SImageBrower)
from PyQt5.QtWidgets import (QApplication, QMainWindow, QWidget, QVBoxLayout, QAction, QFileDialog)
from PyQt5.QtGui import (QIcon, QKeySequence)
from PyQt5.QtCore import (Qt)

class SImageView(QMainWindow):
	"""docstring for SImageBrower"""
	def __init__(self, imageBrower, title = None):
		super(SImageView, self).__init__()
		self.setWindowTitle(title if title != None else "形象编辑器")
		self.setWindowIcon(QIcon("./resource/test.ico"))
		self.mainWidget = QWidget()
		self.mainContainer = QVBoxLayout()
		self.mainContainer.setAlignment(Qt.AlignLeft)
		self.setCentralWidget(self.mainWidget)
		self.mainWidget.setLayout(self.mainContainer)
		self.imageBrower = imageBrower
		self.mainContainer.addWidget(imageBrower)
		self.createToolBar()

	def createToolBar(self):
		self.toolBar = self.addToolBar("形象编辑")
		self.openAct = QAction(QIcon('./resource/open.png'), "&Open...",
                self, shortcut=QKeySequence.Open,
                statusTip="Open an image folder", triggered=self.open)
		self.toolBar.addAction(self.openAct)

	def open(self):
		imageDir = QFileDialog.getExistingDirectory(self)
		self.imageBrower.resetImageDir(imageDir)

if __name__ == '__main__':

	import sys

	app = QApplication(sys.argv)

	imageBrower = SImageBrower("D:\\")
	imageView = SImageView(imageBrower)
	imageView.showMaximized()
	sys.exit(app.exec_())
		
