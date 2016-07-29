from TestTools import (UnitTestWindow)

from Widget.SImageBrower import (SImageBrower)
from PyQt5.QtWidgets import (QApplication)

if __name__ == '__main__':

	import sys

	app = QApplication(sys.argv)

	testWidget = SImageBrower("C:\\Users\\jobs\\Desktop\\形象")
	mainWin = UnitTestWindow(testWidget,"测试动画")
	mainWin.show()
	sys.exit(app.exec_())