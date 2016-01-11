#pragma once

#include <iostream>
#include <string>

using namespace std;

template <typename ItemType>

class LinkedList {

private:
	
	struct DNode {
		ItemType item;
		DNode* next;
		DNode* prev;
	};


	DNode* head;
	DNode* tail;
	int size;

public:
	
		LinkedList(){
			size=0;
			head=NULL;
			tail=NULL;
		}
		~LinkedList()
		{
			while (size>0)
			{
				remove(0);
			}
		}
	
		int getsize() const
		{ return size; }
    
		void insert(int index, const ItemType& item) {

			DNode* n = new DNode();
			n->item = item;
		
			if (index == size)
			{
				if (size ==0)
				{
					head =n;
				}
				else
				{
					tail->next=n;
				}
			
				n->next = NULL;
				n->prev = tail;
				tail = n;
				
				
				size++;
				
				return;
			}
			else if (index ==0)
			{
				//Node* s = head->next;
			
				/*n->next = s;
				head->next= n;
				s->prev = n;
				n->prev = NULL;*/
				
				head->prev=n;
				n->prev = NULL;
				n->next = head;
				head = n;
				
			
				size++;
				
				return;
			
			}
			else if (index > 0 && index < size)
			{
				DNode* e = FindNode(index);
				DNode* ep = e->prev;
			
				e->prev = n;
				n->prev = ep;
				ep->next = n;
				n->next = e;
				size++;
				
				return;
			}
			else
			{
				cout << "Index is invalid." << endl;
				return;
			}
			
			return;		
		}

		ItemType remove(int index) {
						
			if(index==0)
			{
				
				DNode* r = head;
				ItemType name = head->item;
				head = head->next;
				if(head == NULL)
				{
					tail = NULL;
				}
				else
				{
					head->prev = NULL;
				}
				size--;
				
				delete r;
				return name;

			}
			else if(index == size-1)
			{
				
				DNode* r = tail;
				ItemType name = tail->item;
				tail = tail->prev;
				if(tail == NULL)
				{
					head = NULL;
				}
				else
				{
					tail->next=NULL;
				}
				size--;
				
				
				delete r;
				return name;

			}
			else if(index > 0 && index < size-1)
			{
				DNode* r = FindNode(index);
				ItemType name = r->item;
				
				DNode* a = r->prev;
				DNode* z = r->next;
				
				a->next = z;
				z->prev = a;
				size--;
				
				delete r;
				return name;

			}
			
			ItemType grr;
			return grr;
			

		}

		int find(const ItemType& item) {
			
			DNode* search = head;
			for (int m=0; m<size; m++)
			{
				if(search->item == item)
				{
					return m;
					//return true;
				}
				else
				{
					search= search->next;
				}
			}

			return -1;

		}
  
		DNode* FindNode(int index)
		{
			int mid = size / 2;
			if (index <= mid)
			{
				DNode* p = head;
				for (unsigned int i=0; i<index; i++)
				{
					p = p->next;
				}
				return p;
			}
			else
			{
				DNode* p = tail;
				int dif = size - index;
				for (int k=0; k<dif-1; k++)
				{
					p = p->prev;
				}
				return p;
			}
	
		}
		
		void Printer(ofstream& write, int hl)
		{
			//write << "print"<<endl;
			if (size>0)
			{
				DNode* pr = head;
				
				int level = 0;
			
				for(int q=0; q<size; q++)
				{
					if(level == 8)
					{
						write <<endl<<"hash " << hl<<":";
						level == 0;
					}
					
					//write << "Node "<<q<<": "<<pr->item<<endl;
					write << " " <<pr->item;
					
					if (pr->next!=NULL)
					{
						pr= pr->next;						
					}
					level++;
					
				}
				
			}
		}
		
		void clear()
		{
			while (size>0)
			{
				remove(0);
			}
		}
		
		


};
