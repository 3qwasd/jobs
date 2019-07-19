#ifndef UTILS_H
#define UTILS_H

#include <cstddef>
const size_t MAIN_WIDTH = 40 , MAIN_HEIGHT = 30;
const size_t BLOCK_AREA_WIDTH = 18 , BLOCK_AREA_HEIGHT = 25;
const size_t NEXT_BLOCK_WIDTH = 12 , NEXT_BLOCK_HEIGHT = 9;
const size_t SCORE_PANEL_WIDTH = 12 , SCORE_PANEL_HEIGHT = 10;
const short ROTATE_DIRECTION_CW = 0 , ROTATE_DIRECTION_ACW = 1;
const short MOVE_DIERCTION_LEFT = 3 , MOVE_DIERCTION_RIGHT = 1;
const short MOVE_DIERCTION_UP = 4 , MOVE_DIERCTION_DOWN = 2;
const size_t SIGN_BORDER_TOP = 0x000B , SIGN_BORDER_DOWN = 0x0003 , SIGN_BORDER_LEFT = 0x0005 , SIGN_BORDER_RIGHT = 0x0007;
const size_t SIGN_BLOCK = 0x0001 , SIGN_BLANK = 0x0000 , SIGN_BACKGROUND = 0x0009 , SIGN_ENTRANCE = 0x000D;
const char KEY_LEFT = 'a', KEY_RIGHT = 'd', KEY_DOWN = 's', KEY_CW = 'j', KEY_ACW = 'k';

char getSignById(const size_t &id);
int** getBlockResByType(const short &type,size_t &row,size_t &col);
int** rotateBlock(int **&block,const short &type,size_t &row,size_t &col);
int** rotate_90D(int **&block,size_t &row,size_t &col);
int** rotate_N90D(int **&block,size_t &row,size_t &col);
int** rotate_180D(int **&block,size_t &row,size_t &col);
void recycleBlock(int **&block,size_t &row);

class Rect{
public:
	size_t width,height;
	int** matrix;
public:
	Rect(size_t _width,size_t _height,int **_matrix):width(_width),height(_height),matrix(_matrix){}
	Rect(size_t _width,size_t _height):width(_width),height(_height),matrix(NULL){
		this->matrix = new int*[this->height];
		for(size_t i = 0; i != this->height; ++i){
			this->matrix[i] = new int[this->width];
			for(size_t j = 0; j != this->width; ++j){
					this->matrix[i][j] = 0;
			}
		}
	}
	Rect():width(0),height(0),matrix(NULL){}
	~Rect(){

		if(this->matrix == NULL) return;

		for(size_t i = 0; i != this->height; ++i){
			if(this->matrix[i] != NULL){
				delete [] this->matrix[i];
			}
		}
		delete [] this->matrix;
	}
};

bool collisionDetection(Rect *&rect_1, Rect *&rect_2, const short &dircetion);//碰撞检测，有碰撞则返回true，无则返回false

#endif