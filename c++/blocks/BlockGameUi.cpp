#include <conio.h>
#include <windows.h>
#include "BlockGameUi.h"

void BlockGameUi::combineWidgets(){
	if(widgets == NULL) return;
	for(size_t num = 0 ; num != nextWidgetNum ; ++num){
		if(this->widgets[num] == NULL) continue;
		this->combineWidget(this->widgets[num]);
	}
}
void BlockGameUi::combineWidget(GameWidget *&_widget){
	//整合中心区域
	size_t begin_i = _widget->y + _widget->border;
	size_t begin_j = _widget->x + _widget->border;
	size_t end_i = _widget->y + _widget->height - _widget->border;
	size_t end_j = _widget->x + _widget->width - _widget->border;
	end_i = end_i > this->height ? this->height : end_i;
	end_j = end_j > this->width ? this->width : end_j;
	for(size_t i = begin_i , r = 0 ; i != end_i && r != _widget->canvas->height ; ++i , ++r)
		for(size_t j = begin_j, c = 0 ; j != end_j && c != _widget->canvas->width ; ++j , ++c)
			this->canvas->materials[i][j] = _widget->canvas->materials[r][c];
	//整合边框
	begin_i = _widget->y;
	begin_j = _widget->x;
	end_i = _widget->y + _widget->height;
	end_j = _widget->x + _widget->width;
	end_i = end_i > this->height ? this->height : end_i;
	end_j = end_j > this->width ? this->width : end_j;
	//4角
	for(size_t i = begin_i; i != begin_i + _widget->border; ++i){
		for(size_t j = begin_j; j != begin_j + _widget->border; ++j){
			size_t _i = end_i - 1 - (i - begin_i);
			size_t _j = end_j - 1 - (j - begin_j);
			if((i - _widget->y) >= (j - _widget->x)){
				this->canvas->materials[i][j]   = SIGN_BORDER_LEFT;
				this->canvas->materials[_i][j]  = SIGN_BORDER_LEFT;
				this->canvas->materials[_i][_j] = SIGN_BORDER_RIGHT;
				this->canvas->materials[i][_j]  = SIGN_BORDER_RIGHT;
			}else{
				this->canvas->materials[i][j]   = SIGN_BORDER_TOP;
				this->canvas->materials[_i][j]  = SIGN_BORDER_DOWN;
				this->canvas->materials[_i][_j] = SIGN_BORDER_DOWN;
				this->canvas->materials[i][_j]  = SIGN_BORDER_TOP;
			}
		}
	}
	//上下边
	begin_i = _widget->y;
	begin_j = _widget->x + _widget->border;
	end_i = _widget->y + _widget->border;
	end_j = _widget->x + _widget->width - _widget->border;
	end_i = end_i > this->height ? this->height : end_i;
	end_j = end_j > this->width ? this->width : end_j;
	for(size_t i = begin_i; i != end_i; ++i){
		size_t _i = i + (_widget->height - _widget->border);
		_i = _i > this->height - 1 ? this->height - 1 : _i;
		for(size_t j = begin_j; j != end_j; ++j){
			this->canvas->materials[i][j]   = SIGN_BORDER_TOP;//上边框
			this->canvas->materials[_i][j]  = SIGN_BORDER_DOWN;//下边框
		}
	}
	//左右边
	begin_i = _widget->y + _widget->border;
	begin_j = _widget->x;
	end_i = _widget->y + _widget->height - _widget->border;
	end_j = _widget->x + _widget->border;
	end_i = end_i > this->height ? this->height : end_i;
	end_j = end_j > this->width ? this->width : end_j;
	for(size_t j = begin_j; j != end_j; ++j){
		size_t _j = j + (_widget->width - _widget->border);
		_j = _j > this->width - 1 ? this->width - 1 : _j;
		for(size_t i = begin_i; i != end_i; ++i){
			this->canvas->materials[i][j]   = SIGN_BORDER_LEFT;
			this->canvas->materials[i][_j]  = SIGN_BORDER_RIGHT;
		}
	}
}
void BlockGameUi::extendWidget(){
	if(nextWidgetNum >= widgetNum){
		GameWidget **temp = new GameWidget*[widgetNum+4];
		for(size_t i = 0 ; i != widgetNum ; ++i){
			temp[i] = widgets[i];
			widgets[i] == NULL;
		}
		delete widgets;
		widgets = temp;
		widgetNum += 4;
	}
}
void BlockGameUi::paint(){
	this->combineWidgets();
	//获取控制台
	HANDLE hConsole = GetStdHandle(STD_OUTPUT_HANDLE); 
	COORD pos; 
	pos.Y = 0;
	pos.X = 0;
	SetConsoleCursorPosition(hConsole, pos); 
	this->renderer->render(this->canvas->materials,this->width,this->height);
}
