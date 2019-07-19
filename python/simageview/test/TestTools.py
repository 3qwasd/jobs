##用于单元测试的主窗体

import sys
import os

sys.path.append(os.path.abspath('..'))

from PyQt5.QtGui import (QIcon)
from PyQt5.QtWidgets import (QMainWindow,QWidget,QVBoxLayout)

class UnitTestWindow(QMainWindow):

	def __init__(self,testWidget,title):
		super(UnitTestWindow, self).__init__()
		self.setWindowTitle(title if title != None else "测试窗口")
		self.setWindowIcon(QIcon("../resource/test.ico"))
		self.mainWidget = QWidget()
		self.mainContainer = QVBoxLayout()
		self.setCentralWidget(self.mainWidget)
		self.mainWidget.setLayout(self.mainContainer)
		self.mainContainer.addWidget(testWidget)
