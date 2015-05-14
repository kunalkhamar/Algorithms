/**
 File: dictionary.c
Implementation file for "dictionary.h"
*/

#include "dictionary.h"
#include <stdlib.h>
#include <assert.h>

struct bstnode {
  void *key;
  void *val;
  struct bstnode *left;
  struct bstnode *right;
};

struct dictionary {
  struct bstnode *root;
  
  // utility
  CompareFunction keycmp;
  FreeFunction keyfree;
  FreeFunction valfree;
};


// see "dictionary.h"
Dictionary create_Dictionary(CompareFunction comp_k,
             FreeFunction free_k, FreeFunction free_v) {
  assert(comp_k && free_k && free_v);
  Dictionary d = malloc(sizeof(struct dictionary));
  d->root = NULL;
  d->keycmp = comp_k;
  d->keyfree = free_k;
  d->valfree = free_v;
  return d;
}


// destroy_tree(node, free_k, free_v) destroys node
//   by freeing it and its contents using free_k, free_v
// effects: node is deallocated along with all nodes linked to it.
// time: O(n) where n is the number of nodes linked to struct bstnode node.
void destroy_tree(struct bstnode *node, FreeFunction free_k, 
                  FreeFunction free_v) {
	if (NULL == node) {return;}
	destroy_tree(node->left,  free_k, free_v);
	destroy_tree(node->right, free_k, free_v);
  
  free_k(node->key);
  free_v(node->val);
	free(node);
}


// see "dictionary.h"
void destroy_Dictionary(Dictionary dict) {
  assert(dict);
  destroy_tree(dict->root, dict->keyfree, dict->valfree);
  free(dict);
}


// see "dictionary.h"
void insert(Dictionary dict, void *k, void *v) {
  assert(dict && k && v);
  struct bstnode *p = malloc(sizeof(struct bstnode));
  p->key = k;
  p->val = v;
  p->left = NULL;
  p->right = NULL;
  
  if (dict->root == NULL) {
    dict->root = p;
    return;
  }
  
  struct bstnode *prev = NULL;
  struct bstnode *cur = dict->root;
  int cmp = dict->keycmp(k, cur->key);
  while (!(cur == NULL || cmp == 0)) {
    prev = cur;
    if (cmp < 0)
      cur = cur->left;
    else
      cur = cur->right;
    
    if (cur != NULL)
      cmp = dict->keycmp(k, cur->key);
  }
  
  if (cur == NULL) {
    if (cmp < 0)
      prev->left = p;
    else
      prev->right = p;
  } else {
    dict->valfree(cur->val);
    cur->val = v;
    free(p);
  }
}


//  node_search(node,key,cmp) determines if key is a key for a bstnode node, 
//                     returns val of key if it is in node, NULL otherwise
//  time: O(h) where h is the height of node
void *node_search(struct bstnode *node, void *key, CompareFunction cmp) {
  if (node == NULL)
    return NULL;
  if (cmp(key, node->key) == 0)
    return node->val;
  else {
    void *t1 = node_search(node->left,  key, cmp);
    if (t1 != NULL) return t1;
    void *t2 = node_search(node->right, key, cmp);
    if (t2 != NULL) return t2;
    return NULL;
  }
}


// see "dictionary.h"
void *lookup(Dictionary dict, void *k) {
  assert(dict && k);
  return node_search(dict->root, k, dict->keycmp);
}
