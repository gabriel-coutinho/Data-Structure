package adt.skipList;

import java.util.LinkedList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	
	@Override
	public void insert(int key, T newValue, int height) {
		if(newValue == null || height < 1 || height > maxHeight) {
			return;
		}
		SkipListNode<T>[] update = new SkipListNode[maxHeight];
		SkipListNode<T> node = this.root;
		
		for (int i = maxHeight - 1; i >= 0; i--) {
			while(node.getForward(i).getKey() < key) {
				node = node.getForward(i);
			}
			update[i] = node;
		}
		node = node.getForward(0);
		if(node.getKey() == key) {
			node.setValue(newValue);
		} else {
			node = new SkipListNode<T>(key, height, newValue);
			for (int i = 0; i < height; i++) {
				node.getForward()[i] = update[i].getForward(i);
				update[i].getForward()[i] = node;
			}
		}
	}

	@Override
	public void remove(int key) {
		SkipListNode<T>[] update = new SkipListNode[maxHeight];
		SkipListNode<T> node = this.root;
		
		for (int i = maxHeight - 1; i >= 0; i--) {
			while(node.getForward(i).getKey() < key) {
				node = node.getForward(i);
			}
			update[i] = node;
		}
		node = node.getForward(0);
		if(node.getKey() == key) {
			int i = 0;
			while(i < maxHeight && update[i].getForward(i) == node) {
				update[i].getForward()[i] = node.getForward(i);
				i++;
			}
		}
	}

	@Override
	public int height() {
		int result = -1;
		int i = maxHeight - 1;
		while(result == -1 && i >= 0) {
			if(this.root.getForward(i).getKey() != Integer.MAX_VALUE) {
				result = i;
			}
			i--;
		}
		return ++result;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> node = this.root;
		for (int i = maxHeight - 1; i >= 0; i--) {
			while(node.getForward(i).getKey() < key) {
				node = node.getForward(i);
			}
		}
		node = node.getForward(0);
		if(node.getKey() != key) {
			node = null;
		}
		return node;
	}

	@Override
	public int size() {
		int result = 0;
		SkipListNode<T> node = this.root.getForward(0);
		while(node.getKey() != Integer.MAX_VALUE) {
			result++;
			node = node.getForward(0);
		}
		return result;
	}

	@Override
	public SkipListNode<T>[] toArray() {
		LinkedList<SkipListNode<T>> result = new LinkedList<>();
		SkipListNode<T> node = this.root;
		while(node.getKey() != Integer.MAX_VALUE) {
			result.add(node);
			node = node.getForward(0);
		}
		result.add(node);
		SkipListNode<T>[] array = new SkipListNode[result.size()];
		return (SkipListNode<T>[]) result.toArray(array);
	}

}
