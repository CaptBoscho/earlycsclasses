#pragma once

#include <iostream>
#include <string>
#include "DNode.h"
#include <fstream>

using namespace std;

template <typename ItemType>

class AVLTreeSet {

private:

	struct Node {
		ItemType item;
		Node* left;
		Node* right;
		int height;
	};


	Node* root;
	int size;

public:

	AVLTreeSet(){
			size=0;
			root=NULL;
		}
		
	~AVLTreeSet()
		{
			if (root != NULL)
			{
				clear();
			}
		}


	int getheight(Node* n) const {

		if (n == NULL)
		{
			return 0;
		}
		else
		{
			return n->height;
		}
	}

	void updateheight(Node* n) const
	{
		int h1 = getheight(n->right);
		int h2 = getheight(n->left);
		int hn;
		
		if (h1 > h2)
		{
			hn = h1 + 1;
		}
		else
		{
			hn = h2 + 1;
		}
		n->height = hn;
	}

	
	int getsize()
	{
		return size;
	}

	void add(const ItemType& item) {
		root = add(root, item);
		size++;

	}
	
	
  
	Node* add(Node* n, const ItemType& item)
	{
		if (n==NULL)
		{
			n = new Node();
			n->item= item;
			n->left = NULL;
			n->right = NULL;
			//n->height = 1;
		}
		else if (item < n->item)
		{
			n->left = add(n->left, item);
			n->height++;
		}
		else if (item > n->item)
		{
			n->right = add(n->right, item);
			//n->height++;
		}
	  
		updateheight(n);
		n = Balance(n);
		
		return n;
	}


	void remove(const ItemType& item)
	{
		root = remove(root, item);
	}
	
	Node* removecase(Node* n, const ItemType& item)
	{
		if (n->left == NULL && n->right == NULL)
		{
			delete n;
			size--;
			return NULL;
		}
		
		else if (n->left == NULL && n->right != NULL)
		{
			Node* temp = n->right;
			delete n;
			size--;
			return temp;
		}
		
		else if (n->left != NULL && n->right == NULL)
		{
			Node* temp = n->left;
			delete n;
			size--;
			return temp;
		}
		
		else
		{
			Node* replace = CTR(n->right);
			n->item = replace->item;
			n->right = remove(n->right, replace->item);
			
			updateheight(n);
			n = Balance(n);
			return n;
		}
	}
	
	
	Node* remove(Node* n, const ItemType& item)
	{
		if (n == NULL)
		{
			return NULL;
		}
		
		else if (item < n->item)
		{
			n->left = remove(n->left, item);
		}
		
		else if (item > n->item)
		{
			n->right = remove(n->right, item);
		}
		else
		{
			return removecase(n, item);
		}
		
		/*else if (n->left == NULL && n->right == NULL)
		{
			delete n;
			size--;
			return NULL;
		}
		
		else if (n->left == NULL && n->right != NULL)
		{
			Node* temp = n->right;
			delete n;
			size--;
			return temp;
		}
		
		else if (n->left != NULL && n->right == NULL)
		{
			Node* temp = n->left;
			delete n;
			size--;
			return temp;
		}
		
		else
		{
			Node* replace = CTR(n->right);
			n->item = replace->item;
			n->right = remove(n->right, replace->item);
			
			updateheight(n);
			n = Balance(n);
			return n;
		}*/
		
		updateheight(n);
		n = Balance(n);
	}

	
	/*void remove(const ItemType& item) {

		bool there = find(item); //see if its there
		if (there == false)
		{
			return;
		}
		
		if (root->item == item)
		{
			if(root->right == NULL && root->left == NULL)
			{
				//cout << "test" << endl;
				Node* temp = root;
				delete temp;
				root = 	NULL;
				size--;
				//cout << size << endl;
				return;
			}
			else if(root->right == NULL)
			{
				Node* temp = root->left;
				delete root;
				root = temp;
				size--;
				//cout << size << endl;
				return;
			}
			else if(root->left == NULL)
			{
				Node* temp = root->right;
				delete root;
				root = temp;
				size--;
				//cout << size << endl;
				return;
			}
			else
			{
			
				Node* g = CTR(root->right);
					
				root->item = g->item;
				root->right = remove(root->right, g->item);			
				//delete g;
				updateheight(root);	
				root = Balance(root);
				size--;
				//cout << size << endl;
				return;
			}
			
		}
		else
		{
			Node* n= remove(root, item);
			size--;
			
		}
				
		//size--;		
	}

	
	void removeless(Node* temp, Node* n, const ItemType& item)
	{
		if(item == n->left->item)
			{
				temp = n->left;
				
				if (temp->left == NULL && temp->right == NULL)
				{
					n->left = NULL;
					delete temp;
				}
				else if(temp->left == NULL)
				{
					n->left = temp->right;
					delete temp;
				}
				else if(temp->right == NULL)
				{
					n->left = temp->left;
					delete temp;
				}
				else  
				{
					Node* g = CTR(temp->right);
					
					temp->item = g->item;
					temp->right = remove(temp->right, g->item);			
					
					updateheight(temp);	
					temp = Balance(temp);
								
					
				}		
				
			}
			else if(item < n->item)
			{
				n->left = remove(n->left, item);
				//cout << n->left->item << endl;
			}
			
	}
	
	void removegreater(Node* temp, Node* n, const ItemType& item)
	{
		if(item == n->right->item) 
			{
				temp = n->right;
				
				if (temp->left == NULL && temp->right == NULL)
				{
					n->right = NULL;
					delete temp;
				}
				else if(temp->left == NULL)
				{
					n->right = temp->right;
					delete temp;
				}
				else if(temp->right == NULL)
				{
					n->right = temp->left;
					delete temp;
				}
				else 
				{
					Node* g = CTR(temp->right);
					
					temp->item = g->item;
					temp->right = remove(temp->right, g->item);			
					//delete g;
					updateheight(temp);
					temp = Balance(temp);
					
				}	
				
				
				
			}
			else if(item > n->item)
			{
				n->right = remove(n->right, item);
			}
	}
	
	Node* remove(Node* n, const ItemType& item)
	{
		Node* temp;
		
		if (item < n->item && n->left != NULL)
		{
			removeless(temp, n, item);			
		}		
		
		else if (item > n->item && n->right != NULL)
		{
			removegreater(temp, n, item);					
		}
		
		else if(item == n->item)
		{
			Node* temp = n->right;
			delete n;
			return temp;
		}
		
		
		updateheight(n);
		n=Balance(n);
		
		return n;
	}*/
	
	
	Node* CTR(Node*n)
	{
		while (n->left!=NULL)
		{
			n = CTR(n->left);			
		}
		return n;
	}

