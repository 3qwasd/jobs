####Python Tank######
#####地图编辑器######

from PyQt5.QtWidgets import (QApplication)
from MapEditor.MainWidget import (MainUI)

if __name__ == '__main__':

	import sys
	app = QApplication(sys.argv)
	mainWin = MainUI()
	mainWin.show()
	sys.exit(app.exec_())