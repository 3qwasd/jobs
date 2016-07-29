from TestTools import (UnitTestWindow)

from Widget.FrameSelecter import (ToggleToolBar)
from PyQt5.QtWidgets import (QApplication)

if __name__ == '__main__':

	import sys

	app = QApplication(sys.argv)

	testWidget = ToggleToolBar()
	testWidget.createImageButtons("C:\\Users\\yu\\Desktop\\形象\\步-备选\\大隋·红颜\\ATK.bmp")
	testWidget.createImageButtons("C:\\Users\\yu\\Desktop\\形象\\步-备选\\大隋·红颜\\MOV.bmp")
	testWidget.createImageButtons("C:\\Users\\yu\\Desktop\\形象\\步-备选\\大隋·红颜\\SPC.bmp")

	mainWin = UnitTestWindow(testWidget,"测试动画")
	mainWin.show()
	sys.exit(app.exec_())