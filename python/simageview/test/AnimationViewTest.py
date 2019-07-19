from TestTools import (UnitTestWindow)

from Widget.AnimationView import (AnimationView)
from PyQt5.QtWidgets import (QApplication)

if __name__ == '__main__':

	import sys

	app = QApplication(sys.argv)

	testWidget = AnimationView(None)
	testWidget.createAnimation()
	mainWin = UnitTestWindow(testWidget,"测试动画")
	mainWin.show()
	sys.exit(app.exec_())