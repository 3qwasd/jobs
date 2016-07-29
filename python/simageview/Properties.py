##属性

frameWidth = 64
frameHeight = 64


from PyQt5.QtCore import (Qt)
from PyQt5.QtGui import (QColor)

def eliminateBackgroundColor(pixmap):
	backgroundColor = QColor(pixmap.toImage().pixel(0,0))
	bitmap = pixmap.createMaskFromColor(backgroundColor,Qt.MaskInColor)
	pixmap.setMask(bitmap)
	pixmap.mask()