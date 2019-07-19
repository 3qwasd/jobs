

from PyQt5.QtWidgets import(QPushButton,QButtonGroup,QWidget,QHBoxLayout,QVBoxLayout)
from PyQt5.QtGui import (QPen,QPainter,QPixmap,QImage,QColor,QBrush)
from PyQt5.QtCore import (Qt)
from Properties import (frameWidth,frameHeight,eliminateBackgroundColor)


class ToggleToolBar(QWidget):

	index = 0
	buttons = []
	rows = []
	maxRow = 7
	maxCol = 4
	selectedFrame = []
	selectedButtons = []
	def __init__(self):
		super(ToggleToolBar, self).__init__()
		self.setFixedSize(300,560)
		self.container = QVBoxLayout()
		self.container.setAlignment(Qt.AlignLeft)
		self.container.setSpacing(5)
		for i in range(0,self.maxRow):
			row = QHBoxLayout()
			row.setAlignment(Qt.AlignTop)
			row.setSpacing(5)
			self.container.addLayout(row)
			self.rows.append(row)
		self.buttonGroup = QButtonGroup()
		self.buttonGroup.setExclusive(False)
		self.setLayout(self.container)
		self.buttonGroup.buttonClicked[int].connect(self.imageSelected)
	
	def imageSelected(self,imgId):
		button = self.buttons[imgId]
		if button.pixMap in self.selectedFrame and (not button.isChecked()):
			self.selectedFrame.remove(button.pixMap)
			self.selectedButtons.remove(button)

		if (not (button.pixMap in self.selectedFrame)) and button.isChecked():
			self.selectedFrame.append(button.pixMap)
			self.selectedButtons.append(button)

	def getSelected(self):
		return self.selectedFrame

	def clearAllSelected(self):
		for button in self.selectedButtons:
			button.setChecked(False)
		self.selectedButtons = []
		self.selectedFrame = []

	def addImageButton(self,image):
		imageButton = ImageToggleButton(self.index,image)
		self.buttonGroup.addButton(imageButton,self.index)
		self.rows[self.index//self.maxCol].addWidget(imageButton)
		self.buttons.append(imageButton)
		self.index += 1

	def createImageButtons(self,imagePath):
		groupImg = QImage(imagePath)
		width = groupImg.width()
		height = groupImg.height()
		frameSize = width
		for i in range(0,height//width):
			img = groupImg.copy(0,i*frameSize,frameSize,frameSize);
			self.addImageButton(img)

	def removeAllButton(self):
		for i in range(0,self.index):
			row = self.rows[i // self.maxCol]
			row.removeWidget(self.buttons[i])
			self.buttonGroup.removeButton(self.buttons[i])
			self.buttons[i].close()
		self.buttons = []
		self.index = 0

class ToggleButton(QPushButton):
	
	def __init__(self):
		super(ToggleButton, self).__init__()
		self.setFixedSize(frameWidth+8,frameWidth+8)
		self.setCheckable(True)
		self.setChecked(False)
		self.checkedPen = QPen(Qt.red,2,Qt.SolidLine)
		
class ImageToggleButton(ToggleButton):

	def __init__(self,imgId,img):
		super(ImageToggleButton, self).__init__()
		self.id = imgId
		self.pixMap = QPixmap(img)
		eliminateBackgroundColor(self.pixMap)
		self.backBrush = QBrush(QColor(img.pixel(0,0)))

	def paintEvent(self,event):
		super().paintEvent(event)
		painter = QPainter(self)
		painter.fillRect(4,4,frameWidth,frameHeight,self.backBrush)
		painter.drawPixmap(4+(frameWidth-self.pixMap.width())//2,4+(frameHeight-self.pixMap.height())//2,self.pixMap)
		if self.isChecked():
			painter.setPen(self.checkedPen)
			painter.drawRect(3,3,self.width()-6,self.height()-6)
		