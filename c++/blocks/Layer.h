#ifndef LAYER_H
#define LAYER_H

#include "Utils.h"

class Layer{
public:
	size_t width,height;
	int ** materials;
	size_t bgSignId;
public:
	Layer(size_t w,size_t h,size_t bg):width(w),height(h),bgSignId(bg),materials(NULL){
		this->init();
	}
	~Layer(){
		this->release();
	}
	void cleanArea(const size_t x,const size_t y,const size_t _width,const size_t _height);
	void cleanArea(const size_t x,const size_t y,const size_t _width,const size_t _height,int**content);
	void cleanLine(const size_t y);
	void addArea(const size_t x,const size_t y,const size_t _width,const size_t _height,int**content);
	Rect* getRect(const size_t x,const size_t y,const size_t _width,const size_t _height);
	bool areaHasFilled(const size_t x,const size_t y,const size_t _width,const size_t _height);
	bool lineHasFilled(const size_t y);
	bool areaHasEmpty(const size_t x,const size_t y,const size_t _width,const size_t _height);
	bool lineHasEmpty(const size_t y);
	bool moveLineDown(const size_t y,const size_t offset);
private:
	void init();
	void release();
};

#endif
