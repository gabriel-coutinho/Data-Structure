package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = -1;
	}

	@Override
	public T head() {
		T resultado = null;
		if (!isEmpty()) {
			resultado = array[0];
		}
		return resultado;
	}

	@Override
	public boolean isEmpty() {
		if (tail == -1) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isFull() {
		if (tail == array.length - 1) {
			return true;
		}
		return false;
	}

	private void shiftLeft() {
		for (int i = 0; i < tail; i++) {
			array[i] = array[i + 1];

		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (!isFull()) {
			if (element != null) {
				tail++;
				array[tail] = element;
			}
		} else {
			throw new QueueOverflowException();
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (!isEmpty()) {
			T resultado = array[0];
			shiftLeft();
			tail--;
			return resultado;
		} else {
			throw new QueueUnderflowException();
		}
	}

}