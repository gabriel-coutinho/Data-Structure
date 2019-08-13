package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;
import adt.hashtable.hashfunction.HashFunctionOpenAddress;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		int probe = 0;

		while (probe < table.length) {

			int indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);

			if (table[indice] == null || table[indice] instanceof DELETED) {
				table[indice] = element;
				elements++;
				return;
			} else {
				probe++;
				COLLISIONS++;
			}
		}
		throw new HashtableOverflowException();
	}

	@Override
	public void remove(T element) {
		int probe = 0;
		boolean removido = false;
		int indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);

		while (probe < table.length && !removido && table[indice] != null) {

			if (table[indice].equals(element)) {
				table[indice] = new DELETED();
				elements--;
				removido = true;
			} else {
				probe++;
				indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
			}
		}
	}

	@Override
	public T search(T element) {
		int probe = 0;
		int indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);

		while (probe < table.length && table[indice] != null) {

			if (table[indice].equals(element)) {
				return (T) table[indice];
			} else {
				probe++;
				indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);
			}
		}
		return null;
	}

	@Override
	public int indexOf(T element) {
		int indiceElemento = -1;
		int probe = 0;
		boolean encontrado = false;
		
		while (probe < table.length && !encontrado) {

			int indice = ((HashFunctionOpenAddress<T>) hashFunction).hash(element, probe);

			if (table[indice] == null) {
				encontrado = true;
			}

			else if (table[indice].equals(element)) {
				indiceElemento = indice;
				encontrado = true;
			} else {
				probe++;
			}
		}
		return indiceElemento;
	}
}
