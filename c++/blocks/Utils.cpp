#include "Utils.h"

char getSignById(const size_t &id){
	switch(id){
		case SIGN_BORDER_TOP:
		return '*';
		case SIGN_BORDER_DOWN:
		return '*';
		case SIGN_BORDER_LEFT:
		return '|';
		case SIGN_BORDER_RIGHT:
		return '|';
		case SIGN_BLOCK:
		return '#';
		case SIGN_BLANK:
		return ' ';
		case SIGN_BACKGROUND:
		return ' ';
		case SIGN_ENTRANCE:
		return '*';
		default:
		return '~';
	}
}

int** getBlockResByType(const short &type,size_t &row,size_t &col){
	short bigType = type/10;
	short smallType = type % 10;
	int ** temp;
	int ** res;
	row = 3;
	col = 2; 
	switch(bigType){
		case 1:
		temp = new int*[3];
		temp[0] = new int[2];temp[0][0] = SIGN_BLOCK;temp[0][1] = SIGN_BLANK;
		temp[1] = new int[2];temp[1][0] = SIGN_BLOCK;temp[1][1] = SIGN_BLOCK;
		temp[2] = new int[2];temp[2][0] = SIGN_BLOCK;temp[2][1] = SIGN_BLANK;
		break;
		case 2:
		temp = new int*[3];
		temp[0] = new int[2];temp[0][0] = SIGN_BLANK;temp[0][1] = SIGN_BLOCK;
		temp[1] = new int[2];temp[1][0] = SIGN_BLOCK;temp[1][1] = SIGN_BLOCK;
		temp[2] = new int[2];temp[2][0] = SIGN_BLANK;temp[2][1] = SIGN_BLOCK;
		break;
		case 3:
		temp = new int*[3];
		temp[0] = new int[2];temp[0][0] = SIGN_BLOCK;temp[0][1] = SIGN_BLANK;
		temp[1] = new int[2];temp[1][0] = SIGN_BLOCK;temp[1][1] = SIGN_BLANK;
		temp[2] = new int[2];temp[2][0] = SIGN_BLOCK;temp[2][1] = SIGN_BLOCK;
		break;
		case 4:
		temp = new int*[3];
		temp[0] = new int[2];temp[0][0] = SIGN_BLANK;temp[0][1] = SIGN_BLOCK;
		temp[1] = new int[2];temp[1][0] = SIGN_BLANK;temp[1][1] = SIGN_BLOCK;
		temp[2] = new int[2];temp[2][0] = SIGN_BLOCK;temp[2][1] = SIGN_BLOCK;
		break;
		case 6:
		row = 4;
		col = 1;
		temp = new int*[4];
		temp[0] = new int[1];temp[0][0] = SIGN_BLOCK;
		temp[1] = new int[1];temp[1][0] = SIGN_BLOCK;
		temp[2] = new int[1];temp[2][0] = SIGN_BLOCK;
		temp[3] = new int[1];temp[3][0] = SIGN_BLOCK;
		break;
		default:
		row = 2;
		col = 2;
		temp = new int*[2];
		temp[0] = new int[2];temp[0][0] = SIGN_BLOCK;temp[0][1] = SIGN_BLOCK;
		temp[1] = new int[2];temp[1][0] = SIGN_BLOCK;temp[1][1] = SIGN_BLOCK;
		break;
	}

	switch(smallType){
		case 2:
		res = rotateBlock(temp,2,row,col);
		recycleBlock(temp,row);
		return res;
		case 3:
		res = rotateBlock(temp,3,row,col);
		recycleBlock(temp,row);
		return res;
		case 4:
		res = rotateBlock(temp,4,row,col);
		recycleBlock(temp,row);
		return res;
		default:
		return temp;
	}
}

int** rotate_90D(int **&block,size_t &row,size_t &col){
	int ** res;
	res = new int*[col];
	for(size_t i = 0 ; i != col ; ++i){
		res[i] = new int[row];
		for(size_t j = 0 ; j != row ; ++j){
			res[i][j] = block[row-j-1][i];
		}
	}
	size_t temp = col;
	col = row;
	row = temp;
	return res;
}

int** rotate_N90D(int **&block,size_t &row,size_t &col){
	int ** res;
	res = new int*[col];
	for(size_t i = 0 ; i != col ; ++i){
		res[i] = new int[row];
		for(size_t j = 0 ; j != row ; ++j){
				res[i][j] = block[j][col-i-1];
		}
	}
	size_t temp = col;
	col = row;
	row = temp;
	return res;
}

int** rotate_180D(int **&block,size_t &row,size_t &col){
	int ** res;
	res = new int*[row];
	for(size_t i = 0 ; i != row ; ++i){
		res[i] = new int[col];
		for(size_t j = 0 ; j != col ; ++j){
			res[i][j] = block[row-i-1][j];
		}
	}
	return res;
}

int** rotateBlock(int **&block,const short &type,size_t &row,size_t &col){
	switch(type){
		case 3://旋转180度
		return rotate_180D(block,row,col);
		case 4://旋转270度，-90度
		return rotate_N90D(block,row,col);
		default://旋转90度
		return rotate_90D(block,row,col);
	}
}

void recycleBlock(int **&block,size_t &row){
	if(block == NULL) return;
	for(size_t i = 0 ; i != row ; ++i){
		if(block[i] == NULL) continue;
		delete [] block[i];
	}
	delete [] block;
}

bool collisionDetection(Rect *&rect_1,Rect *&rect_2,const short &dircetion){
	if(rect_1 == NULL || rect_2 == NULL) return true;
	size_t i, j, _i, _j;
	bool res = false; 
	switch(dircetion){

		case MOVE_DIERCTION_RIGHT:
		case MOVE_DIERCTION_LEFT:
		for(j = 0, _j = 0; j != rect_1->width && _j != rect_2->width; ++j, ++_j){
			for(i = 0, _i = 0; i != rect_1->height && _i != rect_2->height; ++i, ++_i){
				res |= rect_1->matrix[i][j] & rect_2->matrix[_i][_j] != 0;
			}
		}
		return res;

		case MOVE_DIERCTION_DOWN:
		case MOVE_DIERCTION_UP:
		for(i = 0, _i = 0; i != rect_1->height && _i != rect_2->height; ++i, ++_i){
			for(j = 0, _j = 0; j != rect_1->width && _j != rect_2->width; ++j, ++_j){
				res |= rect_1->matrix[i][j] & rect_2->matrix[_i][_j] != 0;
			}
		}
		return res;

		default:
		return true;
	}
}
