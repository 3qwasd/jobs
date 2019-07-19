#ifndef BLOCK_H
#define BLOCK_H

#include "Utils.h"
#include "Layer.h"
class Block{
public:
	size_t x,y;            //在外部容器中的坐标
	size_t width,height;   //方块的宽和高
	short type;
private:
	int currShape;         //类型，不同的类型宽，高，以及形状不同
	int ***shapes = NULL;         //形状，用矩阵来表示形状
	Rect *rect = NULL;
public:
	Block(size_t p_x,size_t p_y,short p_type):x(p_x),y(p_y),type(p_type){
		this->init();
	}
	~Block(){
		this->release();
	}
	int** getCurrShape();
	//旋转
	int** rotate(const short &direction);
	//顺时针旋转
	int** rotateCw();
	//逆时针旋转
	int** rotateACw();
	//移动
	void move(const short &direction,const size_t &speed);
	Rect* getRect();
private:
	void init();
	void release();
};

class BlockAreaLayer : public Layer{
public:
	BlockAreaLayer(size_t w,size_t h,size_t bg):Layer(w, h, bg){
		this->init();
	}
	void init();
};
#endif