##测试文件浏览器效果

from TestTools import (UnitTestWindow)

from Widget.DirectoryView import (SImageFolderView)
from PyQt5.QtWidgets import (QApplication)

if __name__ == '__main__':

	import sys

	app = QApplication(sys.argv)
	testWidget = SImageFolderView("C:\\Python34\\Lib\\site-packages\\PyQt5\\examples\\itemviews\\")
	testWidget.setClickedHandle(lambda modelIndex:print("clicked") or print(testWidget.fsModel.filePath(modelIndex)))
	mainWin = UnitTestWindow(testWidget,"测试文件浏览器效果")
	mainWin.show()
	sys.exit(app.exec_())