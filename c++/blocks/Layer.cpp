#include "Layer.h"

void Layer::init(){
	this->materials = new int *[this->height];
	for(size_t i = 0 ; i != this->height ; ++i){
		this->materials[i] = new int[this->width];
		for(size_t j = 0 ; j != this->width ; ++j){
			this->materials[i][j] = this->bgSignId;
		}
	}
}
void Layer::release(){
	for(size_t i = 0 ; i < this->height ; ++i){
		delete this->materials[i];
		this->materials[i] = NULL;
	}
	delete this->materials;
	this->materials = NULL;
}
bool Layer::moveLineDown(const size_t y,const size_t offset){
	bool res = false;
	size_t _y = y + offset;
	if(y >= this->height || _y >= this->height)
		return false;
	for(size_t j = 0; j != this->width; ++j){
		res |= this->materials[y][j] != this->bgSignId;
		this->materials[_y][j] = this->materials[y][j];
		this->materials[y][j] = this->bgSignId;
	}
	return res;
}
bool Layer::areaHasFilled(const size_t x,const size_t y,const size_t _width,const size_t _height){

	if(x < 0 || y < 0 || x >= this->width || y >= this->height)
		return false;
	size_t end_x = (x + _width) > this->width ? this->width : x + _width;
	size_t end_y = (y + _height) > this->height ? this->height : y + _height;
	for(size_t i = y, _i = end_y - 1; i <= _i ; ++i, --_i)
		for(size_t j = x, _j = end_x - 1; j <= _j ; ++j, --_j){
			if(this->materials[i][j] == this->bgSignId | this->materials[i][_j] == this->bgSignId |
				this->materials[_i][j] == this->bgSignId | this->materials[_i][_j] == this->bgSignId)
				return false;

		}
	return true;
}
bool Layer::lineHasFilled(const size_t y){
	return areaHasFilled(0,y,this->width,1);
}
bool Layer::areaHasEmpty(const size_t x,const size_t y,const size_t _width,const size_t _height){

	if(x < 0 || y < 0 || x >= this->width || y >= this->height)
		return false;
	size_t end_x = (x + _width) > this->width ? this->width : x + _width;
	size_t end_y = (y + _height) > this->height ? this->height : y + _height;
	for(size_t i = y, _i = end_y - 1; i <= _i ; ++i, --_i)
		for(size_t j = x, _j = end_x - 1; j <= _j ; ++j, --_j){
			if(this->materials[i][j] != this->bgSignId | this->materials[i][_j] != this->bgSignId |
				this->materials[_i][j] != this->bgSignId | this->materials[_i][_j] != this->bgSignId)
				return false;

		}
	return true;
}
bool Layer::lineHasEmpty(const size_t y){
	return areaHasEmpty(0,y,this->width,1);
}
void Layer::cleanLine(const size_t y){
	this->cleanArea(0,y,this->width,1);
}
void Layer::cleanArea(const size_t x,const size_t y,const size_t _width,const size_t _height){
	if(x < 0 || y < 0 || x >= this->width || y >= this->height)
		return;
	size_t end_x = (x + _width) > this->width ? this->width : x + _width;
	size_t end_y = (y + _height) > this->height ? this->height : y + _height;
	for(size_t i = y ; i != end_y ; ++i)
		for(size_t j = x ; j != end_x ; ++j){
			this->materials[i][j] = this->bgSignId;
		}
}
void Layer::cleanArea(const size_t x,const size_t y,const size_t _width,const size_t _height,int**content){

	if(x < 0 || y < 0 || x >= this->width || y >= this->height)
		return;
	size_t end_x = (x + _width) > this->width ? this->width : x + _width;
	size_t end_y = (y + _height) > this->height ? this->height : y + _height;
	for(size_t i = y ; i != end_y ; ++i)
		for(size_t j = x ; j != end_x ; ++j){
			if(this->materials[i][j] == content[i-y][j-x])
				this->materials[i][j] = this->bgSignId;
		}
}
void Layer::addArea(const size_t x,const size_t y,const size_t _width,const size_t _height,int**content){
	if(x < 0 || y < 0 || x >= this->width || y >= this->height)
		return;
	size_t end_x = (x + _width) > this->width ? this->width : x + _width;
	size_t end_y = (y + _height) > this->height ? this->height : y + _height;
	for(size_t i = y ; i != end_y ; ++i)
		for(size_t j = x ; j != end_x ; ++j){
			if(this->materials[i][j] == this->bgSignId)
				this->materials[i][j] = content[i-y][j-x];
		}
}
Rect* Layer::getRect(const size_t x,const size_t y,const size_t _width,const size_t _height){
	size_t _x = x + _width, _y = y + _height;
	if(x < 0 || y < 0 || _x > this->width || _y > this->height)
		return NULL;
	size_t _i, _j;
	Rect *rect = new Rect(_width,_height);
	for(size_t i = y; i != _y; ++i){
		_i = i - y;
		for(size_t j = x; j != _x; ++j){
			_j = j - x;
			rect->matrix[_i][_j] = this->materials[i][j];
		}
	}
	return rect;
}