	/*Node* findnode(Node*n, const ItemType& item, Node*p)
	{
		if(item == n->item)
		{
			return p;
		}
		else if (item < n->item)
		{
			n->left = findnode(n->left, item, n);			
		}
		else if (item > n->item)
		{			
			n->right = findnode(n->right, item, n);
		}		
		return p;	
	}*/

	bool find(const ItemType& item) {

		bool found = find(root, item);
		return found;

	}
	
	bool find(Node*n, const ItemType& item)
	{
		bool hoomagaga = false;
		if (size > 0)
		{
			if(item == n->item)
			{
				return true;
			}
			else if (item < n->item)
			{
				if(n->left == NULL)
				{
					return false;
				}
				
				hoomagaga = find(n->left, item);
				
			}
			else if (item > n->item)
			{
				if (n->right == NULL)
				{
					return false;
				}
				
				hoomagaga = find(n->right, item);				
			}	
		}	
		
		return hoomagaga;
	}
	
	
	
	Node* RotateR(Node* rot)
	{
		Node* temp = rot->left;
		if (rot->item == root->item)
		{
			root = temp;
		}
		
		rot->left = temp->right;
		temp->right = rot;
		
		updateheight(temp->right);
		updateheight(temp);
		
		return temp;
	}
	
	Node* RotateL(Node* rot)
	{		
		Node* temp = rot->right;
		if (rot->item == root->item)
		{
			root = temp;
		}
		
		rot->right = temp->left;
		temp->left = rot;
		
		updateheight(temp->left);
		updateheight(temp);
	
		return temp;		
	}
	
	Node* RotSRight(Node* n)
	{
		if(getheight(n->left->right) > getheight(n->left->left))
		{
			n->left = RotateL(n->left);
		}
		
		n=RotateR(n);
		return n;
	}
	
	Node* RotSLeft(Node* n)
	{
		if(getheight(n->right->left) > getheight(n->right->right))
		{
			n->right = RotateR(n->right);
		}
		
		n=RotateL(n);
		return n;
	}
	
	Node* Balance(Node* n)
	{
		if(getheight(n->left) - getheight(n->right) > 1)
		{
			n = RotSRight(n);
		}
		
		if(getheight(n->right) - getheight(n->left) > 1)
		{
			n = RotSLeft(n);
		}
		
		//updateheight(n);
		return n;		
	}
	
  
	void Print(ofstream& out)
	{
		if (size > 0)
		{
			LinkedList<Node*> Q;
			
			if (root != NULL)
			{
				Q.insert(Q.getsize(), root);
			}
			
			Printit(out,Q);

		}
	}
	
	void Printit (ofstream& out, LinkedList<Node*>& Q)
	{
		int level = 0;
		while (Q.getsize()>0)
		{
			int levelsize = Q.getsize();
			for (int i=0; i<levelsize; i++)
			{
				if (Q.getsize()>0)
				{
					if (i % 8 == 0)
					{
						out << endl<< "level "<<level<<": ";
					}
					Node* r = Q.remove(0);											
					out <<r->item <<"("<<r->height<<") ";					
							
					if (r->left != NULL)
					{
						Q.insert(Q.getsize(),r->left);
					}
					
					if (r->right != NULL)
					{
						Q.insert(Q.getsize(),r->right);
					}
				}
			}
			level++;
		}
	}
	
	Node* removeclear(Node* n)
	{
		if(n->left != NULL)
		{
			n->left = removeclear(n->left);
		}
		
		if(n->right != NULL)
		{
			n->right = removeclear(n->right);
		}
		
		if(n->left == NULL && n->right == NULL)
		{
			delete n;
			return NULL;
		}
		return n;
		
	}
	
	
	void clear()
	{
		if (size>0)
		{
			removeclear(root);
			root = NULL;
			size = 0;
		}
	}


};
