#ifndef BLOCKGAMEUI_H
#define BLOCKGAMEUI_H

#include "Utils.h"
#include "Layer.h"
#include "Renderer.h"

class GameWidget{
public:
	size_t x,y;//在主UI里面的坐标
	Layer *canvas = NULL; //画布，使用Layer表示画布，layer的二维数组每一个元素就表示画布的一个单位格
	size_t border;
	size_t width,height;
public:
	GameWidget(size_t p_x,size_t p_y,Layer *p_canvas,size_t _border):
	x(p_x),
	y(p_y),
	canvas(p_canvas),
	border(_border),
	width(p_canvas->width+2*_border),
	height(p_canvas->height+2*_border){}
};

class BlockGameUi:public GameWidget{
private:
	Renderer *renderer = NULL; //渲染界面
	GameWidget **widgets = NULL;//组件列表
	size_t widgetNum;
	size_t nextWidgetNum;
public:
	BlockGameUi(size_t p_x,size_t p_y,Layer *p_canvas):
	GameWidget(p_x,p_y,p_canvas,0),
	widgetNum(4),
	nextWidgetNum(0){
		widgets = new GameWidget*[widgetNum];
	}

	void addWidget(GameWidget * widget){
		widgets[nextWidgetNum++] = widget;
		this->extendWidget();
	}
	void paint();
private:
	void combineWidgets();
	void combineWidget(GameWidget *&_widget);
	void extendWidget();
};


#endif