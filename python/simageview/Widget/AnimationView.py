##动画展示器

from PyQt5.QtCore import (QTimeLine,Qt)
from PyQt5.QtWidgets import (QWidget)
from PyQt5.QtGui import (QImage,QPixmap,QPainter,QColor)
from Properties import (frameWidth,frameHeight,eliminateBackgroundColor)

class AnimationView(QWidget):
	"""docstring for AnimationView"""

	frameIndex = 0
	frameCount = 0

	def __init__(self, bmpFrames):
		super(AnimationView, self).__init__()
		self.setFixedSize(frameWidth,frameHeight)
		self.bmpSize =64
		if bmpFrames :
			self.bmpFrames = bmpFrames
			self.frameCount = len(bmpFrames)
			self.bmpSize = bmpFrames[0].width()
		else :
			self.initDefaultFrames()

	def initDefaultFrames(self):
		self.bmpFrames = []
		defaultImage = QImage("..\\resource\\default.bmp")
		imageWidth = defaultImage.width()
		imageHeight = defaultImage.height()
		#判断各幀图片是否是横向排列
		isHorizontal = min(imageWidth,imageHeight) == imageHeight
		#计算幀数
		self.frameCount = imageWidth//frameWidth if isHorizontal else imageHeight//frameHeight
		for i in range(0,self.frameCount):
			pixmap = QPixmap(defaultImage.copy(i*frameWidth if isHorizontal else 0,
				0 if isHorizontal else i*frameHeight,frameWidth,frameHeight))
			eliminateBackgroundColor(pixmap)
			self.bmpFrames.append(pixmap)
	
	def createAnimation(self):
		self.timeLine = QTimeLine(10*1000)
		self.timeLine.setFrameRange(0,60//(4/self.frameCount))
		self.timeLine.frameChanged.connect(self.refreshFrameIndex)
		self.timeLine.setLoopCount(0)
		self.timeLine.setCurveShape(3)
		self.timeLine.start()

	def refreshFrameIndex(self,currFrame):
		self.update()
		self.frameIndex = (self.frameIndex+1) % self.frameCount

	def paintEvent(self,event):
		painter = QPainter(self)
		painter.drawPixmap((frameWidth-self.bmpSize)//2,(frameHeight-self.bmpSize)//2,self.bmpFrames[self.frameIndex])
